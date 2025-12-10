package ru.cft.shift.task6.client.model.event;

import ru.cft.shift.task6.client.model.ClientObserver;

/**
 * @author Dmitrii Taranenko
 */
public interface ClientEvent {
    void startEvent(ClientObserver observer);
}
