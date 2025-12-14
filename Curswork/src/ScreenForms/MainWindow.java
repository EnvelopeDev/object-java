package ScreenForms;

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

import java.io.IOException;

import fileManager.FileManager;          
import list.List;                       
import object.dog.Dog;    

/**
 * Main window class for the Dog Festival application
 * Handles GUI creation, user interface components, and application workflow
 * Provides functionality for displaying dog data, adding, editing, deleting records,
 * searching, and other table operations
 * @author Vadim Ustinov
 * @version 2.0
 */
public class MainWindow
{
	private static List<Dog> dogs;
	private static FileManager fileMngr;
	private ImageIcon icon; 
    private String[][] tableData;
    private JPanel buttonsPanel;
    private JPanel inputPanel;
    private JButton[] buttons;
    private JScrollPane tableScrollPane;
    private static JFrame mainFrame;
    private static JTextField searchTextField;
    private static String[][] originalTableData;
    private static DefaultTableModel tableModel;
    private static JTable dogsTable;
    private static Font defaultFont;
    private static Font headerFont;
    
    private static final String DOGS_FILE_PATH = "src/data/dogs.xml";
    
    /** Array of tooltips for application buttons */
    private static final String[] tooltips = {
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
    
    /** Array of image paths for button icons and application images */
    private static final String[] imagePaths = {
            "src/picts/save.png",
            "src/picts/folder_documents.png",
            "src/picts/cloud.png",
            "src/picts/plus.png", 
            "src/picts/minus.png",
            "src/picts/edit.png",
            "src/picts/print.png",
            "src/picts/exit.png",
            "src/picts/search.png",
            "src/picts/dogIcon.png",
            "src/picts/exit.jpg"
    };
    
    /** Array of column names for the data table */
    private static final String[] columnNames = {
            "№",
            "Name",
            "Breed",
            "Awards"
     };
    
    /**
     * Makes the main application window visible
     * Displays the initialized GUI components to the user
     */
    public void show()
    {
        mainFrame.setVisible(true);
    }
    
    /**
     * Constructs the main application window and initializes all components
     * Loads data from file, creates GUI elements, and sets up event handlers
     * @throws InputException if there's an error reading data files or initializing components
     */
    public MainWindow() throws InputException
    {
    	fileMngr = new FileManager();//init FileManager object
        dogs = new List<>();//init List for dogs data

    	//DATA INIT SECTION
    	initData();
    	
    	//STYLE SECTION
        initMainFrame();
        initFonts();
        
        //BUTTON SECTION
        initButtonsPanel();
        
        //TABLE SECTION
        initTable();
        
        //SEARCH SECTION
        initSearchPanel();
        
        //ASSEMBLE MAINFRAME SECTION
        assembleMainFrame();
    }
    
    /**
     * Initializes application data by loading dog information from XML file
     * Creates FileManager and List objects to store and manage dog data
     * @throws InputException if XML file cannot be read or contains invalid data
     */
    private void initData() throws InputException
    {
        try {
            dogs = fileMngr.inputFromXML(DOGS_FILE_PATH); //writes data from dogs3.XML
        } catch (Exception e) {
            throw new InputException("Error initializing data: " + e.getMessage(), 
                                   InputException.ErrorType.INVALID_FORMAT);
        }
    }
    
    /**
     * Initializes font settings for the application
     * Sets default font for regular text and header font for table headers
     */
    private void initFonts() 
    {
    	defaultFont = new Font("Arial", Font.PLAIN, 14);
        headerFont = new Font("Arial", Font.BOLD, 16);
    }
    
    /**
     * Initializes the main application frame
     * Sets window title, icon, size, and default close operation
     */
    private void initMainFrame()
    {
    	icon = new ImageIcon(imagePaths[9]);//init window icon
        mainFrame = new JFrame("Dog Festival");//init mainframe and init window title
        mainFrame.setIconImage(icon.getImage());
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 500);
        mainFrame.setLocationRelativeTo(null);
    }
    
