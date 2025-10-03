package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class EditElementWindow extends InputOutputWindow
{
    private JTable editTable;
    private DefaultTableModel tableModel;
    
    public EditElementWindow(JTable table, String text, String title_window, int num_Fields)
    {
        super(text, title_window, num_Fields);
        editTable = table;
        tableModel = (DefaultTableModel) editTable.getModel();
    }
    
    public void EditRowByNumber(int rowNumber) throws IOException 
    {
        String[] editedData = getData();
        
        if (editedData != null) {
            tableModel.setValueAt(editedData[0].trim(), rowNumber, 1); 
            tableModel.setValueAt(editedData[1].trim(), rowNumber, 2); 
            tableModel.setValueAt(editedData[2].trim(), rowNumber, 3); 
            
            successOperationWindow("Row edited "); 
            show();
        }
    }  
}