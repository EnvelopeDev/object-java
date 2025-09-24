package ScreenForm;

import fileManager.FileManager;
import java.io.IOException;
import list.List;
import object.dog.Dog;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class Window
{
	private JFrame dogFestival;
    private DefaultTableModel model;
    private JScrollPane scroll;
    private JTable dogsTable;
    private JPanel buttonsPanel;
    private JPanel inputPanel;
    private JTextField textField;
    private JButton[] buttons;
    
	public static void show() throws IOException
	{
		FileManager fm = new FileManager();
        List<Dog> dogs = new List<>();
        dogs = fm.inputFromCSV("src/data/dogs.csv");
         
        JFrame frame = new JFrame("Dog Festival");
        ImageIcon icon = new ImageIcon("src/picts/dogIcon.png");
        frame.setIconImage(icon.getImage());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        String[] columnNames = {
            "Кличка", "Порода", "Наличие наград"
        };
        
        String[][] data = new String[dogs.getSize()][];
        String[] strListDogs = dogs.convToStr();
        
        for(int i=0;i<dogs.getSize();i++) 
        {
        	data[i] = strListDogs[i].split(";");
        	if(Integer.parseInt(data[i][2]) == 1) 
        	{
        		data[i][2] = "Да";
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
        

        JTextField textField = new JTextField(12); 
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(textField);
        
        String[] imagePaths = {
            "src/picts/save.png",
            "src/picts/folder_documents.png",
            "src/picts/cloud.png",
            "src/picts/plus.png", 
            "src/picts/minus.png",
            "src/picts/edit.png",
            "src/picts/print.png",
            "src/picts/exit.png",
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
            "dropout",
            "Search"
        };

        JButton[] buttons = new JButton[9];

        for(int i = 0; i < 9; i++) 
        {		    
            ImageIcon buttonIcon = new ImageIcon(imagePaths[i]);
            Image scaledImage = buttonIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            
            buttons[i] = new JButton(scaledIcon);
            buttons[i].setToolTipText(tooltips[i]); 
            buttons[i].setBorderPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setFocusPainted(false);
            
            if(i == 8) 
            {
                inputPanel.add(buttons[i]); 
            } 
            else 
            {
                buttonsPanel.add(buttons[i]);
            }
        }
        
        frame.add(buttonsPanel, BorderLayout.NORTH);          
        frame.add(tableScrollPane, BorderLayout.CENTER);   
        frame.add(inputPanel, BorderLayout.SOUTH);            
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        fm.outputToCSV("src/data/dogs.csv", dogs);
	}
}