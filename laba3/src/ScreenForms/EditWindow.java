package ScreenForms;

import javax.swing.*;
import java.awt.*;

public class EditWindow{
    public static void show(String[] data) {
        if (data == null) {
            return; // Пользователь отменил ввод
        }

        JFrame editFrame = new JFrame("Edit Data");
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Устанавливаем иконку
        ImageIcon icon = new ImageIcon("src/picts/dogIcon.png");
        editFrame.setIconImage(icon.getImage());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Отображаем полученные данные
        JTextArea dataArea = new JTextArea(10, 30);
        dataArea.setFont(new Font("Arial", Font.PLAIN, 14));
        dataArea.setEditable(false);
        
        StringBuilder sb = new StringBuilder("Received data:\n");
        for (int i = 0; i < data.length; i++) {
            sb.append("Field ").append(i + 1).append(": ").append(data[i]).append("\n");
        }
        dataArea.setText(sb.toString());

        JScrollPane scrollPane = new JScrollPane(dataArea);
        
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.addActionListener(e -> editFrame.dispose());

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        editFrame.add(mainPanel);
        editFrame.pack();
        editFrame.setLocationRelativeTo(null);
        editFrame.setVisible(true);
    }
}