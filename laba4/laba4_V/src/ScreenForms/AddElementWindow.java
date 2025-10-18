package ScreenForms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    private int rowToAdd;
    private int rowIndex;
    
    /**
     * Creates window for selecting position to add new row
     * @param table the table to add new row to
     */
    public AddElementWindow(JTable table)
    {
        super("Enter the position to add new row", "Add Row", 1);
        addTable = table;
        tableModel = (DefaultTableModel) addTable.getModel();
    }
    
    /**
     * Creates window for entering new data
     * @param table the table to add new row to
     * @param num_Fields number of input fields
     */
    private AddElementWindow(JTable table, int num_Fields)
    {
        super("Enter the data to add (1 - Name; 2 - Breed; 3 - Awards)", "Add Row", num_Fields);
        addTable = table;
        tableModel = (DefaultTableModel) addTable.getModel();
    }
    
    /**
     * Shows the add window and processes new data
     * Gets user input and adds new row to table
     */
    public void show()
    {
        try {
        	rowSelectionWindow();
            dataAddingWindow();
        } catch (InputException e) {
            showErrorDialog(e.getMessage());
        }
    }
    
    /**
     * First window - select position to add new row
     * Shows error dialog and retries on invalid position input
     */
    private void rowSelectionWindow() throws InputException
    {
        try {
            IODialog.setVisible(true);
            String[] rowData = getData();
            
            if (rowData != null) 
            {
                // Validate position (1 to tableSize+1)
                InputException.validRowNumber(rowData[0], tableModel.getRowCount() + 1);
                rowToAdd = Integer.parseInt(rowData[0]);
            }
        } catch (InputException e) {
            showErrorDialog(e.getMessage());
            rowSelectionWindow();
        }
    }
    
    /**
     * Second window - enter data for new row
     * Shows error dialog and retries on invalid data input
     */
    private void dataAddingWindow() throws InputException
    {
        InputException.validRowNumber(String.valueOf(rowToAdd), tableModel.getRowCount() + 1);
        
        AddElementWindow dataInputWindow = new AddElementWindow(addTable, 3);
        
        try {
            dataInputWindow.IODialog.setVisible(true);
            String[] addData = dataInputWindow.getData();
            
            if (addData != null)
            {
                InputException.validEmptyField(dataInputWindow.textFields);
                InputException.validDataArray(addData, 3);
                
                dataInputWindow.addRowToTable(rowToAdd, addData);
                
                if (dataInputWindow.SCSDialog != null)
                {
                    dataInputWindow.SCSDialog.setVisible(true);
                }
            }
        } catch (InputException e)
        {
            dataInputWindow.showErrorDialog(e.getMessage());
            dataAddingWindow();
        }
    }
    
    /**
     * Adds a new row to the table at specified position
     * @param rowToAdd position to insert new row (1-based)
     * @param newData the data to add [name, breed, awards]
     */
    private void addRowToTable(int rowToAdd, String[] newData)
    {   
        rowIndex = rowToAdd - 1;
        
        tableModel.insertRow(rowIndex, new Object[]{
            String.valueOf(rowToAdd),        
            newData[0].trim(),               
            newData[1].trim(),               
            newData[2].trim()                
        });
        
        updateRowNumbers(rowToAdd);
        
        successOperationWindow("Row added");
    }
    
    /**
     * Updates row numbers starting from specified index
     * @param startIndex the index to start updating from (0-based)
     */
    private void updateRowNumbers(int startIndex)
    {
        for (int i = startIndex; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(String.valueOf(i + 1), i, 0);
        }
    }
}