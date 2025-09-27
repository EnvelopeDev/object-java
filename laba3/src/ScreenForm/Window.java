package ScreenForm;

import fileManager.FileManager;
import java.io.IOException;
import list.List;
import object.dog.Dog;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Color;

/**
 * Main window class for the Dog Festival application
 * Handles GUI creation and user interface components
 * @author Vadim Ustinov
 * @version 1.0
 */
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
    
    /**
     * Displays the main application window with dog data
     * @throws IOException if there's an error reading/writing files
     */
	public static void show() throws IOException
	{
		// Initialize file manager and load dog data from CSV
		FileManager fm = new FileManager();
        List<Dog> dogs = new List<>();
        dogs = fm.inputFromCSV("data/dogs3.csv");
         
        // Create main application frame
        JFrame frame = new JFrame("Dog Festival");
        ImageIcon icon = new ImageIcon("picts/dogIcon.png");
        frame.setIconImage(icon.getImage());

        // Create panels for buttons and input
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        // Define table column names with row numbers column
        String[] columnNamesWithNumbers = {
            "№", "Name", "Breed", "Awards"
        };
        
        // Convert dog data to table format with row numbers
        String[][] dataWithNumbers = new String[dogs.getSize()][4];
        String[] strListDogs = dogs.convToStr();
        
        // Process each dog's data for display with row numbers
        for(int i = 0; i < dogs.getSize(); i++) 
        {
            dataWithNumbers[i][0] = String.valueOf(i + 1); // Row number
            String[] dogData = strListDogs[i].split(";");
            dataWithNumbers[i][1] = dogData[0]; // Dog name
            dataWithNumbers[i][2] = dogData[1]; // Dog breed
            
            // Convert award indicator to readable format
            if(Integer.parseInt(dogData[2]) == 1) 
            {
                dataWithNumbers[i][3] = "Yes";
            }
            else 
            {
                dataWithNumbers[i][3] = "No";
            }
        }
        
        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(dataWithNumbers, columnNamesWithNumbers);
        JTable dogsTable = new JTable(tableModel);
        dogsTable.setDefaultEditor(Object.class, null);
        
        // Configure first column
        TableColumnModel columnModel = dogsTable.getColumnModel();
        TableColumn numberColumn = columnModel.getColumn(0);
        numberColumn.setMaxWidth(35); 
        
        // Center align row numbers
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //creating a render for column
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); //Adjusting the text alignment of the render
        numberColumn.setCellRenderer(centerRenderer); //Applying a renderer to a column
        
        // Configure table appearance
        dogsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        dogsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        dogsTable.getTableHeader().setReorderingAllowed(false); //prohibition to move columns
        dogsTable.setRowHeight(25);
        
        JScrollPane tableScrollPane = new JScrollPane(dogsTable);
        
        // Create search text field
        JTextField textField = new JTextField(12); 
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(textField);
        
        // Define button images and tooltips
        String[] imagePaths = {
            "picts/save.png",
            "picts/folder_documents.png",
            "picts/cloud.png",
            "picts/plus.png", 
            "picts/minus.png",
            "picts/edit.png",
            "picts/print.png",
            "picts/exit.png",
            "picts/search.png"
        };

        String[] tooltips = {
            "Save",
            "Open",
            "Backup",
            "Add",
            "Remove",
            "Edit",
            "Print",
            "Dropout",
            "Search"
        };

        JButton[] buttons = new JButton[9];

        // Create and configure buttons
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
        
        // Add components to frame
        frame.add(buttonsPanel, BorderLayout.NORTH);          
        frame.add(tableScrollPane, BorderLayout.CENTER);   
        frame.add(inputPanel, BorderLayout.SOUTH);            
        
        // Configure frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Save data back to CSV file
        fm.outputToCSV("data/dogs3.csv", dogs);
	}
}