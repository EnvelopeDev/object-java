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
            textFields[0].setText(currentData[0]); // Name
            textFields[1].setText(currentData[1]); // Breed
            textFields[2].setText(currentData[2]); // Awards
        }
    }
    
    /**
     * Shows the edit window and processes user input
     * First asks for row number, then asks for new values with current data pre-filled
     * @throws IOException if there's an error during input/output operations
     * @throws InputException if validation fails
     */
    @Override
    public void show() throws IOException
    {
        try {
            // Show first window to get row number from user
            IODialog.setVisible(true);
            
            String[] inputData = getData();
            
            if (inputData != null && inputData.length > 0) {
                // Validate row number
                InputException.validateRowNumber(inputData[0], tableModel.getRowCount());
                
                int rowToEdit = Integer.parseInt(inputData[0]);
                
                // Get current data from the selected row
                String[] currentData = new String[3];
                currentData[0] = tableModel.getValueAt(rowToEdit-1, 1).toString(); // Name
                currentData[1] = tableModel.getValueAt(rowToEdit-1, 2).toString(); // Breed
                currentData[2] = tableModel.getValueAt(rowToEdit-1, 3).toString(); // Awards
                
                // Create second window to get new values from user with current data pre-filled
                EditElementWindow inputNewElementWindow = new EditElementWindow(editTable, 3, currentData);
                inputNewElementWindow.IODialog.setVisible(true);
                
                // Get new data and validate
                String[] newData = inputNewElementWindow.getData();
                if (newData != null) {
                    InputException.validateRequiredFields(inputNewElementWindow.textFields);
                    
                    // Update the table with new values
                    inputNewElementWindow.EditRowByNumber(rowToEdit-1);
                    
                    // Show success message
                    inputNewElementWindow.SCSDialog.setVisible(true);
                }
            }
        } catch (InputException e) {
            showErrorDialog(e.getMessage());
            this.show();
        } catch (ArrayIndexOutOfBoundsException e) {
            showErrorDialog("Ошибка доступа к данным таблицы");
        }
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