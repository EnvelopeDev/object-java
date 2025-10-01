package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class DeleteElementWindow extends InputOutputWindow
{
    private JTable deleteTable;
    private DefaultTableModel tableModel;
    
    public DeleteElementWindow(JTable table, String text, String title_window, int num_Fields)
    {
        super(text, title_window, num_Fields);
        deleteTable = table;
    }

    public void deleteRowByNumber(int rowNumber)
    {
        boolean confirmed = confirmOperationWindow(	
        	"Confirm Delete",
            "Delete row " + (rowNumber + 1) + "?"
        );
        
        if (confirmed)
        {
            tableModel = (DefaultTableModel) deleteTable.getModel();
            tableModel.removeRow(rowNumber);
            updateRowNumbers();

            successOperationWindow("Row deleted ");
            show();
        }
    }
    
    private void updateRowNumbers()
    {
        for (int i = 0; i < tableModel.getRowCount(); i++)
        {
            tableModel.setValueAt(String.valueOf(i + 1), i, 0); 
        }
    }
}