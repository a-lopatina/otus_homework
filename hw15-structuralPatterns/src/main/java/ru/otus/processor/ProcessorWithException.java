package ru.otus.processor;


import java.time.LocalDateTime;
import ru.otus.model.Message;

public class ProcessorWithException implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorWithException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        LocalDateTime dateTime = dateTimeProvider.getDate();
        System.out.println("Время запуска теста: " + dateTime);
        if (dateTime.getSecond() % 2 == 0) {
            throw new RuntimeException("Выбрасываем исключение, потому что секунда четная");
        } else {
            System.out.println("Секунда нечетная");
            return message;
        }
    }

}
