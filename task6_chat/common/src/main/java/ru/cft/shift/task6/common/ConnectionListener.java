package ru.cft.shift.task6.common;

/**
 * @author Dmitrii Taranenko
 */
public interface ConnectionListener {
    void onConnectionReady(Connection connection);

    void onReceiveObject(Connection connection, Message message);

    void onDisconnected(Connection connection);

    void onException(Connection connection, Exception e);
}
