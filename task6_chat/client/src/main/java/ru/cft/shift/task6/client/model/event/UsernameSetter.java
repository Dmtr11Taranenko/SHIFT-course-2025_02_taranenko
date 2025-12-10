package ru.cft.shift.task6.client.model.event;

import ru.cft.shift.task6.client.model.ClientObserver;

/**
 * @author Dmitrii Taranenko
 */
public class UsernameSetter implements ClientEvent {
    @Override
    public void startEvent(ClientObserver observer) {
        observer.onSetUserName();
    }
}
