package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class DeleteElementWindow extends InputOutputWindow
{
    private JTable deleteTable;
    private DefaultTableModel tableModel;
    
    public DeleteElementWindow(JTable table)
    {
        super("Enter the row number to delete", "Delete Row", 1);
        deleteTable = table;
    }

    @Override
    public void show() throws IOException
    {
    	IODialog.setVisible(true);
    	int rowToDelete = Integer.parseInt(getData()[0]);
    	this.deleteRowByNumber(rowToDelete);
    }
    
    private void deleteRowByNumber(int rowNumber)
    {
        boolean confirmed = confirmOperationWindow("Delete row " + rowNumber + "?");
        
        if (confirmed)
        {
            tableModel = (DefaultTableModel) deleteTable.getModel();
            tableModel.removeRow(rowNumber-1);
            updateRowNumbers();

            successOperationWindow("Row deleted");
        }
    }
    
    private void updateRowNumbers()
    {
        for (int i=0; i<tableModel.getRowCount(); i++)
        {
            tableModel.setValueAt(String.valueOf(i+1), i, 0); 
        }
    }
}