package ScreenForms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

/**
 * Window for deleting rows from the table
 * User can select a row to remove from the data
 * @author Gushchin Kirill
 * @version 1.0
 */
public class DeleteFunction extends DialogWindow
{
    private JTable deleteTable;
    private DefaultTableModel tableModel;
    
    /**
     * Creates window for deleting rows
     * @param table the table to delete rows from
     */
    public DeleteFunction(JTable table)
    {
        super("Enter the row number to delete", "Delete Row", 1);
        deleteTable = table;
    }

    /**
     * Shows the delete window and handles row deletion
     * Asks for row number and removes it after confirmation
     * @throws IOException if there's an error during input/output operations
     */
    @Override
    public void show() throws IOException
    {
    	// Show window to get row number from user
    	IODialog.setVisible(true);
    	
    	// Get the row number user wants to delete
    	int rowToDelete = Integer.parseInt(getData()[0]);
    	
    	// Delete the selected row
    	this.deleteRowByNumber(rowToDelete);
    	
    	// Show success message
    	SCSDialog.setVisible(true);
    }
    
    /**
     * Deletes a specific row from the table
     * Asks user to confirm before deleting
     * @param rowNumber the row to delete (starting from 1)
     */
    private void deleteRowByNumber(int rowNumber)
    {
        // Ask user to confirm the deletion
        boolean confirmed = confirmOperationWindow("Delete row " + rowNumber + "?");
        
        // If user confirmed, delete the row
        if (confirmed)
        {
            tableModel = (DefaultTableModel) deleteTable.getModel();
            // Remove the row (convert from 1-based to 0-based index)
            tableModel.removeRow(rowNumber-1);
            // Update all row numbers after deletion
            updateRowNumbers();

            // Show success message
            successOperationWindow("Row deleted");
        }
    }
    
    /**
     * Updates the row numbers after deletion
     * Makes sure all rows have correct sequential numbers
     */
    private void updateRowNumbers()
    {
        // Go through all rows and update their numbers
        for (int i=0; i<tableModel.getRowCount(); i++)
        {
            // Set new row number (starting from 1)
            tableModel.setValueAt(String.valueOf(i+1), i, 0); 
        }
    }
}