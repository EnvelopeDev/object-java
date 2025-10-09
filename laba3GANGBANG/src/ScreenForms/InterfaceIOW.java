package ScreenForms;

import java.io.IOException;

/**
 * Interface for Input Output Window functionality
 * Defines common methods for all input/output dialog windows
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface InterfaceIOW {
    
    /**
     * Gets data entered by the user from input fields
     * @return array of strings with user input, or null if cancelled
     */
    String[] getData();
    
    /**
     * Shows a success message window after operation
     * @param text the success message to display
     */
    void successOperationWindow(String text);
    
    /**
     * Shows a confirmation dialog window for user approval
     * @param title_window the title of the confirmation window
     * @param text the confirmation message to display
     * @return true if user confirmed, false otherwise
     */
    boolean confirmOperationWindow(String title_window, String text);
    
    /**
     * Gets a row number from user input
     * Used for edit and delete operations
     * @return the row number entered by user
     * @throws IOException if there's an error during input
     */
    int getRow() throws IOException;
    
    /**
     * Shows the dialog window and processes user interaction
     * @throws IOException if there's an error displaying the window
     */
    void show() throws IOException;
}