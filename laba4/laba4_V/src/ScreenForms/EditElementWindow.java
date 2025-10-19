package ScreenForms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Window for editing existing rows in the table
 * User can select a row and change its data
 * @author Vadim Ustinov
 * @version 2.0
 */
public class EditElementWindow extends InputOutputWindow
{
    private JTable editTable;
    private DefaultTableModel tableModel;
    private int rowToEdit;  
    private int rowIndex;
    private String[] currentData; 
    private boolean operationCancelled;
    
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
     * Creates window to enter new values for editing with pre-filled current data
     * @param table the table that contains the data to edit
     * @param num_Fields how many fields to edit (name, breed, awards)
     * @param currentData current values of the row being edited
     */
    private EditElementWindow(JTable table, int num_Fields, String[] currentData)
    {
        super("Enter new values: 1 - Name, 2 - Breed, 3 - Awards", "Edit Row", num_Fields);
        editTable = table;
        tableModel = (DefaultTableModel) editTable.getModel();
        
        // Pre-fill text fields with current data
        if (currentData != null && currentData.length >= 3)
        {
            textFields[0].setText(currentData[0]); 
            textFields[1].setText(currentData[1]); 
            textFields[2].setText(currentData[2]); 
        }
    }
   
    /**
     * Main entry point for the editing process
     * Initiates the row selection window to start editing workflow
     */
    public void show() throws InputException
    {
		showRowSelection();
		if (operationCancelled)
		{
			return;
		}
		showDataEditing();
    }
    
    /**
     * Displays the row selection window and handles row number input validation
     * Shows error dialog and retries on invalid row number input
     * Proceeds to data editing window upon successful row selection
     */
    private void showRowSelection() throws InputException
    {
        boolean validInput = false;
        operationCancelled = false;
        while (!validInput && !operationCancelled)
        {
            IODialog.setVisible(true);
            String[] rowData = getData();
            
            if (rowData != null) 
            {
                InputException.validRowNumber(rowData[0], tableModel.getRowCount());
                rowToEdit = Integer.parseInt(rowData[0]);
                
                currentData = getCurrentRowData(rowToEdit);
                InputException.validDataArray(currentData, 3);
                
                validInput = true;
            }
            else 
            {
                operationCancelled = true;
            }
        }
    }
    
    /**
     * Displays the data editing window with pre-filled current values
     * Handles data input validation and table updates
     * Shows error dialog and retries on invalid data input
     */
    private void showDataEditing() throws InputException
    {
        boolean validInput = false;
        operationCancelled = false;
        while (!validInput && !operationCancelled)
        {
            InputException.validRowNumber(String.valueOf(rowToEdit), tableModel.getRowCount());
            InputException.validDataArray(currentData, 3);
            
            EditElementWindow editDataWindow = new EditElementWindow(editTable, 3, currentData);
            editDataWindow.IODialog.setVisible(true);
            
            String[] editData = editDataWindow.getData();
            
            if (editData != null)
            {
                InputException.validEmptyField(editDataWindow.textFields);
                InputException.validDataArray(editData, 3);
                
                editDataWindow.EditRowByNumber(rowToEdit, editData);
                
                if (editDataWindow.SCSDialog != null)
                {
                    editDataWindow.SCSDialog.setVisible(true);
                }
                
                validInput = true;
            }
            else 
            {
                operationCancelled = true;
            }
        }
    }
    
    /**
     * Gets current data from the specified row
     * @param rowToEdit the row number (starting from 1)
     * @return array with current data [name, breed, awards]
     */
    private String[] getCurrentRowData(int rowToEdit)
    {
        rowIndex = rowToEdit - 1;
        
        String[] currentData = new String[3];
        currentData[0] = tableModel.getValueAt(rowIndex, 1).toString();
        currentData[1] = tableModel.getValueAt(rowIndex, 2).toString(); 
        currentData[2] = tableModel.getValueAt(rowIndex, 3).toString(); 
        
        return currentData;
    }
    
    /**
     * Updates the table with edited data for specific row
     * @param rowToEdit the row to edit (starting from 0)
     * @param editedData the new data to set
     */
    private void EditRowByNumber(int rowToEdit, String[] editedData)
    {
        rowIndex = rowToEdit - 1;
        
        tableModel.setValueAt(editedData[0].trim(), rowIndex, 1); 
        tableModel.setValueAt(editedData[1].trim(), rowIndex, 2); 
        tableModel.setValueAt(editedData[2].trim(), rowIndex, 3); 
        
        successOperationWindow("Row edited"); 
    }
}