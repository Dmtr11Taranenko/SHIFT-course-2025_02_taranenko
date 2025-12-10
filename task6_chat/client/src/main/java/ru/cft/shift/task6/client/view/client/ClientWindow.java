package ru.cft.shift.task6.client.view.client;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

/**
 * @author Dmitrii Taranenko
 */
@Getter
@Setter
public class ClientWindow extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private JList<String> userList;
    private JButton sendMessageButton;

    private SendMessageListener listener;

    public ClientWindow() {
        super("ChatApp");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        textField = new JTextField();
        textField.addActionListener(e -> listener.sendMessage(textField.getText()));

        sendMessageButton = new JButton("=>");
        sendMessageButton.addActionListener(e -> listener.sendMessage(textField.getText()));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(sendMessageButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        userList = new JList<>();
        add(new JScrollPane(userList), BorderLayout.WEST);
    }

    public void setTextFieldText(String text) {
        textField.setText(text);
    }

    public void addUsersToList(ListModel<String> users) {
        userList.setModel(users);
    }
}
