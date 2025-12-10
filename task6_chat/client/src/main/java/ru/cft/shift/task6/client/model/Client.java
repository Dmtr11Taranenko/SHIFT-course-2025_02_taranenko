package ru.cft.shift.task6.client.model;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.client.model.event.*;
import ru.cft.shift.task6.common.Connection;
import ru.cft.shift.task6.common.ConnectionListener;
import ru.cft.shift.task6.common.Message;
import ru.cft.shift.task6.common.MessageType;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitrii Taranenko
 */
public class Client implements ObservableClient, ConnectionListener {
    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private final List<ClientObserver> listeners = new ArrayList<>();

    private Connection connection;
    private Socket socket;
    private String serverAddress;
    private int serverPort;

    @Setter
    private String userName;

    public void initClient() {
        notifyListeners(new ClientInitializer());
    }

    public void setUsernameClient() {
        notifyListeners(new UsernameSetter());
    }

    public void connectToServer(String serverAddress, int serverPort) throws IOException {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.socket = new Socket(serverAddress, serverPort);
    }

    public void startClient() {
        try {
            if (socket == null || socket.isClosed()) {
                throw new IOException("Server not found");
            }

            this.connection = new Connection(this, socket);
            Message authMessage = new Message(userName, null, MessageType.AUTH_REQUEST);
            connection.sendObject(authMessage);

        } catch (IOException e) {
            LOG.error("Connection failed", e);
            JOptionPane.showMessageDialog(null, "Connection failed", "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            initClient();
            startClient();

        } catch (Exception e) {
            LOG.error("Unexpected error during client startup", e);
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(500);
        }
    }

    public void sendMessage(String msg) {
        Message message = new Message(userName, msg, MessageType.TEXT_MESSAGE);
        connection.sendObject(message);
        notifyListeners(new MessageSender(message));
    }

    @Override
    public void addListener(ClientObserver listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ClientObserver listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifyListeners(ClientEvent event) {
        listeners.forEach(event::startEvent);
    }

    @Override
    public void onConnectionReady(Connection connection) {
        LOG.debug("Connection is ready");
        notifyListeners(new ClientStarter());
    }

    @Override
    public void onReceiveObject(Connection connection, Message message) {
        switch (message.getType()) {
            case AUTH_ACCEPTED -> {
                LOG.info("Authentication is successful");
                notifyListeners(new MessageReceiver(message));
            }
            case AUTH_REJECTED -> {
                JOptionPane.showMessageDialog(null, "Client with same name already exists",
                        "Error", JOptionPane.WARNING_MESSAGE);
                setUsernameClient();
                Message renameUser = new Message(userName, null, MessageType.AUTH_REQUEST);
                connection.sendObject(renameUser);
            }
            case TEXT_MESSAGE -> {
                notifyListeners(new MessageReceiver(message));
            }
            case INFO_MESSAGE -> {
                notifyListeners(new UsersUpdater(message));
            }
        }
    }

    @Override
    public void onDisconnected(Connection connection) {
        Message message = new Message("Connection: ", "close", MessageType.TEXT_MESSAGE);
        notifyListeners(new MessageReceiver(message));
        LOG.info("Connection with server was closed");
        LOG.debug("Try to reconnect to server");
        try {
            socket.close();
            reconnect();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void onException(Connection connection, Exception e) {
        Message message = new Message(e.getMessage(), e.toString(), MessageType.TEXT_MESSAGE);
        notifyListeners(new MessageReceiver(message));
        LOG.error(e.getMessage(), e);
    }

    private void reconnect() {
        while (socket.isClosed()) {
            try {
                Thread.sleep(1000);

                this.socket = new Socket(serverAddress, serverPort);
                startClient();

            } catch (IOException | InterruptedException ignored) {
            }
        }
    }
}
