package ScreenForms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Window for adding new rows to the table
 * User can enter data for a new dog entry
 * @author Vadim Ustinov
 * @version 1.1
 */
public class AddElementWindow extends InputOutputWindow
{
    private JTable addTable;
    private DefaultTableModel tableModel;
    private boolean operationCancelled;
    
    private static final String[] FIELD_NAMES = {"Name", "Breed", "Awards"};
    
    /**
     * Creates window for selecting position to add new row
     * @param table the table to add new row to
     */
    public AddElementWindow(JTable table)
    {
        super("Enter the data to add (1 - Name; 2 - Breed; 3 - Awards)", "Add Row", 2, 1);
        addTable = table;
        tableModel = (DefaultTableModel) addTable.getModel();
    }
    
    /**
     * Shows the add window and processes new data
     * Gets user input and adds new row to table
     */
    public void show() throws InputException
    {
		showDataAdding();
    }
        
    /**
     * Enter data for new row
     * Shows error dialog and retries on invalid data input
     */
    private void showDataAdding() throws InputException
    {
    	boolean validInput = false;
        operationCancelled = false;
        while (!validInput && !operationCancelled)
        {          
            IODialog.setVisible(true);
            String[] addData = getData();
            
            if (addData != null)
            {
                InputException.validEmptyField(textFields, FIELD_NAMES);
                InputException.validDataArray(addData, 3);
                InputException.validLettersOnly(addData[0], FIELD_NAMES[0]);
                InputException.validLettersOnly(addData[1], FIELD_NAMES[1]);
                InputException.validZeroOrOne(addData[2], FIELD_NAMES[2]);
                
                addRowToTable(addData);
                
                if (SCSDialog != null)
                {
                    SCSDialog.setVisible(true);
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
     * Adds a new row to the table at specified position
     * @param rowToAdd position to insert new row (1-based)
     * @param newData the data to add [name, breed, awards]
     */
    private void addRowToTable(String[] newData)
    {   
    	int newRowNumber = tableModel.getRowCount() + 1;        
        tableModel.addRow(new Object[]{
                String.valueOf(newRowNumber),        // Row number
                newData[0].trim(),   // Dog name
                newData[1].trim(),    // Dog breed
                newData[2].trim()     // Dog awards
            });
                
        successOperationWindow("Row added");
    }
    
}