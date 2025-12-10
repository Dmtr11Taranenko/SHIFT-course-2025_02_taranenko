package ru.cft.shift.task6.client.model;

import ru.cft.shift.task6.client.model.event.ClientEvent;

/**
 * @author Dmitrii Taranenko
 */
public interface ObservableClient {
    void addListener(ClientObserver listener);

    void removeListener(ClientObserver listener);

    void notifyListeners(ClientEvent event);
}
