package fileManager;

import list.interfaceList;
import java.io.IOException;
import list.List;
import object.dog.*;

/**
 * Interface for managing file operations with dog data
 * Defines methods for reading and writing data in CSV format
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface interfaceFM 
{
    /**
     * Function reads data about dogs from a CSV file
     * @param filePath is the path to the CSV file
     * @return list of Dog objects
     * @throws IOException if an I/O error has occurred
     */
    List<Dog> inputFromCSV(String filePath) throws IOException;
    
    /**
     * Function records information about dogs in a CSV file
     * @param filePath is the path to the CSV file
     * @param dogs is a list of Dog objects to save
     * @throws IOException if an I/O error has occurred
     */
    void outputToCSV(String filePath, List<Dog> dogs) throws IOException;
}