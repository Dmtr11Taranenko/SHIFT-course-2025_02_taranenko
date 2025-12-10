package ru.cft.shift.task6.client.view.creds;

import ru.cft.shift.task6.client.model.ClientObserver;
import ru.cft.shift.task6.client.view.client.ClientWindow;

import java.util.List;

/**
 * @author Dmitrii Taranenko
 */
public class ServerCredWindowUpdater extends ServerCredWindow implements ClientObserver {

    public ServerCredWindowUpdater(ClientWindow clientWindow, ServerCredListener credListener) {
        super(clientWindow, credListener);
    }

    @Override
    public void onInitClient() {
        setVisible(true);
    }

    @Override
    public void onSetUserName() {
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
