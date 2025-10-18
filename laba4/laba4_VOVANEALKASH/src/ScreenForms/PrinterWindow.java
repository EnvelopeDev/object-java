package ScreenForms;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.UIManager;

/**
 * Window for displaying printer error messages
 * Shows a dialog when printer is not available
 * @author Vadim Ustinov
 * @version 1.0
 */
public class PrinterWindow
{
	private ImageIcon icon;
	private JPanel printPanel;
	private JDialog dialog;
	private JLabel imageLabel;
	private JLabel textLabel;
	private ImageIcon printerImage ;
	private Image scaledImage;
	private static final String[] imagePaths = {
			"src/picts/dogIcon.png",
			"src/picts/printer.png"
            
    };
	/**
	 * Shows the printer error window
	 * Displays an error message when printer is not found
	 */
	public void show()
    {	
		icon = new ImageIcon(imagePaths[0]);
		
		printPanel = new JPanel(new BorderLayout());
        
        printerImage = new ImageIcon(imagePaths[1]);
        scaledImage = printerImage.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
 
        textLabel = new JLabel("The system cannot see the printer. Check the connection and try again...", JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        printPanel.add(imageLabel, BorderLayout.CENTER);
        printPanel.add(textLabel, BorderLayout.SOUTH);
        
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 16));

        JOptionPane optionPane = new JOptionPane(
                printPanel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION
            );
        
        dialog = optionPane.createDialog("Error");
        dialog.setModal(false); 
        dialog.setVisible(true); 
        dialog.setIconImage(icon.getImage()); 
        
	}
}