    /**
     * Initializes the buttons panel with application control buttons
     * Creates buttons with icons, tooltips, and event handlers
     * Buttons include Save, Open, Backup, Add, Remove, Edit, Print, Dropout, and Search
     */
    private void initButtonsPanel()
    {
    	buttonsPanel = new JPanel();//init buttons panel
        buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));//set 1 row, 8 cols, 10 width, 10 height
        
        buttons = new JButton[9];//init buttons array
        for(int i = 0; i < 9; i++) 
        {		    
            ImageIcon imageForButton = new ImageIcon(imagePaths[i]);//init image for icon 
            Image scaledImage = imageForButton.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);//scale without loss of quality and fixed size of 32 by 32 px
            ImageIcon buttonIcon = new ImageIcon(scaledImage);//init icon for button
            final int buttonIndex = i; // need final for lambda function
            
            buttons[i] = new JButton(buttonIcon);//set icon 
            buttons[i].setToolTipText(tooltips[i]);//set button tooltips
            buttons[i].setBorderPainted(false);//remove the frame
            buttons[i].setContentAreaFilled(false);//set transparent background
            buttons[i].setFocusPainted(false);//Remove backlight when focusing
            buttons[i].addActionListener(e -> handleButtonClick(buttonIndex));//add click handler calls lambda that calls click logic
            if (i < 8) {
            	buttonsPanel.add(buttons[i]);//add button into buttonsPanel
            }          
        }
    }
    
    /**
     * Initializes the data table with dog information
     * Converts dog data from List to table format, sets up table model,
     * configures column properties and table appearance
     */
    private void initTable() 
    {
    	tableData = new String[dogs.getSize()][4];//init table data
        String[] strListDogs = dogs.convToStr();//get dogs data
        
        // Process each dog's data for display
        for(int i = 0; i < dogs.getSize(); i++) 
        {
        	String[] RowData = strListDogs[i].split(";");//split separator ;
        	tableData[i][0] = String.valueOf(i + 1); // Row number
            tableData[i][1] = RowData[0]; // Dog name
            tableData[i][2] = RowData[1]; // Dog breed
            tableData[i][3] = RowData[2]; // Dog awards
        }
        
        originalTableData = new String[tableData.length][4];//init reserve copy table data
        for (int i = 0; i < tableData.length; i++) {
            System.arraycopy(tableData[i], 0, originalTableData[i], 0, 4);//copy array (source_arr, start_i, target_arr, start_i, length_of_elements)
        }
        
        // Create table model
        tableModel = new DefaultTableModel(tableData, columnNames);//init table model
        dogsTable = new JTable(tableModel);//creating a data visualization 
        dogsTable.setDefaultEditor(Object.class, null);//prohibits editing of table cells
        
        // Configure first column
        TableColumnModel columnModel = dogsTable.getColumnModel();
        TableColumn numberColumn = columnModel.getColumn(0);
        numberColumn.setMaxWidth(35); 
        
        // Center align row numbers
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer(); //creating a render for column
        centerRenderer.setHorizontalAlignment(JLabel.CENTER); //Adjusting the text alignment of the render
        numberColumn.setCellRenderer(centerRenderer); //Applying a renderer to a column
        
        // Configure table appearance
        dogsTable.setFont(defaultFont);
        dogsTable.getTableHeader().setFont(headerFont);
        dogsTable.getTableHeader().setReorderingAllowed(false); //prohibition to move columns
        dogsTable.setRowHeight(25);
        
        tableScrollPane = new JScrollPane(dogsTable);
    }
    
    /**
     * Initializes the search panel with text field and search button
     * Provides functionality for filtering table data based on user input
     */
    private void initSearchPanel()
    {
    	inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));//init text panel for search
    	searchTextField = new JTextField(12);
        searchTextField.setFont(defaultFont);
        buttons[8].addActionListener(e -> handleButtonClick(8));
        inputPanel.add(searchTextField);
        inputPanel.add(buttons[8]); 
    }
    
    /**
     * Assembles all GUI components into the main application frame
     * Arranges buttons panel, table, and search panel in appropriate layout positions
     */
    private void assembleMainFrame()
    {
        mainFrame.add(buttonsPanel, BorderLayout.NORTH);          
        mainFrame.add(tableScrollPane, BorderLayout.CENTER);   
        mainFrame.add(inputPanel, BorderLayout.SOUTH);            
    }
    
    /**
     * Handles all button clicks in the application
     * Routes button events to appropriate functionality based on button index
     * @param buttonIndex the index of the clicked button (0-8)
     */
    private static void handleButtonClick(int buttonIndex)
    {
        if (buttonIndex < tooltips.length) 
        {   
            try {
                switch(buttonIndex)
                {
                    case 0:
                        try 
                        {
                            fileMngr.outputToXML(DOGS_FILE_PATH, dogs);
                            JLabel message = new JLabel("Data saved successfully!");
                            message.setFont(new Font("Arial", Font.PLAIN, 16));
                            message.setHorizontalAlignment(SwingConstants.CENTER);
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    message,
                                    "Save",
                                    JOptionPane.PLAIN_MESSAGE
                                );
                        } 
                        catch (IOException e) 
                        {
                            //e.printStackTrace();
                        }
                        break;
                    case 2:
                        try 
                        {
                            dogs = fileMngr.inputFromXML(DOGS_FILE_PATH);
                            updateTableWithDataDogs(dogs);
                            JLabel message = new JLabel("Date restored successfully!");
                            message.setFont(new Font("Arial", Font.PLAIN, 16));
                            message.setHorizontalAlignment(SwingConstants.CENTER);
                            JOptionPane.showMessageDialog(
                                    mainFrame,
                                    message,
                                    "Backup",
                                    JOptionPane.PLAIN_MESSAGE
                                );

                        } 
                        catch (IOException e) 
                        {
                            //e.printStackTrace();
                        }
                        break;
                    case 3:
                        AddElementWindow addElem = new AddElementWindow(dogsTable, dogs);
                        addElem.show();
                        break;
                    case 4: 
                        DeleteElementWindow deleteWindow = new DeleteElementWindow(dogsTable, dogs);    
                        deleteWindow.show();
                        break;
                    case 5: 
                        EditElementWindow editWindow = new EditElementWindow(dogsTable, dogs);
                        editWindow.show();	       
                        break;
                    case 6: 
                        AddReportWindow reportWindow = new AddReportWindow();
                        reportWindow.show();
                        break;
                    case 7: 
                        exitApplication();
                        break;
                    case 8:
                        String text = searchTextField.getText(); 
                        searchElement(text);
                        break;
                }
            } catch (InputException e) {
                JOptionPane.showMessageDialog(
                    mainFrame,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    /**
     * Displays a confirmation dialog box with exit image and confirmation text
     * Prompts user to confirm application exit before terminating the program
     * @throws IOException 
     */
    private static void exitApplication() 
    {
        JPanel exitPanel = new JPanel(new BorderLayout());
        
        ImageIcon exitImage = new ImageIcon(imagePaths[10]);
        Image scaledImage = exitImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
 
        JLabel textLabel = new JLabel("Are you sure you want to exit?", JLabel.CENTER);
        textLabel.setFont(defaultFont);
        
        exitPanel.add(imageLabel, BorderLayout.CENTER);
        exitPanel.add(textLabel, BorderLayout.SOUTH);
        
        UIManager.put("OptionPane.buttonFont", defaultFont);

        int result = JOptionPane.showConfirmDialog(
            mainFrame, //parent window
            exitPanel,  //message
            "Confirm Exit", //window title
            JOptionPane.YES_NO_OPTION, //type of buttons
            JOptionPane.PLAIN_MESSAGE //type of message
        );

        if (result == JOptionPane.YES_OPTION)
        {
        	try {
				fileMngr.outputToXML(DOGS_FILE_PATH, dogs);
			} catch (IOException e) {
				e.printStackTrace();
			}
            System.exit(0);
        }
    }
    
    /**
     * Searches the table and shows only the matching rows
     * Filters table data based on search text, showing rows where any cell starts with the search text
     * @param searchText the text to search for in table cells
     */
    private static void searchElement(String searchText)
    {
        // If no search text, show all data again
        if (searchText == null)
        {
            restoreData();
            return;
        }
        // Make search text lowercase and remove extra spaces
        searchText = searchText.toLowerCase().trim();

        // Create a list to store matching rows
        List<String[]> findedData = new List<>();
        
        // Look through every row in the table
        for (int i = 0; i < originalTableData.length; i++)
        {
        	String[] row = originalTableData[i];
        		boolean found = false;
        		// Check every cell in this row
        		for(String cell : row)
        		{
	                // If cell starts with search text, we found a match
	                if (cell.toLowerCase().startsWith(searchText))
	                {
	                    found = true;
	                    break; // Stop checking this row
	                }
        		}
        		// If we found matching text, save this row
        		if(found)
        		{
        			findedData.push_back(row);
        		}
        } 
        updateTableWithData(findedData);
    }
    
    /**
     * Shows all the data again after searching
     * Restores the original full table data, clearing any search filters
     */
    private static void restoreData()
    {
    	// Clear the table
    	tableModel.setRowCount(0);
        // Add back all the original rows
        for (String[] row : originalTableData)
        {
        	tableModel.addRow(row);
        }
    }
    
    /**
     * Updates the table to show only certain rows
     * Replaces current table data with the provided filtered data set
     * @param data the rows to display in the table after filtering
     */
    private static void updateTableWithData(List<String[]> data)
    {
        // Clear the table
        tableModel.setRowCount(0);
        // Add each row from the search results
        for (int i = 0; i < data.getSize(); i++)
        {
            String[] row = data.at(i);
            tableModel.addRow(row);
        }
    }
    
    private static void updateTableWithDataDogs(List<Dog> data)
    {
        // Clear the table
        tableModel.setRowCount(0);
        String[] dogData = new String[3];
        String[] row = new String[4];
        // Add each row from the search results
        for (int i = 0; i < data.getSize(); i++)
        {
        	dogData = data.at(i).toString().split(";");
        	row[0] = Integer.toString(i+1);
            for(int j=0;j<3;j++) 
            {	
            	row[1+j] = dogData[j];
            }
            tableModel.addRow(row);
        }
    }

}