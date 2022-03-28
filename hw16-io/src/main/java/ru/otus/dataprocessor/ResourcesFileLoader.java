package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        List<Measurement> measurementList;
        final Type reviewType = new TypeToken<List<Measurement>>() {}.getType();
        try (var jsonReader = new JsonReader(new InputStreamReader(ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName)))) {
            measurementList = new Gson().fromJson(jsonReader, reviewType);
        } catch (IOException e) {
           throw new FileProcessException(e.getMessage());
        }
        return measurementList;
    }
}
