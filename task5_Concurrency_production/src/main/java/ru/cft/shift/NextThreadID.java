package ru.cft.shift;

/**
 * @author Dmitrii Taranenko
 */

public abstract class NextThreadID {
    private static volatile long nextId = 0;

    public static synchronized long getAndIncrementNextId() {
        return nextId++;
    }
}
