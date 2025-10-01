package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class AddRowWindow
{
	private JTable addTable;
	private IOTextWindow addWindow;
	private int row;
	private ImageIcon icon;
	private DefaultTableModel tableModel;
	private String[] result;
	private JPanel successPanel;
	private JLabel successLabel;
	private JDialog successDialog;
	private JOptionPane successPane;
	
	
	public AddRowWindow(JTable table)
	{
        addTable = table;
	}
	
	public String[] getRowData() throws IOException
    {
        addWindow = new IOTextWindow("Enter the data to add( 1 - Name; 2 - Breed;3 - Awards)",
        		"Add Row", 3);
        addWindow.show();
        result = addWindow.getData();
        
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
		icon = new ImageIcon("src/picts/dogIcon.png"); 
		
        DefaultTableModel model = (DefaultTableModel) addTable.getModel();
        
        model.addRow(new Object[]{
            String.valueOf(model.getRowCount() + 1),
            newData[0].trim(),
            newData[1].trim(),  
            newData[2].trim()
        });
        
        successLabel = new JLabel("Dog added successfully!", JLabel.CENTER);
        successLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        successPanel = new JPanel(new BorderLayout());
        successPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        successPanel.add(successLabel, BorderLayout.CENTER);
        
        successPane = new JOptionPane(
            successPanel,
            JOptionPane.PLAIN_MESSAGE,                    
            JOptionPane.DEFAULT_OPTION                     
        );
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
        successDialog = successPane.createDialog("Success");

        successDialog.setIconImage(icon.getImage());
        successDialog.setVisible(true);
	}
}
