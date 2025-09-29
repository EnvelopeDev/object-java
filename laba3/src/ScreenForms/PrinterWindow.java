package ScreenForms;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.UIManager;

public class PrinterWindow
{
	public static void show() throws IOException
    {	
		
		ImageIcon icon = new ImageIcon("src/picts/dogIcon.png");
		JPanel printPanel = new JPanel(new BorderLayout());
        
        ImageIcon exitImage = new ImageIcon("src/picts/printer.png");
        Image scaledImage = exitImage.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
 
        JLabel textLabel = new JLabel("The system cannot see the printer. Check the connection and try again...", JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        printPanel.add(imageLabel, BorderLayout.CENTER);
        printPanel.add(textLabel, BorderLayout.SOUTH);
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 16));

        JOptionPane optionPane = new JOptionPane(
                printPanel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION
            );
        
        JDialog dialog = optionPane.createDialog("Printing error");
        dialog.setModal(false);
        dialog.setVisible(true);
        dialog.setIconImage(icon.getImage());
        
	}
}
