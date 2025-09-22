package lab3;

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
         
        JFrame frame = new JFrame("Dog Festival - Администратор");
        ImageIcon icon = new ImageIcon("src/picts/dogIcon.png");
        frame.setIconImage(icon.getImage());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 7, 10, 10));
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        JTextField textField = new JTextField(12); 
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        
        String[] columnNames = {
            "Кличка", "Порода", "Есть ли награда"
        };
        
        String[][] data = new String[dogs.getSize()][];
        String[] strListDogs = dogs.convToStr();
        
        for(int i=0;i<dogs.getSize();i++) 
        {
        	data[i] = strListDogs[i].split(";");
        	System.out.println(data[i][2]);
        	if(Integer.parseInt(data[i][2]) == 1) 
        	{
        		data[i][2] = "Есть";
        	}
        	else 
        	{
        		data[i][2] = "Нет";
        	}
        }
        
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable dogsTable = new JTable(tableModel);
        
        dogsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        dogsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        dogsTable.setRowHeight(25);
        
        JScrollPane tableScrollPane = new JScrollPane(dogsTable);
        
        String[] imagePaths = {
            "src/picts/save.png",
            "src/picts/folder_documents.png",
            "src/picts/cloud.png",
            "src/picts/plus.png", 
            "src/picts/minus.png",
            "src/picts/edit.png",
            "src/picts/print.png",
            "src/picts/search.png" 
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
        fm.outputToCSV("src/data/dogs.csv", dogs);
    }
}
