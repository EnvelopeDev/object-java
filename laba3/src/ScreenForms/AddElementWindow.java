package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class AddElementWindow extends InputOutputWindow
{
	private JTable addTable;
	private DefaultTableModel tableModel;
	private String[] result;

	
	
	public AddElementWindow(JTable table, String text, String title_window, int num_Fields)
	{
		super(text, title_window, num_Fields);
        addTable = table;
	}
	
	public String[] getRowData() throws IOException
    {
        show();
        result = getData();
        
        if (result != null)
        {
            return result;
        }
        else
        {
            System.out.println("Input cancelled");
        }
        return null;
    }
	
	public void addRowToTable(String[] newData) throws IOException
	{	
		tableModel = (DefaultTableModel) addTable.getModel();
        
		tableModel.addRow(new Object[]{
            String.valueOf(tableModel.getRowCount() + 1),
            newData[0].trim(),
            newData[1].trim(),  
            newData[2].trim()
        });
        
        successOperationWindow("Row added ");
        show();
	}
}
