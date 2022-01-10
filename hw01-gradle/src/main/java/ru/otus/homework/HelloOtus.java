package ru.otus.homework;


import com.google.common.base.Joiner;
import java.util.HashMap;
import java.util.Map;


/**
 * To start the application:
 * ./gradlew build
 * java -jar ./hw01-gradle/build/libs/hw01-gradle-shadow-0.1.jar
 */
public class HelloOtus {
    public static void main(String... args) {
        Map<String, String> animalsSounds = new HashMap<>();
        animalsSounds.put("Собака", "гав");
        animalsSounds.put("Кошка", "мяу");
        animalsSounds.put("Утка", "кря");
        animalsSounds.put("Лягушка", "ква");
        String result = Joiner.on(", ")
                .withKeyValueSeparator(" говорит: ")
                .join(animalsSounds);
        System.out.println(result);
    }
}
