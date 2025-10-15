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
 * @author Gushchin Kirill
 * @version 1.0
 */
public class PrintFunction
{
	/**
	 * Shows the printer error window
	 * Displays an error message when printer is not found
	 * @throws IOException if there's an error displaying the window
	 */
	public static void show() throws IOException
    {	
		// Set application icon
		ImageIcon icon = new ImageIcon("src/picts/icon.png");
		
		// Create main panel for printer error message
		JPanel printPanel = new JPanel(new BorderLayout());
        
        // Load and scale printer error image
        ImageIcon printerImage = new ImageIcon("src/picts/printer.jpeg");
        Image scaledImage = printerImage.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
 
        // Create error message label
        JLabel textLabel = new JLabel("The system cannot see the printer. Check the connection and try again...", JLabel.CENTER);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
        // Add image and text to panel
        printPanel.add(imageLabel, BorderLayout.CENTER);
        printPanel.add(textLabel, BorderLayout.SOUTH);
        
        // Set font for dialog buttons
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 16));

        // Create option pane for error message
        JOptionPane optionPane = new JOptionPane(
                printPanel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION
            );
        
        // Create and configure dialog window
        JDialog dialog = optionPane.createDialog("Printing error");
        dialog.setModal(false); // Allow interaction with other windows
        dialog.setVisible(true); // Show the dialog
        dialog.setIconImage(icon.getImage()); // Set window icon
        
	}
}