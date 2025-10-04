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
	private ImageIcon icon; 
    private String[][] tableData;
    private JPanel buttonsPanel;
    private JPanel inputPanel;
    private JButton[] buttons;
    private static JFrame mainFrame;
    private static JTextField searchTextField;
    private static String[][] originalTableData;
    private static DefaultTableModel tableModel;
    private static JTable dogsTable;
    private static Font defaultFont;
    private static Font boldDefaultFont;
    private static Font subHeaderFont;
    private static Font boldSubHeaderFont;
    private static Font headerFont;
    private static Font boldHeaderFont;
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
    	//INITIALIZATION SECTION
        FileManager file = new FileManager();//init FileManager object
        List<Dog> dogs = new List<>();//init List fot dogs data
        dogs = file.inputFromCSV("src/data/dogs3.csv"); //writes data from dogs3.csv
        
        icon = new ImageIcon(imagePaths[9]);//init window icon
        mainFrame = new JFrame("Dog Festival");//init mainfraime and init window title
        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));//init inputPanel with center align, 10 width, 5 heigth 
        defaultFont = new Font("Arial", Font.PLAIN, 14);
        boldDefaultFont = new Font("Arial", Font.BOLD, 14);
        subHeaderFont = new Font("Arial", Font.PLAIN, 16);
        boldSubHeaderFont = new Font("Arial", Font.BOLD, 16);
        headerFont = new Font("Arial", Font.PLAIN, 20);
        boldHeaderFont = new Font("Arial", Font.BOLD, 20);
        
        //BUTTON SECTION
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
        
        //TABLE SECTION
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
        dogsTable.getTableHeader().setFont(boldDefaultFont);
        dogsTable.getTableHeader().setReorderingAllowed(false); //prohibition//set fixed row height to move columns
        dogsTable.setRowHeight(25);
        
        JScrollPane tableScrollPane = new JScrollPane(dogsTable);

        //SEARCH SECTION
        searchTextField = new JTextField(12);
        searchTextField.setFont(defaultFont);
        buttons[8].addActionListener(e -> handleButtonClick(8));

        inputPanel.add(searchTextField);
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
    
    /**
     * Handles all button clicks in the app
     */
    private static void handleButtonClick(int buttonIndex)
    {
        if (buttonIndex < tooltips.length) 
        {   
            switch(buttonIndex) {
	            case 3: // Add button
	                try {
	                    AddElementWindow addElem = new AddElementWindow(dogsTable);
	                    addElem.show();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                break;
	            case 4: 
	                try {
	                    DeleteElementWindow deleteElem = new DeleteElementWindow(dogsTable);
	                    
	                    deleteElem.show();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                break;
	
	            case 5: 
	                try {
	                    EditElementWindow editElem = new EditElementWindow(dogsTable);
	                    editElem.show();	                    
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
        textLabel.setFont(subHeaderFont);
        
        exitPanel.add(imageLabel, BorderLayout.CENTER);
        exitPanel.add(textLabel, BorderLayout.SOUTH);
        
        UIManager.put("OptionPane.buttonFont", subHeaderFont);

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
     */
    private static void performSearch(String searchText)
    {
        if (searchText == null)
        {
            restoreData();
            return;
        }
        searchText = searchText.toLowerCase().trim();

        List<String[]> findedData = new List<>();
        
        for (int i = 0; i < originalTableData.length; i++)
        {
        	String[] row = originalTableData[i];
        		boolean found = false;
        		for(String cell : row)
        		{
	                if (cell.toLowerCase().startsWith(searchText))
	                {
	                    found = true;
	                    break;
	                }
        		}
        		if(found)
        		{
        			findedData.push_back(row);
        		}
        } 
        updateTableWithData(findedData);
    }
    
    /**
     *Restores the full table from the backup after searching
     */
    private static void restoreData()
    {
    	tableModel.setRowCount(0);
        for (String[] row : originalTableData)
        {
        	tableModel.addRow(row);
        }
    }
    
    /**
     *Replaces the data in the table with the passed list of rows 
     */
    private static void updateTableWithData(List<String[]> data)
    {
        tableModel.setRowCount(0);
        for (int i = 0; i < data.getSize(); i++)
        {
            String[] row = data.at(i);
            tableModel.addRow(row);
        }
    }
    
    /**
     *Makes the main application window visible 
     */
    public static void show() throws IOException
    {
        mainFrame.setVisible(true);
    }
}