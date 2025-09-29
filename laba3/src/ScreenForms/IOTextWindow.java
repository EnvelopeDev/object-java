package ScreenForms;

import javax.swing.*;
import java.awt.*;

public class IOTextWindow
{
    private String[] results;
    private JTextField[] textFields;
    private ImageIcon icon;
    private JPanel IOPanel;
    private JOptionPane optionPane;
    private int numFields;
    private String title;
    private JDialog inputDialog;
    private JLabel textLabel;
    private JPanel fieldsPanel;
    
    
    public IOTextWindow(String text, String title_window, int num_Fields)
    {	
        numFields = num_Fields;
        title = title_window;
        results = new String[numFields];
        textFields = new JTextField[numFields];
        icon = new ImageIcon("src/picts/dogIcon.png"); 
        
        IOPanel = new JPanel(new BorderLayout());
        IOPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        textLabel = new JLabel(text, JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        for (int i = 0; i < numFields; i++)
        {
            textFields[i] = new JTextField(15);
            textFields[i].setFont(new Font("Arial", Font.PLAIN, 14));
            fieldsPanel.add(textFields[i]);
        }
        
        IOPanel.add(textLabel, BorderLayout.NORTH);
        IOPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        optionPane = new JOptionPane(
            IOPanel,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION
        );
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
        
        inputDialog = optionPane.createDialog(title);
        inputDialog.setIconImage(icon.getImage());
    }
    
    public String[] getData()
    {
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) 
        {
            for (int i = 0; i < numFields; i++)
            {
                results[i] = textFields[i].getText();
            }
            return results;
        }
        return null;
    }
    
    public void show()
    {	
    	inputDialog.setVisible(true);
    }
}