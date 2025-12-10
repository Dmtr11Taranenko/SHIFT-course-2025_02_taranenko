package ru.cft.shift.task6.client.view.client;

import ru.cft.shift.task6.client.model.ClientObserver;

import javax.swing.*;
import java.util.List;

/**
 * @author Dmitrii Taranenko
 */
public class ClientWindowUpdater extends ClientWindow implements ClientObserver {
    private final SendMessageListener listener;

    public ClientWindowUpdater(SendMessageListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStartedClient() {
        SwingUtilities.invokeLater(() -> {
            setListener(listener);
            setVisible(true);
        });
    }

    @Override
    public void onMessageSent(String userName, String sentTime, String message) {
        setTextFieldText("");
        if (!message.trim().isEmpty()) {
            printMessage(userName, sentTime, message);
        }
    }

    @Override
    public void onUpdateUsers(List<String> users) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addAll(users);
        addUsersToList(listModel);
    }

    @Override
    public void onMessageReceived(String userName, String sentTime, String message) {
        printMessage(userName, sentTime, message);
    }

    @Override
    public void onInitClient() {
    }

    @Override
    public void onSetUserName() {
    }

    private void printMessage(String userName, String sentTime, String message) {
        SwingUtilities.invokeLater(() -> {
            getMessageArea().append(sentTime + " - " + userName + ": " + message + " \n");
            int messageAreaLength = getMessageArea().getDocument().getLength();
            getMessageArea().setCaretPosition(messageAreaLength);
        });
    }

}
