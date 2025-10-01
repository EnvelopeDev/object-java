package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class AddElementWindow extends InputOutputWindow
{
    private JTable addTable;
    private DefaultTableModel tableModel;
    
    public AddElementWindow(JTable table, String text, String title_window, int num_Fields)
    {
        super(text, title_window, num_Fields);
        addTable = table;
        tableModel = (DefaultTableModel) addTable.getModel();
    }
    
    public void addRowToTable() throws IOException
    {   
        show();
        String[] newData = getData();
        
        if (newData != null && newData.length >= 3) {
            
            int newRowNumber = tableModel.getRowCount() + 1;
            tableModel.addRow(new Object[]{
                String.valueOf(newRowNumber),
                newData[0].trim(),   
                newData[1].trim(),    
                newData[2].trim()     
            });
            
            successOperationWindow("Row added ");
            showSuccessWindow();
        } else {
            System.out.println("Input cancelled or insufficient data");
        }
    }
    
    private void showSuccessWindow()
    {
        JDialog successDialog = IOPane.createDialog("Success");
        successDialog.setIconImage(icon.getImage());
        successDialog.setVisible(true);
    }
}