package ScreenForms;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

public class DeleteRowWindow
{
    private JTable deleteTable;
    private IOTextWindow deleteWindow;
    private ImageIcon icon;
    private DefaultTableModel tableModel;
    private int row;
    private JLabel confirmLabel;
    private JPanel confirmPanel;
    private JOptionPane confirmPane;
    private Dialog confirmDialog;
    private JLabel successLabel;
    private JPanel successPanel;
    private JOptionPane successPane;
    private Dialog successDialog;
    
    public DeleteRowWindow(JTable table)
    {
        deleteTable = table;
    }
    
    public int getRow() throws IOException
    {
        deleteWindow = new IOTextWindow("Enter the row number to delete", "Delete Row", 1);
        deleteWindow.show();
        String[] result = deleteWindow.getData();
        
        if (result != null)
        {

            row = Integer.parseInt(result[0].trim());
            return row;
        }
        else
        {
            System.out.println("Input cancelled");
        }
        return 0;
    }

    public void deleteRowByNumber(int rowNumber) {
        
        icon = new ImageIcon("src/picts/dogIcon.png"); 
        
        confirmLabel = new JLabel("Delete row " + (rowNumber + 1) + "?", JLabel.CENTER);
        confirmLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        confirmPanel = new JPanel(new BorderLayout());
        confirmPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        confirmPanel.add(confirmLabel, BorderLayout.CENTER);
        
        confirmPane = new JOptionPane(
            confirmPanel,
            JOptionPane.PLAIN_MESSAGE,                    
            JOptionPane.YES_NO_OPTION                     
        );
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
        confirmDialog = confirmPane.createDialog("Confirm Delete");

        confirmDialog.setIconImage(icon.getImage());
        confirmDialog.setVisible(true);
        
        if (confirmPane.getValue().equals(JOptionPane.YES_OPTION)) {
            tableModel = (DefaultTableModel) deleteTable.getModel();
            tableModel.removeRow(rowNumber);
            updateRowNumbers();

            successLabel = new JLabel("Row deleted successfully", JLabel.CENTER);
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
    private void updateRowNumbers() {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(String.valueOf(i + 1), i, 0); 
        }
    }
}