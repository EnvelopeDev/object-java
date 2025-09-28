package ScreenForms;

import javax.swing.*;
import java.awt.*;

public class DeleteW {
    public static void show(String[] data) {
        if (data == null) {
            return; // Пользователь отменил ввод
        }

        JFrame deleteFrame = new JFrame("Delete Confirmation");
        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Устанавливаем иконку
        ImageIcon icon = new ImageIcon("src/picts/dogIcon.png");
        deleteFrame.setIconImage(icon.getImage());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Сообщение с данными для удаления
        StringBuilder message = new StringBuilder("Are you sure you want to delete?\n\n");
        for (int i = 0; i < data.length; i++) {
            message.append("Field ").append(i + 1).append(": ").append(data[i]).append("\n");
        }

        JLabel messageLabel = new JLabel("<html>" + message.toString().replace("\n", "<br>") + "</html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton confirmButton = new JButton("Confirm Delete");
        JButton cancelButton = new JButton("Cancel");
        
        confirmButton.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
        
        confirmButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(deleteFrame, "Data deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            deleteFrame.dispose();
        });
        
        cancelButton.addActionListener(e -> deleteFrame.dispose());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(messageLabel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        deleteFrame.add(mainPanel);
        deleteFrame.pack();
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setVisible(true);
    }
}