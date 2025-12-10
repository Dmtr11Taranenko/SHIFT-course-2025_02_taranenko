package ru.cft.shift.task6.client;

import ru.cft.shift.task6.client.controller.Controller;
import ru.cft.shift.task6.client.model.Client;
import ru.cft.shift.task6.client.view.client.ClientWindowUpdater;
import ru.cft.shift.task6.client.view.creds.ServerCredWindowUpdater;
import ru.cft.shift.task6.client.view.username.UsernameWindowUpdater;

import java.io.IOException;

/**
 * @author Dmitrii Taranenko
 */
public class ClientApp {
    public static void main(String[] args) throws IOException {
        Client client = new Client();
        Controller controller = new Controller(client);

        ClientWindowUpdater clientWindowUpdater = new ClientWindowUpdater(controller);
        ServerCredWindowUpdater serverCredWindowUpdater = new ServerCredWindowUpdater(clientWindowUpdater, controller);
        UsernameWindowUpdater usernameWindowUpdater = new UsernameWindowUpdater(clientWindowUpdater, controller);

        client.addListener(clientWindowUpdater);
        client.addListener(serverCredWindowUpdater);
        client.addListener(usernameWindowUpdater);

        controller.setUsernameClient();
        controller.initClient();
        controller.startClient();
    }
}
