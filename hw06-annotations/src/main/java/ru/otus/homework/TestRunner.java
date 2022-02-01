package ru.otus.homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

public class TestRunner {

    private TestRunner() {
    }

    public static <T> void run(Class<T> clazz) {
        Method[] methods = clazz.getDeclaredMethods();

        List<Method> testMethods = getMethodsByAnnotation(methods, Test.class);
        List<Method> beforeMethods = getMethodsByAnnotation(methods, Before.class);
        List<Method> afterMethod = getMethodsByAnnotation(methods, After.class);

        runTests(clazz, testMethods, beforeMethods, afterMethod);
    }

    private static List<Method> getMethodsByAnnotation(Method[] allMethods, Class<? extends Annotation> annotationClazz) {
        List<Method> methods = new ArrayList<>();
        for (Method method : allMethods) {
            if (method.isAnnotationPresent(annotationClazz)) {
                methods.add(method);
            }
        }
        return methods;
    }

    private static <T> void runTests(Class<T> clazz, List<Method> testMethods, List<Method> beforeMethods, List<Method> afterMethod) {
        int failedCount = 0;

        for (Method testMethod : testMethods) {
            T object = instantiate(clazz);
            beforeMethods.forEach(m -> callMethod(object, m.getName()));
            try {
                callMethod(object, testMethod.getName());
            } catch (Exception e) {
                failedCount++;
                System.out.println(e.getCause().getCause().getMessage());
            }
            afterMethod.forEach(m -> callMethod(object, m.getName()));
        }

        int testCount = testMethods.size();
        int passedCount = testCount - failedCount;

        System.out.println("_____________________________");
        System.out.println("RUN: " + testCount);
        System.out.println("PASSED: " + passedCount);
        System.out.println("FAILED: " + failedCount);
    }

    private static Object callMethod(Object object, String name, Object... args) {
        try {
            var method = object.getClass().getDeclaredMethod(name, toClasses(args));
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }
}
