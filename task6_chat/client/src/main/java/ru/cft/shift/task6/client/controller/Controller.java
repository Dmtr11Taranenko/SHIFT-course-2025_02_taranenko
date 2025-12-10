package ru.cft.shift.task6.client.controller;

import ru.cft.shift.task6.client.model.Client;
import ru.cft.shift.task6.client.view.client.SendMessageListener;
import ru.cft.shift.task6.client.view.creds.ServerCredListener;
import ru.cft.shift.task6.client.view.username.UsernameListener;

import java.io.IOException;

/**
 * @author Dmitrii Taranenko
 */
public class Controller implements SendMessageListener, ServerCredListener, UsernameListener {
    private final Client client;

    public Controller(Client client) {
        this.client = client;
    }

    public void initClient() {
        client.initClient();
    }

    public void setUsernameClient() {
        client.setUsernameClient();
    }

    public void startClient() {
        client.startClient();
    }

    @Override
    public void sendMessage(String messageText) {
        if (!messageText.trim().isEmpty()) {
            client.sendMessage(messageText);
        }
    }

    @Override
    public void onServerCredEntered(String address, String port) throws IOException {
        client.connectToServer(address, Integer.parseInt(port));
    }

    @Override
    public void onUserNameEntered(String name) {
        client.setUserName(name);
    }
}
