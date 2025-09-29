package ScreenForms;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DeleteWindow
{
	private static JTable tableD;
	private static int col;
	
	public DeleteWindow(JTable table)
	{
		tableD = table;
	}
    public static int getCol() throws IOException
    {
    	IOTextWindow win1 = new IOTextWindow();
        String[] result = win1.use("Enter the row number to delete", "Delete Row",  1);
        
        if (result != null)
        {
            col = Integer.parseInt(result[0]);
            return col;
        }
        else
        {
            System.out.println("Ввод отменен");
        }
        return 0;
    }
    public static void deleteSelectedRow()
    {
    	
    }
}