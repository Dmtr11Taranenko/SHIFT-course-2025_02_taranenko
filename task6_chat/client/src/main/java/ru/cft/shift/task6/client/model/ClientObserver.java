package ru.cft.shift.task6.client.model;

import java.util.List;

/**
 * @author Dmitrii Taranenko
 */
public interface ClientObserver {
    void onInitClient();

    void onSetUserName();

    void onStartedClient();

    void onMessageReceived(String userName, String sentTime, String message);

    void onMessageSent(String userName, String sentTime, String message);

    void onUpdateUsers(List<String> users);
}
