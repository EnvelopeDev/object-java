package ScreenForms;

import fileManager.FileManager;
import ScreenForms.PrinterWindow;
import ScreenForms.DeleteElementWindow;
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
	private static ImageIcon icon;
    private static JFrame mainFrame;
    private static JTable dogsTable;
    private static String[][] tableData;
    private static JPanel buttonsPanel;
    private static JPanel inputPanel;
    private static JButton[] buttons;
    private static String[] tooltips = {
    		"Save",
    		"Open",
    		"Backup",
    		"Add",
    		"Remove",
    		"Edit",
    		"Print",
    		"Dropout",
    		"Search"};
    private static String[] imagePaths = {
            "scr/picts/save.png",
            "scr/picts/folder_documents.png",
            "scr/picts/cloud.png",
            "scr/picts/plus.png", 
            "scr/picts/minus.png",
            "scr/picts/edit.png",
            "scr/picts/print.png",
            "scr/picts/exit.png",
            "scr/picts/search.png",
            "scr/picts/dogIcon.png",
            "scr/picts/exit.jpg"
            
        };
    private static String[] columnNames = {
            "№",
            "Name",
            "Breed",
            "Awards"
        };
    /**
     * Displays the main application window with dog data
     * @throws IOException if there's an error reading/writing files
     */
    public MainWindow() throws IOException
    {
    	//INITIALIZATION SECTION
    	// Initialize file manager and load dog data from CSV
        FileManager fm = new FileManager();
        List<Dog> dogs = new List<>();
        dogs = fm.inputFromCSV("scr/data/dogs3.csv");
        
        icon = new ImageIcon(imagePaths[9]);
        mainFrame = new JFrame("Dog Festival");
        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        //BUTTON SECTION
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));
        
        buttons = new JButton[9];
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
            if (i < 8) {
            	buttonsPanel.add(buttons[i]);	
            }          
        }
        
        //TABLE SECTION
        // Convert dog data to table format
        tableData = new String[dogs.getSize()][4];
        String[] strListDogs = dogs.convToStr();
        
        // Process each dog's data for display
        for(int i = 0; i < dogs.getSize(); i++) 
        {
        	String[] dogData = strListDogs[i].split(";");
        	tableData[i][0] = String.valueOf(i + 1); // Row number
            tableData[i][1] = dogData[0]; // Dog name
            tableData[i][2] = dogData[1]; // Dog breed
            tableData[i][3] = dogData[2]; // Dog awards
        }
        
        // Create table model
        DefaultTableModel tableModel = new DefaultTableModel(tableData, columnNames);
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
        
        JScrollPane tableScrollPane = new JScrollPane(dogsTable);
        
        //SEARCH SECTION
        JTextField textField = new JTextField(12); 
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        inputPanel.add(textField);
        inputPanel.add(buttons[8]);
        
        //MAINFRAME ADDING ELEMENTS SECTION
        mainFrame.setIconImage(icon.getImage());
        mainFrame.add(buttonsPanel, BorderLayout.NORTH);          
        mainFrame.add(tableScrollPane, BorderLayout.CENTER);   
        mainFrame.add(inputPanel, BorderLayout.SOUTH);            
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 500);
        mainFrame.setLocationRelativeTo(null);
    }
    
    private static void handleButtonClick(int buttonIndex)
    {
        if (buttonIndex < tooltips.length) 
        {
            String buttonName = tooltips[buttonIndex];
            
            switch(buttonIndex) {
	            case 3: // Add button
	                try {
	                    AddElementWindow addElem = new AddElementWindow(
	                        dogsTable,
	                        "Enter the data to add( 1 - Name; 2 - Breed;3 - Awards)",
	                        "Add Row"
	                    );
	                    
	                    addElem.addRowToTable();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                break;
	            case 4: 
	                try {
	                    DeleteElementWindow deleteElem = new DeleteElementWindow(
	                        dogsTable, 
	                        "Enter the row number to delete", 
	                        "Delete Row"
	                    );
	                    
	                    int row = deleteElem.getRow();
	                    if (row > 0) {
	                        deleteElem.deleteRowByNumber(row - 1); 
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                break;
	
	            case 5: 
	                try {
	                    EditElementWindow editElem = new EditElementWindow(
	                        dogsTable,
	                        "Enter the row number to edit",
	                        "Edit Row", 
	                        1
	                    );
	                    
	                    int row = editElem.getRow();
	                    if (row > 0) {
	
	                        EditElementWindow editDataWindow = new EditElementWindow(
	                            dogsTable,
	                            "Enter new values: 1 - Name, 2 - Breed, 3 - Awards",
	                            "Edit Data", 
	                            3
	                        );
	                        editDataWindow.EditRowByNumber(row - 1); 
	                    }
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                break;
	                case 6:
	                    try {
	                        PrinterWindow.show();
	                    } catch (IOException e) {
	                    }
	                    break;
	                case 7: 
	                    exitApplication();
	                    break;

            }
        }
    }
    
    private static void exitApplication() 
    {
        JPanel exitPanel = new JPanel(new BorderLayout());
        
        ImageIcon exitImage = new ImageIcon(imagePaths[10]);
        Image scaledImage = exitImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
 
        JLabel textLabel = new JLabel("Are you sure you want to exit?", JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        exitPanel.add(imageLabel, BorderLayout.CENTER);
        exitPanel.add(textLabel, BorderLayout.SOUTH);
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 16));

        int result = JOptionPane.showConfirmDialog(
            mainFrame, //parent window
            exitPanel,  //message
            "Confirm Exit", //window title
            JOptionPane.YES_NO_OPTION, //type of buttons
            JOptionPane.PLAIN_MESSAGE //type of message
        );

        if (result == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }
    

    public static void show() throws IOException
    {
        mainFrame.setVisible(true);
    }
}