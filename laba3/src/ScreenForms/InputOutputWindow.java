package ScreenForms;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

abstract public class InputOutputWindow
{
	protected String[] results;
    protected JTextField[] textFields;
    protected ImageIcon icon;
    protected int numFields;
    protected String title;
    protected JPanel fieldsPanel;
    protected Font ioDefaultFont;
    protected JPanel IOPanel;
    protected JOptionPane IOPane;
    protected JLabel IOLabel;
    protected JDialog IODialog;
    protected JPanel SCSPanel;
    protected JOptionPane SCSPane;
    protected JLabel SCSLabel;
    protected JDialog SCSDialog;
    protected JPanel CONPanel;
    protected JOptionPane CONPane;
    protected JLabel CONLabel;
    protected JDialog CONDialog;
    
    
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
        if (IOPane.getValue().equals(JOptionPane.YES_OPTION)) 
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
    	SCSPanel = new JPanel(new BorderLayout());
        SCSPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        SCSLabel = new JLabel(message, JLabel.CENTER);
        SCSLabel.setFont(ioDefaultFont);
        
    	SCSPane = new JOptionPane(
    			SCSLabel,
    			JOptionPane.DEFAULT_OPTION
    			);
    	
    	UIManager.put("OptionPane.buttonFont", ioDefaultFont);
        
    	SCSDialog = SCSPane.createDialog(title);
    	SCSDialog.setIconImage(icon.getImage());				
    }
    
    public boolean confirmOperationWindow(String text)
    {
    	CONPanel = new JPanel(new BorderLayout());
    	CONPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
    	CONLabel = new JLabel(text, JLabel.CENTER);
    	CONLabel.setFont(ioDefaultFont);
        
    	CONPane = new JOptionPane(
    			CONLabel,
    			JOptionPane.PLAIN_MESSAGE,
    			JOptionPane.YES_NO_OPTION
    			);
    	
    	UIManager.put("OptionPane.buttonFont", ioDefaultFont);
        
    	CONDialog = CONPane.createDialog(title);
    	CONDialog.setIconImage(icon.getImage());
    	CONDialog.setVisible(true);
    	
    	if (CONPane.getValue().equals(JOptionPane.YES_OPTION)) 
        {
            
            return true;
        }
        return false;
    }
}