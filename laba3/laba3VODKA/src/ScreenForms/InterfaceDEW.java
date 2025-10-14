package ScreenForms;

import javax.swing.*;

/**
 * Interface for Delete Element Window functionality
 * Defines methods for deleting rows from tables
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface InterfaceDEW {
    
    /**
     * Deletes a specific row from the table
     * Asks user to confirm before deleting
     * @param rowNumber the row to delete (starting from 1)
     */
    void deleteRowByNumber(int rowNumber);
    
    /**
     * Updates row numbers after deletion to maintain sequence
     * Makes sure all rows have correct sequential numbers
     */
    void updateRowNumbers();
}