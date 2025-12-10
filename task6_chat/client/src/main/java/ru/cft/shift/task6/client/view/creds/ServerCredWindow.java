package ru.cft.shift.task6.client.view.creds;

import ru.cft.shift.task6.client.view.client.ClientWindow;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;

/**
 * @author Dmitrii Taranenko
 */
public class ServerCredWindow extends JDialog {
    private final ServerCredListener credListener;

    public ServerCredWindow(ClientWindow clientWindow, ServerCredListener credListener) {
        super(clientWindow, "Connection to chat server", true);
        this.credListener = credListener;
        JFormattedTextField addressField = null;
        JFormattedTextField portField = null;

        try {
            MaskFormatter ipFormatter = new MaskFormatter("###.###.###.###");
            ipFormatter.setPlaceholderCharacter('_');
            addressField = new JFormattedTextField(ipFormatter);

            MaskFormatter portFormatter = new MaskFormatter("####");
            portFormatter.setPlaceholderCharacter('_');
            portField = new JFormattedTextField(portFormatter);

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid server cred", "Error", JOptionPane.WARNING_MESSAGE);
        }

        GridLayout layout = new GridLayout(3, 2);
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(new JLabel("Enter server ip address"));
        contentPane.add(addressField);
        contentPane.add(new JLabel("Enter server port"));
        contentPane.add(portField);
        contentPane.add(createConnectButton(addressField, portField));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 120));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private JButton createConnectButton(JTextField addressField, JTextField portField) {
        JButton button = new JButton("Connect");
        button.addActionListener(e -> {
            dispose();

            if (credListener != null) {
                try {
                    credListener.onServerCredEntered(addressField.getText(), portField.getText());
                } catch (IOException ignored) {
                }
            }
        });
        return button;
    }
}
