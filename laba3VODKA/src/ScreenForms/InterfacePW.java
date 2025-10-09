package ScreenForms;

import java.io.IOException;

/**
 * Interface for Printer Window functionality
 * Defines methods for printer-related operations
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface InterfacePW {
    /**
     * Shows the printer window
     * @throws IOException if there's an error displaying the window
     */
    void show() throws IOException;
}