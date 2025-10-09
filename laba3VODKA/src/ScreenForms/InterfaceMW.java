package ScreenForms;

import java.io.IOException;

/**
 * Interface for Main Window functionality
 * Defines methods for the main application window operations
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface InterfaceMW {
    
    /**
     * Shows the main application window
     * Makes the window visible to the user
     * @throws IOException if there's an error displaying the window
     */
    void show() throws IOException;
    
    /**
     * Handles all button clicks in the application
     * Processes user interactions with toolbar buttons
     * @param buttonIndex the index of the clicked button
     */
    void handleButtonClick(int buttonIndex);
    
    /**
     * Displays a confirmation dialog box for exiting the application
     * Shows exit confirmation with image and text before closing
     */
    void exitApplication();
}