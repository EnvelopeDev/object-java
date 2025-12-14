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
import java.util.concurrent.CountDownLatch;

import fileManager.FileManager;          
import list.List;                       
import object.dog.Dog;
import report.ReportGenerator;

/**
 * Main window class for the Dog Festival application
 * @author Vadim Ustinov
 * @version 2.0 (with integrated multithreading)
 */
public class MainWindow
{
    /** List of dogs stored in the application */
    private static List<Dog> dogs;
    /** File manager for handling file operations */
    private static FileManager fileMngr;
    /** Icon for the application window */
    private ImageIcon icon; 
    /** Data for the table display */
    private String[][] tableData;
    /** Panel containing control buttons */
    private JPanel buttonsPanel;
    /** Panel containing search input field */
    private JPanel inputPanel;
    /** Array of application control buttons */
    private JButton[] buttons;
    /** Scroll pane for the dogs table */
    private JScrollPane tableScrollPane;
    /** Main application frame */
    private static JFrame mainFrame;
    /** Text field for searching dogs */
    private static JTextField searchTextField;
    /** Original table data before any filtering */
    private static String[][] originalTableData;
    /** Table model for managing table data */
    private static DefaultTableModel tableModel;
    /** Table displaying dog information */
    private static JTable dogsTable;
    /** Default font used throughout the application */
    private static Font defaultFont;
    /** Font used for table headers */
    private static Font headerFont;
    
    /** Path to the XML file containing dog data */
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
     */
    public void show()
    {
        mainFrame.setVisible(true);
    }
    
    /**
     * Constructs the main application window
     * @throws InputException if there is an error initializing data
     */
    public MainWindow() throws InputException
    {
        fileMngr = new FileManager();
        dogs = new List<>();

        // DATA INIT SECTION
        initData();
        
        // STYLE SECTION
        initMainFrame();
        initFonts();
        
        // BUTTON SECTION
        initButtonsPanel();
        
        // TABLE SECTION
        initTable();
        
        // SEARCH SECTION
        initSearchPanel();
        
        // ASSEMBLE MAINFRAME SECTION
        assembleMainFrame();
    }
    
    /**
     * Initializes application data using multithreaded loading
     * @throws InputException if there is an error loading data
     */
    private void initData() throws InputException
    {
        CountDownLatch latch = new CountDownLatch(1);
        final Exception[] loadException = new Exception[1];
        
        fileMngr.loadFromXML(DOGS_FILE_PATH, new FileManager.FileOperationCallback<List<Dog>>() {
            @Override
            public void onSuccess(List<Dog> result) {
                dogs = result;
                latch.countDown();
            }
            
            @Override
            public void onError(Exception e) {
                loadException[0] = e;
                latch.countDown();
            }
        });
        
        try {
            latch.await();
            if (loadException[0] != null) {
                throw new InputException("Error loading data: " + loadException[0].getMessage(), 
                                       InputException.ErrorType.INVALID_FORMAT);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new InputException("Loading interrupted", InputException.ErrorType.INVALID_FORMAT);
        }
    }
    
    /**
     * Initializes font settings for the application
     */
    private void initFonts() 
    {
        defaultFont = new Font("Arial", Font.PLAIN, 14);
        headerFont = new Font("Arial", Font.BOLD, 16);
    }
    
    /**
     * Initializes the main application frame
     */
    private void initMainFrame()
    {
        icon = new ImageIcon(imagePaths[9]);
        mainFrame = new JFrame("Dog Festival");
        mainFrame.setIconImage(icon.getImage());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 500);
        mainFrame.setLocationRelativeTo(null);
    }
    
