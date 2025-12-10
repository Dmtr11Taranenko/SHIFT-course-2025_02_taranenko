package ru.cft.shift.task6.client.view.creds;

import java.io.IOException;

/**
 * @author Dmitrii Taranenko
 */
public interface ServerCredListener {
    void onServerCredEntered(String address, String port) throws IOException;
}
