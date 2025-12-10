package ru.cft.shift.task6.client.view.username;

import ru.cft.shift.task6.client.model.ClientObserver;
import ru.cft.shift.task6.client.view.client.ClientWindow;

import java.util.List;

/**
 * @author Dmitrii Taranenko
 */
public class UsernameWindowUpdater extends UsernameWindow implements ClientObserver {

    public UsernameWindowUpdater(ClientWindow clientWindow, UsernameListener nameListener) {
        super(clientWindow, nameListener);
    }

    @Override
    public void onSetUserName() {
        setVisible(true);
    }

    @Override
    public void onInitClient() {
    }

    @Override
    public void onStartedClient() {
    }

    @Override
    public void onMessageReceived(String userName, String sentTime, String message) {
    }

    @Override
    public void onMessageSent(String userName, String sentTime, String message) {
    }

    @Override
    public void onUpdateUsers(List<String> users) {
    }
}
