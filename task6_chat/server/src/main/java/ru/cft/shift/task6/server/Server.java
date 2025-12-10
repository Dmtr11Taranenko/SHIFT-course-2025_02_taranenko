package ru.cft.shift.task6.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.cft.shift.task6.common.Connection;
import ru.cft.shift.task6.common.ConnectionListener;
import ru.cft.shift.task6.common.Message;
import ru.cft.shift.task6.common.MessageType;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;

/**
 * @author Dmitrii Taranenko
 */
public class Server implements ConnectionListener {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final ConcurrentMap<Connection, String> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new Server().startServer();
    }

    private void startServer() {
        final PropertiesReader propertiesReader = new PropertiesReader("serverAddress.properties");
        final String address = propertiesReader.getProperties().getProperty("ipAddress");
        final int port = Integer.parseInt(propertiesReader.getProperties().getProperty("port"));
        final int backlog = Integer.parseInt(propertiesReader.getProperties().getProperty("backlog"));

        try (ServerSocket serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(address))) {

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Connection(this, clientSocket);
            }

        } catch (IOException ignored) {
        }
    }

    @Override
    public synchronized void onConnectionReady(Connection connection) {
        LOG.info("Client connected: {}", connection);
    }

    @Override
    public synchronized void onReceiveObject(Connection connection, Message message) {
        switch (message.getType()) {
            case AUTH_REQUEST -> sendAuthRequest(connection, message);
            case TEXT_MESSAGE -> sendTextMessage(connection, message);
        }
    }

    @Override
    public synchronized void onDisconnected(Connection connection) {
        String client = clients.get(connection);
        clients.remove(connection);
        LOG.info("Client disconnected: {}", client);
        sendToAllClients("Client disconnected: " + client, connection);
        sendClientList();
    }

    @Override
    public synchronized void onException(Connection connection, Exception e) {
    }

    private synchronized void sendToAllClients(String msg, Connection connection) {
        sendToAllClients(msg, "Server", connection);
    }

    private synchronized void sendToAllClients(String msg, String userName, Connection connection) {
        Message message = new Message(userName, msg, MessageType.TEXT_MESSAGE);

        clients.keySet().stream()
                .filter(Predicate.not(connection::equals))
                .forEach(client -> client.sendObject(message));
    }

    private synchronized void sendTextMessage(Connection connection, Message message) {
        sendToAllClients(message.getText(), message.getUserName(), connection);
    }

    private synchronized void sendAuthRequest(Connection connection, Message message) {
        String requestedName = message.getUserName();

        if (clients.containsValue(requestedName)) {
            Message rejection = new Message("Server",
                    "Name '" + requestedName + "' already taken. Choose another one", MessageType.AUTH_REJECTED);
            connection.sendObject(rejection);
            LOG.info("Connection with client '{}' was rejected - user with same name already exists.", connection);

        } else {
            clients.put(connection, requestedName);
            Message acceptance = new Message("Server",
                    "Welcome to ChatApp, " + requestedName + "!", MessageType.AUTH_ACCEPTED);
            connection.sendObject(acceptance);

            sendToAllClients(requestedName + " joined chat", connection);
            sendClientList();

            LOG.info("Client '{}' successfully authenticated", requestedName);
        }
    }

    private void sendClientList() {
        Message clientsList = new Message("Server", String.join("/n", clients.values()), MessageType.INFO_MESSAGE);
        clients.keySet().forEach(con -> con.sendObject(clientsList));
    }
}
