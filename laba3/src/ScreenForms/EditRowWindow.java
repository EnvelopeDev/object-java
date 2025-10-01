package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class EditRowWindow
{
	private JTable editTable;
	private IOTextWindow editWindow;
	private ImageIcon icon;
	private JLabel confirmLabel;
	private JPanel confirmPanel;
	private JOptionPane confirmPane;
	private JDialog confirmDialog;
	private DefaultTableModel tableModel;
	private JLabel successLabel;
	private JPanel successPanel;
	private JOptionPane successPane;
	private JDialog successDialog;
	
    public EditRowWindow(JTable table)
    {
	    editTable = table;
    }
    
    public int getRow() throws IOException
    {
    	editWindow = new IOTextWindow("Enter the row number to edit", "Edit Row", 1);
    	editWindow.show();
        String[] result = editWindow.getData();
        
        if (result != null)
        {
            int row = Integer.parseInt(result[0].trim());
            return row;
        }
        else
        {
            System.out.println("Input cancelled");
        }
        return 0;
    }
    
    public String[] getDataForEdit(int rowNumber) throws IOException {

        tableModel = (DefaultTableModel) editTable.getModel();
        String currentName = tableModel.getValueAt(rowNumber, 1).toString();
        String currentBreed = tableModel.getValueAt(rowNumber, 2).toString();
        String currentAwards = tableModel.getValueAt(rowNumber, 3).toString();

        editWindow = new IOTextWindow(
        		        "<html>Current: " + currentName + "; " + currentBreed + "; " + currentAwards + 
        		        "<br>Enter new values:<br>1 - Name, 2 - Breed, 3 - Awards</html>",
        		        "Edit Row", 
        		        3
            );
        
        editWindow.show();
        return editWindow.getData();
    }
    
    public void EditRowByNumber(int rowNumber) throws IOException 
    {
    	icon = new ImageIcon("src/picts/dogIcon.png"); 
        
        tableModel = (DefaultTableModel) editTable.getModel();
        
        String[] editedData = getDataForEdit(rowNumber);
        
        tableModel.setValueAt(editedData[0].trim(), rowNumber, 1); // Name
        tableModel.setValueAt(editedData[1].trim(), rowNumber, 2); // Breed
        tableModel.setValueAt(editedData[2].trim(), rowNumber, 3); // Awards (Yes/No как есть)


        successLabel = new JLabel("Row edit successfully", JLabel.CENTER);
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