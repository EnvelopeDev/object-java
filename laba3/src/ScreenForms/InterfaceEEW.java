package ScreenForms;

import javax.swing.*;
import java.io.IOException;

/**
 * Interface for Edit Element Window functionality
 * Defines methods for editing existing table rows
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface InterfaceEEW {
    
	 /**
     * Shows the edit window and processes user input
     * First asks for row number, then asks for new values
     * @throws IOException if there's an error during input/output operations
     */
    void show() throws IOException;
}