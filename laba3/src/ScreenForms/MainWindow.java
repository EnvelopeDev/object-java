package ScreenForms;

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
public class MainWindow
{
    private static JFrame mainFrame;
    private static JTable dogsTable;
    private static JPanel buttonsPanel;
    private static JPanel inputPanel;
    private static JTextField textField;
    private static JButton[] buttons;
    private static JScrollPane tableScrollPane;
    private static String[] tooltips = {"Save", "Open", "Backup", "Add", "Remove", "Edit", "Print", "Dropout", "Search"};
    
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
        mainFrame = new JFrame("Dog Festival");
        ImageIcon icon = new ImageIcon("picts/dogIcon.png");
        mainFrame.setIconImage(icon.getImage());

        // Create panels for buttons and input
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));
        
        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
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
        dogsTable = new JTable(tableModel);
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
        
        tableScrollPane = new JScrollPane(dogsTable);
        
        // Create search text field
        textField = new JTextField(12); 
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

        buttons = new JButton[9];

        // Create and configure buttons
        for(int i = 0; i < 9; i++) 
        {		    
            ImageIcon buttonIcon = new ImageIcon(imagePaths[i]);
            Image scaledImage = buttonIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            final int buttonIndex = i; // need final for lambda
            
            buttons[i] = new JButton(scaledIcon);
            buttons[i].setToolTipText(tooltips[i]); 
            buttons[i].setBorderPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(e -> handleButtonClick(buttonIndex));
            
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
        mainFrame.add(buttonsPanel, BorderLayout.NORTH);          
        mainFrame.add(tableScrollPane, BorderLayout.CENTER);   
        mainFrame.add(inputPanel, BorderLayout.SOUTH);            
        
        // Configure frame properties
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        
        // Save data back to CSV file
        fm.outputToCSV("data/dogs3.csv", dogs);
    }
    
    private static void handleButtonClick(int buttonIndex)
    {
        if (buttonIndex < tooltips.length) 
        {
            String buttonName = tooltips[buttonIndex];
            
            switch(buttonIndex) {
                case 7: 
                    exitApplication();
                    break;
                case 3: // Add button
                case 4: // Remove button  
                case 5: // Edit button
                case 8: // Search button
                default:
                	new ToolWindow(buttonName, null);
            }
        }
    }
    
    private static void exitApplication() {
        JPanel panel = new JPanel(new BorderLayout());
        
        ImageIcon exitIcon = new ImageIcon("picts/exit.jpg");
        Image scaledImage = exitIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
 
        JLabel textLabel = new JLabel("Are you sure you want to exit?", JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(textLabel, BorderLayout.SOUTH);
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 16));

        int result = JOptionPane.showConfirmDialog(
            mainFrame, //parent window
            panel,  //message
            "Confirm Exit", //window title
            JOptionPane.YES_NO_OPTION, //type of buttons
            JOptionPane.PLAIN_MESSAGE //type of message
        );
        
        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}