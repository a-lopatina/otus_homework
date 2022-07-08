package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;
import ru.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final BlockingDeque<SensorData> bufferedData = new LinkedBlockingDeque<>();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        bufferedData.add(data);
        if (bufferedData.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        List<SensorData> dataBuffer = new ArrayList<>();
        try {
            bufferedData.drainTo(dataBuffer);
            if (!dataBuffer.isEmpty()) {
                dataBuffer.sort(Comparator.comparing(SensorData::getMeasurementTime));
                writer.writeBufferedData(dataBuffer);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
