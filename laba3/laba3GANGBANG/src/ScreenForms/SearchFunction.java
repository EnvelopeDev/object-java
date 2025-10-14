package ScreenForms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;

/**
 * Search window for finding dogs by various criteria
 * Provides non-modal search functionality that remains open during use
 * @author Gushchin Kirill
 * @version 1.0
 */
public class SearchFunction extends DialogWindow
{
    
    private JTable dogsTable;
    private static String[][] originalTableData;
    
    /**
     * Creates a new search window
     * @param text the instruction text to show to user
     * @param title_window the title of the window
     * @param num_Fields how many input fields to create
     * @param table the table to search in
     */
    public SearchFunction(String text, String title_window, int num_Fields, JTable table)
    {
        super(text, title_window, num_Fields);
        this.dogsTable = table;
        createSearchDialog(text, title_window, num_Fields);
    }
    
    /**
     * Displays the search window
     * @throws IOException if there's an error displaying the window
     */
    @Override
    public void show() throws IOException
    {
        IODialog.setVisible(true);
    }
    
    /**
     * Performs search action when search button is clicked
     * Executes search with current input or restores data if empty
     */
    @Override
    protected void performSearchAction()
    {
        String searchText = textFields[0].getText();
        if (searchText == null || searchText.trim().isEmpty())
        {
            restoreData();
        } 
        else
        {
            performSearch(searchText);
        }
    }
    
    /**
     * Restores original data when search window is closed
     */
    @Override
    protected void restoreDataOnClose() 
    {
        restoreData();
    }
    
    /**
     * Performs the actual search operation
     * Searches for records that start with the specified text
     * @param searchText the text to search for
     */
    private void performSearch(String searchText) {
        searchText = searchText.toLowerCase().trim();
        
        // Get the table model
        DefaultTableModel tableModel = (DefaultTableModel) dogsTable.getModel();
        
        // Clear the table
        tableModel.setRowCount(0);
        
        // Search through original data for matching records
        for (int i = 0; i < originalTableData.length; i++)
        {
            String[] row = originalTableData[i];
            boolean found = false;
            
            // Check each cell in the row for matches
            for(String cell : row)
            {
                if (cell != null && cell.toLowerCase().startsWith(searchText))
                {
                    found = true;
                    break; 
                }
            }
            
            // Add matching row to table
            if(found) {
                tableModel.addRow(row);
            }
        }
    }
    
    /**
     * Restores the original data to the table
     * Used when search is cleared or window is closed
     */
    private void restoreData() {
        DefaultTableModel tableModel = (DefaultTableModel) dogsTable.getModel();
        tableModel.setRowCount(0);
        
        for (String[] row : originalTableData)
        {
            tableModel.addRow(row);
        }
    }
    
    /**
     * Sets the original data for search functionality
     * @param data the original table data to search through
     */
    public static void setOriginalData(String[][] data)
    {
        originalTableData = new String[data.length][4];
        for (int i = 0; i < data.length; i++)
        {
            System.arraycopy(data[i], 0, originalTableData[i], 0, 4);
        }
    }
}