package ScreenForms;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class InputOutputWindow
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
    
    
    public InputOutputWindow(String text, String title_window, int num_Fields)
    {	
        numFields = num_Fields;
        title = title_window;
        results = new String[numFields];
        textFields = new JTextField[numFields];
        icon = new ImageIcon("src/picts/dogIcon.png"); 
        
        IOPanel = new JPanel(new BorderLayout());
        IOPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        IOLabel = new JLabel(text, JLabel.CENTER);
        IOLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        for (int i = 0; i < numFields; i++)
        {
            textFields[i] = new JTextField(15);
            textFields[i].setFont(new Font("Arial", Font.PLAIN, 14));
            fieldsPanel.add(textFields[i]);
        }
        
        IOPanel.add(IOLabel, BorderLayout.NORTH);
        IOPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        IOPane = new JOptionPane(
            IOPanel,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION
        );
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
        
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
    	IOLabel = new JLabel(text +"successfully", JLabel.CENTER);
    	IOLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
    	IOPanel = new JPanel(new BorderLayout());
    	IOPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    	IOPanel.add(IOLabel, BorderLayout.CENTER);
        
    	IOPane = new JOptionPane(
    			IOPanel,
            JOptionPane.PLAIN_MESSAGE,                    
            JOptionPane.DEFAULT_OPTION                     
        );
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
        IODialog = IOPane.createDialog("Success");

        IODialog.setIconImage(icon.getImage());
    }
    
    public boolean confirmOperationWindow(String title_window, String text)
    {
    	IOLabel = new JLabel(text, JLabel.CENTER);
    	IOLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
    	IOPanel = new JPanel(new BorderLayout());
    	IOPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    	IOPanel.add(IOLabel, BorderLayout.CENTER);
        
    	IOPane = new JOptionPane(
    		IOPanel,
            JOptionPane.PLAIN_MESSAGE,                    
            JOptionPane.YES_NO_OPTION                     
        );
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));
        IODialog = IOPane.createDialog(title_window);
        IODialog.setIconImage(icon.getImage()); 
        IODialog.setVisible(true);
        
        if (IOPane.getValue().equals(JOptionPane.YES_OPTION))
		{
        	return true;
		}
        return false;
    }
    
    public int getRow() throws IOException
    {
        show();
        String[] result = getData();
        
        if (result != null)
        {
        	return Integer.parseInt(result[0].trim());
        }
        else
        {
            System.out.println("Input cancelled");
        }
        return 0;
    }

    public void show()
    {	
    	IODialog.setVisible(true);
    }
}