package ScreenForms;

import javax.swing.*;
import java.io.IOException;

/**
 * Interface for Add Element Window functionality
 * Defines methods for adding rows to tables
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface InterfaceAEW {

    /**
     * Adds a new row to the table with user input data
     * Creates new row with number, name, breed, and awards
     * @throws IOException if there's an error during the operation
     */
    void addRowToTable() throws IOException;
    
    /**
     * Shows a success window after adding a row
     * Displays confirmation message to user
     */
    void showSuccessWindow();
}