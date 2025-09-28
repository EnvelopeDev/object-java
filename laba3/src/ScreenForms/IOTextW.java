package ScreenForms;

import javax.swing.*;
import java.awt.*;

public class IOTextW 
{
    private String[] results;

    public String[] use(String text, String title, int numFields) 
    {
        results = new String[numFields];
        
        ImageIcon icon = new ImageIcon("src/picts/dogIcon.png");
        JPanel IOPanel = new JPanel(new BorderLayout());
        IOPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
 
        JPanel textPanel = new JPanel(new BorderLayout());
        JLabel textLabel = new JLabel(text, JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        textPanel.add(textLabel);
        
        JPanel FieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JTextField[] textFields = new JTextField[numFields];

        for (int i = 0; i < numFields; i++)
        {
            textFields[i] = new JTextField(15);
            textFields[i].setFont(new Font("Arial", Font.PLAIN, 14));
            FieldsPanel.add(textFields[i]);
        }
        
        IOPanel.add(FieldsPanel, BorderLayout.CENTER);
        IOPanel.add(textPanel, BorderLayout.NORTH);
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 14));

        JOptionPane optionPane = new JOptionPane(
            IOPanel,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.YES_NO_OPTION
        );
        
        JDialog dialog = optionPane.createDialog(title);
        dialog.setIconImage(icon.getImage());
        dialog.setVisible(true);

        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) // value == 0 
        {
            for (int i = 0; i < textFields.length; i++)
            {
                results[i] = textFields[i].getText();
            }
            return results;
        }
        return null;
    }
}