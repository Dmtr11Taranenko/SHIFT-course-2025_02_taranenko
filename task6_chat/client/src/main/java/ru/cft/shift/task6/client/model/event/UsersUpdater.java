package ru.cft.shift.task6.client.model.event;

import ru.cft.shift.task6.client.model.ClientObserver;
import ru.cft.shift.task6.common.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Dmitrii Taranenko
 */
public class UsersUpdater implements ClientEvent {
    private final List<String> users;

    public UsersUpdater(Message users) {
        this.users = new ArrayList<>(Arrays.asList(users.getText().split("/n")));
    }

    @Override
    public void startEvent(ClientObserver observer) {
        observer.onUpdateUsers(users);
    }
}
