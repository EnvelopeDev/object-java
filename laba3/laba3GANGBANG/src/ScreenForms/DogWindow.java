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

import java.awt.Graphics;
import java.awt.Color;    

/**
 * Main window class for the Dog Festival application
 * Handles GUI creation and user interface components
 * @author Gushchin Kirill
 * @version 1.0
 */
public class DogWindow
{
	private List<Dog> dogs;
	private FileManager fileMngr;
	private ImageIcon icon; 
    private String[][] tableData;
    private JPanel buttonsPanel;
    private JButton[] buttons;
    private JScrollPane tableScrollPane;
    private static JFrame mainFrame;
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
            "src/picts/1.png",
            "src/picts/2.png",
            "src/picts/3.png",
            "src/picts/4.png", 
            "src/picts/5.png",
            "src/picts/6.png",
            "src/picts/7.png",
            "src/picts/8.png",
            "src/picts/9.png",
            "src/picts/icon.png",
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
    public DogWindow() throws IOException
    {
        initData();
        initMainFrame();
        initFonts();
        
        initButtonsPanel();
        initTable();
        
        setBackgroundImage();

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
        
        SearchFunction.setOriginalData(originalTableData);
        
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
        
        dogsTable.setOpaque(false);
        dogsTable.setBackground(new Color(255, 255, 255, 200)); 
        dogsTable.setForeground(Color.BLACK);
        dogsTable.setFont(defaultFont);
        
        dogsTable.setSelectionBackground(new Color(100, 150, 255, 150));
        dogsTable.setSelectionForeground(Color.BLACK);
        
        tableScrollPane.setOpaque(false);
        tableScrollPane.getViewport().setOpaque(false);
        
        dogsTable.getTableHeader().setOpaque(false);
        dogsTable.getTableHeader().setBackground(new Color(240, 240, 240, 200));

    }
    
    private void initButtonsPanel()
    {
        buttonsPanel = new JPanel();//init buttons panel
        buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));//set 1 row, 8 cols, 10 width, 10 heigth
        
        buttons = new JButton[9];//init buttons array
        for(int i = 0; i < 9; i++) 
        {		    
            ImageIcon imageForButton = new ImageIcon(imagePaths[i]);//init image for icon 
            Image scaledImage = imageForButton.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            ImageIcon buttonIcon = new ImageIcon(scaledImage);//init icon for button
            final int buttonIndex = i; // need final for lambda function
            
            buttons[i] = new JButton(buttonIcon);//set icon 
            buttons[i].setToolTipText(tooltips[i]);//set button tooltips
            buttons[i].setBorderPainted(false);//remove the frame
            buttons[i].setContentAreaFilled(false);//set transparent background
            buttons[i].setFocusPainted(false);//Remove backlight when focusing
            buttons[i].addActionListener(e -> handleButtonClick(buttonIndex));//add click handler calls lambda that calls click logic
            buttonsPanel.add(buttons[i]);//add button into buttonsPane      
        }
    }
    
    private void setBackgroundImage() {

        ImageIcon backgroundIcon = new ImageIcon("src/picts/background.png");
        final Image backgroundImage = backgroundIcon.getImage();
        
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        buttonsPanel.setOpaque(false);
        tableScrollPane.setOpaque(false);
        tableScrollPane.getViewport().setOpaque(false);
        dogsTable.setOpaque(false);
        
        backgroundPanel.add(buttonsPanel, BorderLayout.SOUTH);
        backgroundPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        mainFrame.setContentPane(backgroundPanel);
    }
   
    private static void handleButtonClick(int buttonIndex)
    {
        if (buttonIndex < tooltips.length) 
        {   
            switch(buttonIndex)
            {
	            case 3: 
	                try {
	                    AddFunction addElem = new AddFunction(dogsTable);
	                    addElem.show();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                break;
	            case 4: 
	                try {
	                    DeleteFunction deleteElem = new DeleteFunction(dogsTable);    
	                    deleteElem.show();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                break;
	
	            case 5: 
	                try {
	                    EditFunction editElem = new EditFunction(dogsTable);
	                    editElem.show();	                    
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                break;
	                case 6:
	                    try {
	                        PrintFunction.show();
	                    } catch (IOException e) {
	                    }
	                    break;
	                case 7: 
	                    exitApplication();
	                    break;
	                case 8: 
	                    try {
	                        SearchFunction searchWindow = new SearchFunction(
	                            "Enter search term:", 
	                            "Search Dogs", 
	                            1, 
	                            dogsTable
	                        );
	                        searchWindow.show();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                    break;

            }
        }
    }
    
    /**
     * Displays a confirmation dialog box with a picture and text
     * for exiting the application
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
     * Displays the main application window
     * @throws IOException if there's an error displaying the window
     */
    public static void show() throws IOException
    {
        mainFrame.setVisible(true);
    }
}