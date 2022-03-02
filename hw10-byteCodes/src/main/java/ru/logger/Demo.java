package ru.logger;

public class Demo {

    public static void main(String[] args) {
        TestLogging testLogging = Proxy.createTestLoggingClass();
        testLogging.calculation(6);
        testLogging.calculation(10, 34);
        testLogging.calculation(7, 8, "Calculation");
    }
}
