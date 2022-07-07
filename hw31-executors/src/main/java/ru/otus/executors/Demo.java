package ru.otus.executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Demo {
    private static final Logger logger = LoggerFactory.getLogger(Demo.class);
    private volatile String lastThreadName = "Thread-1";

    public static void main(String[] args) {
        Demo demo = new Demo();
        new Thread(demo::printNumbers).start();
        new Thread(demo::printNumbers).start();
    }

    private synchronized void printNumbers() {
        int number = 0;
        for (int i = 1; i < 20; i++) {
            try {
                if (lastThreadName.equals(Thread.currentThread().getName())) {
                    this.wait();
                }
                lastThreadName = Thread.currentThread().getName();
                sleep();
                notifyAll();

                if (i > 10) {
                    number--;
                } else number++;

                logger.info(String.valueOf(number));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
