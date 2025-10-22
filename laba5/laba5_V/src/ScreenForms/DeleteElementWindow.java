package ScreenForms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import list.List;
import object.dog.Dog;


/**
 * Window for deleting rows from the table
 * User can select a row to remove from the data
 * @author Vadim Ustinov
 * @version 1.0
 */
public class DeleteElementWindow extends InputOutputWindow
{
    private JTable deleteTable;
    private List<Dog> dogs;
    private DefaultTableModel tableModel;
    private int rowToDelete;
    
    /**
     * Creates window for deleting rows
     * @param table the table to delete rows from
     */
    public DeleteElementWindow(JTable table, List<Dog> _dogs)
    {
        super("Enter the row number to delete", "Delete Row", 1, 0);
        deleteTable = table;
        dogs = _dogs;
        tableModel = (DefaultTableModel) deleteTable.getModel();
    }

    /**
     * Shows the delete window and handles row deletion
     * Asks for row number and removes it after confirmation
     */
    public void show() throws InputException
    {
    	showDeleteRow();  
    }
    
    /**
     * Displays the row selection window and handles row number input validation
     * Shows error dialog and retries on invalid row number input
     * Proceeds to data deleting window upon successful row selection
     */
    private void showDeleteRow() throws InputException
    {
        boolean validInput = false;
        boolean operationCancelled = false;
        while (!validInput && !operationCancelled)
        {
            IODialog.setVisible(true);
            String[] rowData = getData();
            
            if (rowData != null) 
            {
                InputException.validRowNumber(rowData[0], tableModel.getRowCount());
                rowToDelete = Integer.parseInt(rowData[0]);
                
                deleteRowByNumber(rowToDelete);
                validInput = true;
            }
            else 
            {
                operationCancelled = true;
            }
        }
    }
    
    /**
     * Deletes a specific row from the table
     * Asks user to confirm before deleting
     * @param rowToDelete the row to delete (starting from 1)
     */
    private void deleteRowByNumber(int rowToDelete)
    {
        boolean confirmed = confirmOperationWindow("Delete row " + rowToDelete + "?");
        
        if (confirmed)
        {
        	dogs.remove(rowToDelete-1);
            tableModel.removeRow(rowToDelete-1);
            
            updateRowNumbers();

            successOperationWindow("Row deleted");
        }
    }
    
    /**
     * Updates the row numbers after deletion
     * Makes sure all rows have correct sequential numbers
     */
    private void updateRowNumbers()
    {
        for (int i = 0; i < tableModel.getRowCount(); i++)
        {
            tableModel.setValueAt(String.valueOf(i+1), i, 0); 
        }
    }
}