package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class EditElementWindow extends InputOutputWindow
{
    private JTable editTable;
    private DefaultTableModel tableModel;
    
    public EditElementWindow(JTable table) 
    {
    	super("Enter the row number to edit", "Edit Row", 1);
        editTable = table;
        tableModel = (DefaultTableModel) editTable.getModel();
    }
    
    private EditElementWindow(JTable table, int num_Fields)
    {
        super("Enter new values: 1 - Name, 2 - Breed, 3 - Awards", "Edit Row", num_Fields);
        editTable = table;
        tableModel = (DefaultTableModel) editTable.getModel();
    }
    
    @Override
    public void show() throws IOException 
    {
    	IODialog.setVisible(true);
    	int rowToEdit = Integer.parseInt(getData()[0]);
    	EditElementWindow inputNewElementWindow = new EditElementWindow(editTable, 3);
    	inputNewElementWindow.IODialog.setVisible(true);
    	inputNewElementWindow.EditRowByNumber(rowToEdit-1);
    	SCSDialog.setVisible(true);
    }
    
    
    
    private void EditRowByNumber(int rowNumber) throws IOException 
    {
        String[] editedData = getData();
        
        if (editedData != null) {
            tableModel.setValueAt(editedData[0].trim(), rowNumber, 1); 
            tableModel.setValueAt(editedData[1].trim(), rowNumber, 2); 
            tableModel.setValueAt(editedData[2].trim(), rowNumber, 3); 
            
            successOperationWindow("Row edited "); 
        }
    }  
}