package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class AddElementWindow extends InputOutputWindow
{
    private JTable addTable;
    private DefaultTableModel tableModel;
    
    public AddElementWindow(JTable table)
    {
        super("Enter the data to add( 1 - Name; 2 - Breed;3 - Awards)", "Add Row", 3);
        addTable = table;
        tableModel = (DefaultTableModel) addTable.getModel();
    }
    
    @Override
    public void show() throws IOException
    {
    	IODialog.setVisible(true);
    	addRowToTable();
    }
    
    private void addRowToTable() throws IOException
    {   
        String[] newData = getData();
        if (newData != null)
        {
            int newRowNumber = tableModel.getRowCount() + 1;
            tableModel.addRow(new Object[]{
                String.valueOf(newRowNumber),
                newData[0].trim(),   
                newData[1].trim(),    
                newData[2].trim()     
            });
            
            successOperationWindow("Row added");
        } else {
            System.out.println("Input cancelled or insufficient data");
        }
    }
}