    /**
     * Initializes the buttons panel with control buttons
     */
    private void initButtonsPanel()
    {
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 8, 10, 10));
        
        buttons = new JButton[9];
        for(int i = 0; i < 9; i++) 
        {		    
            ImageIcon imageForButton = new ImageIcon(imagePaths[i]);
            Image scaledImage = imageForButton.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            ImageIcon buttonIcon = new ImageIcon(scaledImage);
            final int buttonIndex = i;
            
            buttons[i] = new JButton(buttonIcon);
            buttons[i].setToolTipText(tooltips[i]);
            buttons[i].setBorderPainted(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(e -> handleButtonClick(buttonIndex));
            if (i < 8) {
                buttonsPanel.add(buttons[i]);
            }          
        }
    }
    
    /**
     * Initializes the data table with dog information
     */
    private void initTable() 
    {
        tableData = new String[dogs.getSize()][4];
        String[] strListDogs = dogs.convToStr();
        
        for(int i = 0; i < dogs.getSize(); i++) 
        {
            String[] RowData = strListDogs[i].split(";");
            tableData[i][0] = String.valueOf(i + 1);
            tableData[i][1] = RowData[0];
            tableData[i][2] = RowData[1];
            tableData[i][3] = RowData[2];
        }
        
        originalTableData = new String[tableData.length][4];
        for (int i = 0; i < tableData.length; i++) {
            System.arraycopy(tableData[i], 0, originalTableData[i], 0, 4);
        }
        
        tableModel = new DefaultTableModel(tableData, columnNames);
        dogsTable = new JTable(tableModel);
        dogsTable.setDefaultEditor(Object.class, null);
        
        TableColumnModel columnModel = dogsTable.getColumnModel();
        TableColumn numberColumn = columnModel.getColumn(0);
        numberColumn.setMaxWidth(35);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        numberColumn.setCellRenderer(centerRenderer);
        
        dogsTable.setFont(defaultFont);
        dogsTable.getTableHeader().setFont(headerFont);
        dogsTable.getTableHeader().setReorderingAllowed(false);
        dogsTable.setRowHeight(25);
        
        tableScrollPane = new JScrollPane(dogsTable);
    }
    
    /**
     * Initializes the search panel with text field and search button
     */
    private void initSearchPanel()
    {
        inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        searchTextField = new JTextField(12);
        searchTextField.setFont(defaultFont);
        buttons[8].addActionListener(e -> handleButtonClick(8));
        inputPanel.add(searchTextField);
        inputPanel.add(buttons[8]);
    }
    
    /**
     * Assembles all GUI components into the main frame
     */
    private void assembleMainFrame()
    {
        mainFrame.add(buttonsPanel, BorderLayout.NORTH);          
        mainFrame.add(tableScrollPane, BorderLayout.CENTER);   
        mainFrame.add(inputPanel, BorderLayout.SOUTH);            
    }
    
    /**
     * Handles all button clicks in the application
     * @param buttonIndex index of the clicked button
     */
    private static void handleButtonClick(int buttonIndex)
    {
        if (buttonIndex < tooltips.length) 
        {   
            try {
                switch(buttonIndex)
                {
                    case 0: // Save - three sequential threads
                        saveData();
                        break;
                    case 3: // Add
                        AddElementWindow addElem = new AddElementWindow(dogsTable, dogs);
                        addElem.show();
                        break;
                        
                    case 4: // Remove
                        DeleteElementWindow deleteWindow = new DeleteElementWindow(dogsTable, dogs);    
                        deleteWindow.show();
                        break;
                        
                    case 5: // Edit
                        EditElementWindow editWindow = new EditElementWindow(dogsTable, dogs);
                        editWindow.show();	       
                        break;
                        
                    case 6: // Print/Generate Report
                    	AddReportWindow reportWindow = new AddReportWindow();
                        reportWindow.show();
                        break;
                        
                    case 7: // Dropout/Exit
                        exitApplication();
                        break;
                        
                    case 8: // Search
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
     * Saves data using three parallel threads
     */
    private static void saveData() {
        final CountDownLatch saveCompleteLatch = new CountDownLatch(1);
        
        Thread thread1 = new Thread(() -> {
            fileMngr.saveToXML(DOGS_FILE_PATH, dogs, new FileManager.FileOperationCallback<Void>() {
                @Override
                public void onSuccess(Void result) {
                    saveCompleteLatch.countDown();
                }
                @Override
                public void onError(Exception e) {
                    saveCompleteLatch.countDown();
                }
            });
        });
        
        Thread thread2 = new Thread(() -> {
            try {
                saveCompleteLatch.await();
                ReportGenerator.generateHTMLReport(DOGS_FILE_PATH,"dog_report.html");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        Thread thread3 = new Thread(() -> {
            try {
                saveCompleteLatch.await();
                ReportGenerator.generatePDFReport(DOGS_FILE_PATH,"dog_report.pdf");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // === START ALL THREADS ===
        thread1.start();
        thread2.start();
        thread3.start();
        threadsControl(thread1,thread2,thread3).start();
    }
    
    /**
     * Controls and monitors the execution of save threads
     * @param thread1 first thread for saving data
     * @param thread2 second thread for HTML report generation
     * @param thread3 third thread for PDF report generation
     * @return thread that controls the other threads
     */
    private static Thread threadsControl(Thread thread1, Thread thread2, Thread thread3)
    {
    	return new Thread(() -> {
            try {
                thread1.join();
                thread2.join();
                thread3.join();
                
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(mainFrame,
                        "Thread 1: Saving data to main file ✓\n" +
                        "Thread 2: Generating HTML report from main file ✓\n" +
                        "Thread 3: Generating PDF report from main file ✓\n\n",
                        "Threads control",
                        JOptionPane.DEFAULT_OPTION);
                });
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    /**
     * Displays exit confirmation dialog and exits application if confirmed
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
            mainFrame,
            exitPanel,
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE
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
     * @param searchText text to search for in the table
     */
    private static void searchElement(String searchText)
    {
        if (searchText == null || searchText.trim().isEmpty())
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
                if (cell.toLowerCase().contains(searchText))
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
     * Shows all the data again after searching (restores original data)
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
     * Updates the table to show only certain rows
     * @param data list of rows to display
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
}