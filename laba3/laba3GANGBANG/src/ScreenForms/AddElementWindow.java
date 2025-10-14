package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

/**
 * Window for adding new rows to the table
 * User can enter data for a new dog entry
 * @author Vadim Ustinov
 * @version 1.0
 */
public class AddElementWindow extends InputOutputWindow
{
    private JTable addTable;
    private DefaultTableModel tableModel;
    
    /**
     * Creates window for adding new rows
     * @param table the table to add new row to
     */
    public AddElementWindow(JTable table)
    {
        super("Enter the data to add( 1 - Name; 2 - Breed;3 - Awards)", "Add Row", 3);
        addTable = table;
        tableModel = (DefaultTableModel) addTable.getModel();
    }
    
    /**
     * Shows the add window and processes new data
     * Gets user input and adds new row to table
     * @throws IOException if there's an error during input/output operations
     */
    @Override
    public void show() throws IOException
    {
    	// Show input window to get data from user
    	IODialog.setVisible(true);
    	
    	// Add the new row to table with user data
    	addRowToTable();
    	
    	// Show success message if row was added
    	if (SCSDialog != null)
    	{
    		SCSDialog.setVisible(true);
    	}
    }
    
    /**
     * Adds a new row to the table with user input data
     * Creates new row with number, name, breed, and awards
     * @throws IOException if there's an error during file operations or data processing
     */
    private void addRowToTable() throws IOException
    {   
        // Get the data user entered
        String[] newData = getData();
        
        // If user provided data, add new row
        if (newData != null)
        {
            // Calculate new row number (last row number + 1)
            int newRowNumber = tableModel.getRowCount() + 1;
            
            // Add new row to table with all data
            tableModel.addRow(new Object[]{
                String.valueOf(newRowNumber),        // Row number
                newData[0].trim(),   // Dog name
                newData[1].trim(),    // Dog breed
                newData[2].trim()     // Dog awards
            });
            
            // Show success message
            successOperationWindow("Row added");
        } else {
            // User cancelled or didn't enter data
            System.out.println("Input cancelled or insufficient data");
        }
    }
}