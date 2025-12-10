package ru.cft.shift.task6.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Dmitrii Taranenko
 */
public class Connection {
    private static final Logger LOG = LoggerFactory.getLogger(Connection.class);

    private final Socket socket;
    private final Thread thread;
    private final ConnectionListener connectionListener;
    private final DataInputStream reader;
    private final DataOutputStream writer;
    private final ObjectMapper mapper;

    public Connection(ConnectionListener connectionListener, Socket socket) throws IOException {
        this.socket = socket;
        this.connectionListener = connectionListener;
        this.writer = new DataOutputStream(socket.getOutputStream());
        this.reader = new DataInputStream(socket.getInputStream());
        this.mapper = new ObjectMapper();

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    connectionListener.onConnectionReady(Connection.this);

                    while (!thread.isInterrupted()) {
                        Message message = receiveMessage();

                        if (message != null) {
                            connectionListener.onReceiveObject(Connection.this, message);
                        }
                    }
                } catch (IOException e) {
                    connectionListener.onException(Connection.this, e);

                } finally {
                    connectionListener.onDisconnected(Connection.this);
                }
            }
        });
        thread.start();
    }

    public synchronized void sendObject(Message message) {
        try {
            byte[] jsonBytes = mapper.writeValueAsBytes(message);

            int length = jsonBytes.length;
            writer.writeInt(length);
            writer.write(jsonBytes);
            writer.flush();

        } catch (IOException e) {
            connectionListener.onException(Connection.this, e);
            disconnected();
        }
    }

    public synchronized void disconnected() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            connectionListener.onException(Connection.this, e);
        }
    }

    private Message receiveMessage() throws IOException {
        try {
            int length = reader.readInt();

            if (length <= 0 || length > 1024 * 1024) {
                LOG.error("Invalid message length: {}", length);
                return null;
            }

            byte[] messageBytes = new byte[length];
            reader.readFully(messageBytes);

            return mapper.readValue(messageBytes, Message.class);

        } catch (EOFException e) {
            return null;
        } catch (SocketException e) {
            throw e;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new SocketException(e.getMessage());
        }
    }
}
