package ru.logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Proxy {

    private Proxy() {
    }

    static TestLogging createTestLoggingClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLogging) java.lang.reflect.Proxy.newProxyInstance(Proxy.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingImpl testLoggingClass;
        private final List<Method> loggingMethods;

        DemoInvocationHandler(TestLoggingImpl testLoggingClass) {
            this.testLoggingClass = testLoggingClass;
            Class<TestLoggingImpl> clazz = TestLoggingImpl.class;
            Method[] methods = clazz.getDeclaredMethods();
            loggingMethods = new ArrayList<>();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Log.class)) {
                    loggingMethods.add(method);
                }
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method implMethod = testLoggingClass.getClass().getMethod(method.getName(), method.getParameterTypes());
            if (loggingMethods.contains(implMethod)) {
                System.out.print("\n executed method: " + implMethod.getName());
                for (Object arg : args) {
                    System.out.print(", param: " + arg);
                }
            }
            return method.invoke(testLoggingClass, args);
        }
    }
}
