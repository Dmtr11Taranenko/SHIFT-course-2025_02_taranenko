package ru.cft.shift.task6.client.view.username;

import ru.cft.shift.task6.client.view.client.ClientWindow;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

/**
 * @author Dmitrii Taranenko
 */
public class UsernameWindow extends JDialog {
    private UsernameListener nameListener;

    public UsernameWindow(ClientWindow clientWindow, UsernameListener nameListener) {
        super(clientWindow, "Username", true);
        this.nameListener = nameListener;

        JTextField nameField = new JTextField();
        AbstractDocument doc = (AbstractDocument) nameField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                if (string.trim().isEmpty()) return;
                string = string.replaceAll("[^a-zA-Zа-яА-Я\\s\\-']", "*");
                super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                if (text.trim().isEmpty()) return;
                text = text.replaceAll("[^a-zA-Zа-яА-Я\\s\\-']", "*");
                super.replace(fb, offset, length, text, attrs);
            }
        });

        GridLayout layout = new GridLayout(3, 2);
        Container contentPane = getContentPane();
        contentPane.setLayout(layout);

        contentPane.add(new JLabel("Enter your name"));
        contentPane.add(nameField);
        contentPane.add(createNameButton(nameField));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 120));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private JButton createNameButton(JTextField nameField) {
        JButton button = new JButton("OK");
        button.addActionListener(e -> {

            if (nameListener != null) {
                nameListener.onUserNameEntered(nameField.getText());
            }

            dispose();
        });
        return button;
    }
}
