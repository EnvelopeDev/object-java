package ScreenForms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Window for deleting rows from the table
 * User can select a row to remove from the data
 * @author Vadim Ustinov
 * @version 1.0
 */
public class DeleteElementWindow extends InputOutputWindow
{
    private JTable deleteTable;
    private DefaultTableModel tableModel;
    private int rowToDelete;
    
    /**
     * Creates window for deleting rows
     * @param table the table to delete rows from
     */
    public DeleteElementWindow(JTable table)
    {
        super("Enter the row number to delete", "Delete Row", 1);
        deleteTable = table;
        tableModel = (DefaultTableModel) deleteTable.getModel();
    }

    /**
     * Shows the delete window and handles row deletion
     * Asks for row number and removes it after confirmation
     */
    public void show()
    {
        try {
        	deleteSelectedRowWindow();
        } catch (InputException e) {
            showErrorDialog(e.getMessage());
        }
    }
    private void deleteSelectedRowWindow() throws InputException
    {
        try {
            IODialog.setVisible(true);
            String[] rowData = getData();
            
            if (rowData != null) 
            {
                InputException.validRowNumber(rowData[0], tableModel.getRowCount());
                rowToDelete = Integer.parseInt(rowData[0]);
                
                deleteRowByNumber(rowToDelete);
            }
        } catch (InputException e) {
            showErrorDialog(e.getMessage());
            deleteSelectedRowWindow();
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