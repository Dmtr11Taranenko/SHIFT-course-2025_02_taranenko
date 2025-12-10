package ru.cft.shift.util;

import lombok.Data;

import java.util.function.Consumer;

/**
 * @author Dmitrii Taranenko
 */
@Data
public class Timer {
    private final javax.swing.Timer timer;
    private long sec;

    public Timer(Consumer<Long> timeUpdateCallback) {
        this.timer = new javax.swing.Timer(1000, e -> {
            sec++;
            timeUpdateCallback.accept(sec);
        });
    }

    public void start() {
        sec = 0;
        timer.start();
    }

    public void stop() {
        timer.stop();
    }
}
