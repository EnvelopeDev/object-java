package ScreenForms;

import fileManager.FileManager;
import java.io.IOException;
import list.List;
import object.dog.Dog;
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

/**
 * Main window class for the Dog Festival application
 * Handles GUI creation and user interface components
 * @author Vadim Ustinov
 * @version 1.0
 */
public class MainWindow
{
	private List<Dog> dogs;
	private FileManager fileMngr;
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
    private static final String[] columnNames = {
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
        initData();
        initMainFrame();
        initFonts();
        
        //BUTTON SECTION
        initButtonsPanel();
        //TABLE SECTION
        initTable();
        //SEARCH SECTION
        initSearchPanel();
        //MAINFRAME ADDING ELEMENTS SECTION
        assembleMainFrame();
    }
    
    private void initData() throws IOException
    {
    	fileMngr = new FileManager();//init FileManager object
        dogs = new List<>();//init List fot dogs data
        dogs = fileMngr.inputFromCSV("src/data/dogs3.csv"); //writes data from dogs3.csv
    }
    
    private void initFonts() 
    {
    	defaultFont = new Font("Arial", Font.PLAIN, 14);
        headerFont = new Font("Arial", Font.BOLD, 16);

    }
    
    private void initMainFrame()
    {
    	icon = new ImageIcon(imagePaths[9]);//init window icon
        mainFrame = new JFrame("Dog Festival");//init mainfraime and init window title
        mainFrame.setIconImage(icon.getImage());
    	mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 500);
        mainFrame.setLocationRelativeTo(null);
    }
    
    private void initButtonsPanel()
    {
    	buttonsPanel = new JPanel();//init buttons panel
        buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));//set 1 row, 8 cols, 10 width, 10 heigth
        
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
        dogsTable.getTableHeader().setReorderingAllowed(false); //prohibition//set fixed row height to move columns
        dogsTable.setRowHeight(25);
        
        tableScrollPane = new JScrollPane(dogsTable);

    }
    
    private void initSearchPanel() 
    {
    	inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));//init text panel for search
    	searchTextField = new JTextField(12);
        searchTextField.setFont(defaultFont);
        buttons[8].addActionListener(e -> handleButtonClick(8));
        inputPanel.add(searchTextField);
        inputPanel.add(buttons[8]); 
    }
    
    private void assembleMainFrame()
    {
        mainFrame.add(buttonsPanel, BorderLayout.NORTH);          
        mainFrame.add(tableScrollPane, BorderLayout.CENTER);   
        mainFrame.add(inputPanel, BorderLayout.SOUTH);            
    }
    
    /**
     * Handles all button clicks in the app
     */
    private static void handleButtonClick(int buttonIndex)
    {
        if (buttonIndex < tooltips.length) 
        {   
            switch(buttonIndex)
            {
                case 3: 
                    try {
                        AddElementWindow addElem = new AddElementWindow(dogsTable);
                        addElem.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(
                            mainFrame,
                            "Ошибка при добавлении: " + e.getMessage(),
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    break;
                case 4: 
                    DeleteElementWindow deleteWindow = new DeleteElementWindow(dogsTable);    
                    deleteWindow.show();
                    break;

                case 5: 
                    EditElementWindow editWindow = new EditElementWindow(dogsTable);
                    editWindow.show();	       
                    break;
                case 6:
                    try {
                        PrinterWindow.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(
                            mainFrame,
                            "Ошибка при печати: " + e.getMessage(),
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                    break;
                case 7: 
                    exitApplication();
                    break;
                case 8:
                    String text = searchTextField.getText(); 
                    performSearch(text);
                    break;
            }
        }
    }
    
    /**
     *Displays a confirmation dialog box with a picture and text
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
            System.exit(0);
        }
    }
    
    /**
     *Searches the table and shows only the matching rows
     *@param searchText the words you want to search for
     */
    private static void performSearch(String searchText)
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
        // Show only the matching rows in the table
        updateTableWithData(findedData);
    }
    
    /**
     * Shows all the data again after searching
     * Goes back to the original full table
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
     * @param data the rows you want to show in the table
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
    /**
     * Makes the main application window visible 
     * @throws IOException if there's an error displaying the window
     */
    public static void show() throws IOException
    {
        mainFrame.setVisible(true);
    }
}