package ScreenForms;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Base class for all input and output windows in the application
 * Provides common functionality for dialogs that get user input
 * and show messages to the user
 * @author Vadim Ustinov
 * @version 1.0
 */
abstract public class InputOutputWindow
{
	protected String[] results;
    protected JTextField[] textFields;
    protected ImageIcon icon;
    protected int numFields;
    protected String title;
    protected JPanel fieldsPanel;
    protected Font ioDefaultFont;
    protected JPanel IOPanel;
    protected JOptionPane IOPane;
    protected JLabel IOLabel;
    protected JDialog IODialog;
    protected JPanel SCSPanel;
    protected JOptionPane SCSPane;
    protected JLabel SCSLabel;
    protected JDialog SCSDialog;
    protected JPanel CONPanel;
    protected JOptionPane CONPane;
    protected JLabel CONLabel;
    protected JDialog CONDialog;
    
    /**
     * Makes the application window visible 
     * @throws IOException if there's an error displaying the window
     */
    abstract public void show() throws IOException;
    
    /**
     * Creates a new window for input and output
     * @param text the instruction text to show to user
     * @param title_window the title of the window
     * @param num_Fields how many input fields to create
     */
    public InputOutputWindow(String text, String title_window, int num_Fields)
    {	
    	// Set up fonts and basic properties
    	ioDefaultFont = new Font("Arial", Font.PLAIN, 14);
        numFields = num_Fields;
        title = title_window;
        results = new String[numFields];
        textFields = new JTextField[numFields];
        icon = new ImageIcon("src/picts/icon.png"); 
        
        // Create main panel with borders
        IOPanel = new JPanel(new BorderLayout());
        IOPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Create label with instructions
        IOLabel = new JLabel(text, JLabel.CENTER);
        IOLabel.setFont(ioDefaultFont);
        
        // Create panel for input fields
        fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        // Create each text field and add to panel
        for (int i = 0; i < numFields; i++)
        {
            textFields[i] = new JTextField(15);
            textFields[i].setFont(ioDefaultFont);
            fieldsPanel.add(textFields[i]);
        }
        
        // Add label and fields to main panel
        IOPanel.add(IOLabel, BorderLayout.NORTH);
        IOPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        // Create option pane with Yes/No buttons
        IOPane = new JOptionPane(
            IOPanel,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION
        );
        
        // Set font for buttons
        UIManager.put("OptionPane.buttonFont", ioDefaultFont);
        
        // Create the dialog window
        IODialog = IOPane.createDialog(title);
        IODialog.setIconImage(icon.getImage());
    }
    
    /**
     * Gets the data that user entered in the text fields
     * @return array of strings with user input, or null if user cancelled
     */
    public String[] getData()
    {
        // Check if user clicked Yes/OK button
        if (IOPane.getValue().equals(JOptionPane.YES_OPTION)) 
        {
            // Get text from each input field
            for (int i = 0; i < numFields; i++)
            {
                results[i] = textFields[i].getText();
            }
            return results;
        }
        return null;
    }
    
    /**
     * Shows a window with success message
     * @param text the success message to show
     */
    public void successOperationWindow(String text)
    {
    	// Create success message
    	String message = text + " successfully!";
    	
    	// Create panel for message
    	SCSPanel = new JPanel(new BorderLayout());
        SCSPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Create label with message
        SCSLabel = new JLabel(message, JLabel.CENTER);
        SCSLabel.setFont(ioDefaultFont);
        
        // Create option pane for message
    	SCSPane = new JOptionPane(
    			SCSLabel,
    			JOptionPane.DEFAULT_OPTION
    			);
    	
    	// Set font for buttons
    	UIManager.put("OptionPane.buttonFont", ioDefaultFont);
        
        // Create dialog window
    	SCSDialog = SCSPane.createDialog(title);
    	SCSDialog.setIconImage(icon.getImage());				
    }
    
    /**
     * Shows a window to confirm an action with user
     * @param text the question to ask user
     * @return true if user clicked Yes, false if user clicked No
     */
    public boolean confirmOperationWindow(String text)
    {
    	// Create panel for confirmation message
    	CONPanel = new JPanel(new BorderLayout());
    	CONPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Create label with question
    	CONLabel = new JLabel(text, JLabel.CENTER);
    	CONLabel.setFont(ioDefaultFont);
        
        // Create option pane with Yes/No buttons
    	CONPane = new JOptionPane(
    			CONLabel,
    			JOptionPane.PLAIN_MESSAGE,
    			JOptionPane.YES_NO_OPTION
    			);
    	
    	// Set font for buttons
    	UIManager.put("OptionPane.buttonFont", ioDefaultFont);
        
        // Create and show dialog window
    	CONDialog = CONPane.createDialog(title);
    	CONDialog.setIconImage(icon.getImage());
    	CONDialog.setVisible(true);
    	
    	// Check if user confirmed
    	if (CONPane.getValue().equals(JOptionPane.YES_OPTION)) 
        {
            return true;
        }
        return false;
    }
}