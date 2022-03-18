package ru.otus;

import java.time.LocalDateTime;
import java.util.List;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.model.Message;
import ru.otus.processor.ProcessorWithException;
import ru.otus.processor.SwapProcessor;

public class HomeWork {

    public static void main(String[] args) {
        var processors = List.of(new SwapProcessor(),
                new ProcessorWithException(LocalDateTime::now));

        var complexProcessor = new ComplexProcessor(processors, ex -> System.out.println("A вот и исключение: " + ex));
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);
        System.out.println();
        complexProcessor.removeListener(listenerPrinter);
    }
}
