package courseworkP2P;

import fileManager.InterfaceFM;
import fileManager.FileManager;
import list.InterfaceList;
import list.List;
import object.Dog;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Image;

public class DogPC
{

	public static void main(String[] args) throws IOException
    {
        FileManager fm = new FileManager();
        List<Dog> dogs = new List<>();
        
        dogs = fm.inputFromCSV("src/data/dogs.csv");
        dogs.print();
        fm.outputToCSV("src/data/dogs.csv", dogs);
         
        JFrame frame = new JFrame("Dog Festival - Администратор");
        ImageIcon icon = new ImageIcon("src/laba2/dogIcon.png");
        frame.setIconImage(icon.getImage());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 7, 10, 10));
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JTextField textField = new JTextField(12); 
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        
        String[] columnNames = {
            "Кличка", "Порода", "Владелец", "Судья", "Награда", "Породы судьи"
        };
        
        Object[][] data = {
            {"Барсик", "Такса", "Иванов И.И.", "Петров П.П.", "1 место", "Такса, Пудель"},
            {"Шарик", "Лабрадор", "Сидоров С.С.", "Козлов К.К.", "2 место", "Лабрадор, Овчарка"},
            {"Рекс", "Овчарка", "Петров П.П.", "Иванов И.И.", "3 место", "Овчарка, Такса"},
            {"Мухтар", "Пудель", "Козлов К.К.", "Сидоров С.С.", "Лучший в породе", "Пудель, Лабрадор"},
            {"Джек", "Такса", "Смирнов А.А.", "Петров П.П.", "Участвовал", "Такса, Пудель"},
            {"Лорд", "Лабрадор", "Кузнецов В.В.", "Козлов К.К.", "Приз зрителей", "Лабрадор, Овчарка"}
        };
        
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable dogsTable = new JTable(tableModel);
        
        dogsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        dogsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        dogsTable.setRowHeight(25);
        
        JScrollPane tableScrollPane = new JScrollPane(dogsTable);
        
        String[] imagePaths = {
            "src/laba2/save.png",
            "src/laba2/folder_documents.png",
            "src/laba2/cloud.png",
            "src/laba2/plus.png", 
            "src/laba2/minus.png",
            "src/laba2/edit.png",
            "src/laba2/print.png",
            "src/laba2/search.png" 
        };

        String[] tooltips = {
            "Save",
            "Open",
            "Backup",
            "Add",
            "Remove",
            "Edit",
            "Print",
            "Search"
        };

        JButton[] buttons = new JButton[8];

        for(int i = 0; i < 8; i++) {		    
            ImageIcon buttonIcon = new ImageIcon(imagePaths[i]);
            Image scaledImage = buttonIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            
            buttons[i] = new JButton(scaledIcon);
            buttons[i].setToolTipText(tooltips[i]); 
            buttons[i].setBorderPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setFocusPainted(false);
            
            if(i == 7) {
                inputPanel.add(buttons[i]); 
            } else {
                buttonsPanel.add(buttons[i]);
            }
        }
        
        inputPanel.add(textField);
        
        frame.add(buttonsPanel, BorderLayout.NORTH);          
        frame.add(tableScrollPane, BorderLayout.CENTER);   
        frame.add(inputPanel, BorderLayout.SOUTH);            
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setVisible(true);
    }

}
