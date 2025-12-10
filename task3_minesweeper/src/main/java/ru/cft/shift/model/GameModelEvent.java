package ru.cft.shift.model;

import lombok.Getter;

/**
 * @author Dmitrii Taranenko
 */
@Getter
public class GameModelEvent {
    private final GameEventType eventType;
    private final int[] data;

    public GameModelEvent(GameEventType eventType) {
        this(eventType, null);
    }

    public GameModelEvent(GameEventType eventType, int[] data) {
        this.eventType = eventType;
        this.data = data;
    }
}
