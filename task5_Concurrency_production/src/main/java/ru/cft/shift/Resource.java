package ru.cft.shift;

import lombok.Getter;

/**
 * @author Dmitrii Taranenko
 */
@Getter
public class Resource {
    private static volatile long nextId = 0;
    private static final Object monitor = new Object();

    private final long id;

    public Resource() {
        synchronized (monitor) {
            id = nextId++;
        }
    }
}
