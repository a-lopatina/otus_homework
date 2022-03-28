package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        Map<String, Double> sortedData = new TreeMap<>(data);
        final Type reviewType = new TypeToken<Map<String, Double>>() {}.getType();
        try (var jsonWriter = new JsonWriter(new OutputStreamWriter(new FileOutputStream(fileName)))) {
            new Gson().toJson(sortedData, reviewType, jsonWriter);
        } catch (IOException e) {
            throw new FileProcessException(e.getMessage());
        }
    }
}
