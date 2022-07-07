package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    List<SensorData> bufferedData = new ArrayList<>();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public synchronized void process(SensorData data) {
        bufferedData.add(data);
        if (bufferedData.size() >= bufferSize) {
            flush();
        }
    }

    public synchronized void flush() {
        try {
            if (!bufferedData.isEmpty()) {
                bufferedData.sort(Comparator.comparing(SensorData::getMeasurementTime));
                writer.writeBufferedData(bufferedData);
                bufferedData = new ArrayList<>();
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public synchronized void onProcessingEnd() {
        flush();
    }
}
