package ru.cft.shift.task6.client.model.event;

import ru.cft.shift.task6.client.model.ClientObserver;
import ru.cft.shift.task6.common.Message;

/**
 * @author Dmitrii Taranenko
 */
public class MessageSender implements ClientEvent {
    private final Message message;

    public MessageSender(Message message) {
        this.message = message;
    }

    @Override
    public void startEvent(ClientObserver observer) {
        observer.onMessageSent(message.getUserName(), message.getSendTime(), message.getText());
    }
}
