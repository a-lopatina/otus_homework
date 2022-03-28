package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

public class HistoryListener implements Listener, HistoryReader {

    private final List<Message> messageList = new ArrayList<>();

    @Override
    public void onUpdated(Message msg) {
        ObjectForMessage objectForMessage = new ObjectForMessage();
        List<String> data = new ArrayList<>(msg.getField13().getData());
        objectForMessage.setData(data);
        messageList.add(msg.toBuilder().field13(objectForMessage).build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return messageList.stream().filter(message -> id == message.getId()).findAny();
    }
}
