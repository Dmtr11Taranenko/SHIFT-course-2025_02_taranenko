package ru.cft.shift.task6.client.model.event;

import ru.cft.shift.task6.client.model.ClientObserver;
import ru.cft.shift.task6.common.Message;

/**
 * @author Dmitrii Taranenko
 */
public class MessageReceiver implements ClientEvent {
    private final Message message;

    public MessageReceiver(Message message) {
        this.message = message;
    }

    @Override
    public void startEvent(ClientObserver observer) {
        observer.onMessageReceived(message.getUserName(), message.getSendTime(), message.getText());
    }
}
