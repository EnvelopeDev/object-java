package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

/**
 * Window for editing existing rows in the table
 * User can select a row and change its data
 * @author Vadim Ustinov
 * @version 1.0
 */
public class EditElementWindow extends InputOutputWindow
{
    private JTable editTable;
    private DefaultTableModel tableModel;
    
    /**
     * Creates window to select which row to edit
     * @param table the table that contains the data to edit
     */
    public EditElementWindow(JTable table) 
    {
    	super("Enter the row number to edit", "Edit Row", 1);
        editTable = table;
        tableModel = (DefaultTableModel) editTable.getModel();
    }
    
    /**
     * Creates window to enter new values for editing
     * @param table the table that contains the data to edit
     * @param num_Fields how many fields to edit (name, breed, awards)
     */
    private EditElementWindow(JTable table, int num_Fields)
    {
        super("Enter new values: 1 - Name, 2 - Breed, 3 - Awards", "Edit Row", num_Fields);
        editTable = table;
        tableModel = (DefaultTableModel) editTable.getModel();
    }
    
    /**
     * Shows the edit window and processes user input
     * First asks for row number, then asks for new values
     * @throws IOException if there's an error during input/output operations
     */
    @Override
    public void show() throws IOException 
    {
    	// Show first window to get row number from user
    	IODialog.setVisible(true);
    	
    	// Get the row number user wants to edit
    	int rowToEdit = Integer.parseInt(getData()[0]);
    	
    	// Create second window to get new values from user
    	EditElementWindow inputNewElementWindow = new EditElementWindow(editTable, 3);
    	inputNewElementWindow.IODialog.setVisible(true);
    	
    	// Update the table with new values
    	inputNewElementWindow.EditRowByNumber(rowToEdit-1);
    	
    	// Show success message
    	inputNewElementWindow.SCSDialog.setVisible(true);
    }
    
    /**
     * Updates the table with edited data for specific row
     * @param rowNumber the row to edit (starting from 0)
     * @throws IOException if there's an error during file operations or data processing
     */
    private void EditRowByNumber(int rowNumber) throws IOException 
    {
        // Get the new values from user input
        String[] editedData = getData();
        
        // If user provided data, update the table
        if (editedData != null) {
            // Update name in column 1
            tableModel.setValueAt(editedData[0].trim(), rowNumber, 1); 
            // Update breed in column 2
            tableModel.setValueAt(editedData[1].trim(), rowNumber, 2); 
            // Update awards in column 3
            tableModel.setValueAt(editedData[2].trim(), rowNumber, 3); 
            
            // Show success message
            successOperationWindow("Row edited "); 
        }
    }  
}