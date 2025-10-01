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
    }
    
    public String[] getDataForEdit(int rowNumber) throws IOException
    {
        tableModel = (DefaultTableModel) editTable.getModel();
        String currentName = tableModel.getValueAt(rowNumber, 1).toString();
        String currentBreed = tableModel.getValueAt(rowNumber, 2).toString();
        String currentAwards = tableModel.getValueAt(rowNumber, 3).toString();
        
        show();
        return getData();
    }
    
    public void EditRowByNumber(int rowNumber) throws IOException 
    {
        tableModel = (DefaultTableModel) editTable.getModel();
        String[] editedData = getDataForEdit(rowNumber);
        
        if (editedData != null) {
            tableModel.setValueAt(editedData[0].trim(), rowNumber, 1); 
            tableModel.setValueAt(editedData[1].trim(), rowNumber, 2); 
            tableModel.setValueAt(editedData[2].trim(), rowNumber, 3); 
            
            successOperationWindow("Row edited "); 
            show();
        }
    }  
}