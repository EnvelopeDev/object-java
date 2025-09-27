package ScreenForms;

import javax.swing.*;
import java.awt.Image;

public class ToolWindow
{
    private JFrame toolFrame;
    /**
     * Constructor for ToolWindow with different contexts
     * @param contextType - type of operation (add, edit, search, etc.)
     * @param data - optional data for the operation
     */
    public ToolWindow(String contextType, String data)
    {
        toolFrame = new JFrame(contextType);
        ImageIcon icon = new ImageIcon("src/picts/dogIcon.png");
        toolFrame.setIconImage(icon.getImage());
        
        toolFrame.setSize(300, 200);
        toolFrame.setLocationRelativeTo(null);
        toolFrame.setVisible(true);
    }
}