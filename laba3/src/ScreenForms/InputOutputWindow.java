package ScreenForms;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

abstract public class InputOutputWindow
{
	protected String[] results;
    protected JTextField[] textFields;
    protected ImageIcon icon;
    protected JPanel IOPanel;
    protected JOptionPane IOPane;
    protected int numFields;
    protected String title;
    protected JDialog IODialog;
    protected JLabel IOLabel;
    protected JPanel fieldsPanel;
    protected Font ioDefaultFont;
    
    abstract public void show() throws IOException;
    
    public InputOutputWindow(String text, String title_window, int num_Fields)
    {	
    	ioDefaultFont = new Font("Arial", Font.PLAIN, 14);
        numFields = num_Fields;
        title = title_window;
        results = new String[numFields];
        textFields = new JTextField[numFields];
        icon = new ImageIcon("src/picts/dogIcon.png"); 
        
        IOPanel = new JPanel(new BorderLayout());
        IOPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        IOLabel = new JLabel(text, JLabel.CENTER);
        IOLabel.setFont(ioDefaultFont);
        
        fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        for (int i = 0; i < numFields; i++)
        {
            textFields[i] = new JTextField(15);
            textFields[i].setFont(ioDefaultFont);
            fieldsPanel.add(textFields[i]);
        }
        
        IOPanel.add(IOLabel, BorderLayout.NORTH);
        IOPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        IOPane = new JOptionPane(
            IOPanel,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION
        );
        
        UIManager.put("OptionPane.buttonFont", ioDefaultFont);
        
        IODialog = IOPane.createDialog(title);
        IODialog.setIconImage(icon.getImage());
    }
    
    public String[] getData()
    {
        if (IOPane.getValue() != null && IOPane.getValue().equals(JOptionPane.YES_OPTION)) 
        {
            for (int i = 0; i < numFields; i++)
            {
                results[i] = textFields[i].getText();
            }
            return results;
        }
        return null;
    }
    
    public void successOperationWindow(String text)
    {
    	String message = text + " successfully!";
    	JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public boolean confirmOperationWindow(String text)
    {
    	int result = JOptionPane.showConfirmDialog(null, text, title, JOptionPane.YES_NO_OPTION);
    	return result == JOptionPane.YES_OPTION;
    }
}