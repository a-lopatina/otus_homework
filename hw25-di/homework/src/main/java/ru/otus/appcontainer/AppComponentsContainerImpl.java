package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);

        List<Method> methods = Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class)).toList();

        Object configClassObj;
        try {
            configClassObj = configClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Не смогли создать инстанс");
        }

        methods.stream().sorted(Comparator.comparingInt(config -> config.getAnnotation(AppComponent.class).order()))
                .forEach(method -> addComponent(configClassObj, method));
    }

    private void addComponent(Object configObject, Method method) {
        String componentName = method.getAnnotation(AppComponent.class).name();
        Object[] params;
        List<? extends Class<?>> paramsTypes = Arrays.stream(method.getParameters())
                .map(Parameter::getType).toList();

        params = paramsTypes.isEmpty()
                ? new Object[0]
                : paramsTypes.stream().map(this::getAppComponent).toArray();

        try {
            Object component = method.invoke(configObject, params);
            appComponentsByName.put(componentName, component);
            appComponents.add(component);
        } catch (Exception e) {
            throw new RuntimeException("Возникла проблема при инициализации компонента");
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(componentClass::isInstance)
                .findFirst()
                .orElse(null);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
