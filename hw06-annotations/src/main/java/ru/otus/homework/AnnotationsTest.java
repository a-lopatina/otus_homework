package ru.otus.homework;


import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

class AnnotationsTest {

    @Before
    void beforeEachTestFirst(){
        System.out.println("Выполняется первый before метод");
    }

    @Before
    void beforeEachTestSecond(){
        System.out.println("Выполняется второй before метод");
    }

    @Test
    void firstTest() {
        System.out.println("Выполняется первый тест");
    }

    @Test
    void secondTest() {
        System.out.println("Выполняется второй тест");
        throw new RuntimeException("Второй тест выбросил исключение");
    }

    @Test
    void thirdTest() {
        System.out.println("Выполняется третий тест");
    }

    @After
    void afterEachTestFirst(){
        System.out.println("Выполняется первый after метод");
    }

    @After
    void afterEachTestSecond(){
        System.out.println("Выполняется второй after метод");
    }
}