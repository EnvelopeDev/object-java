package fileManager;

import list.List;
import object.dog.Dog;
import java.io.IOException;

/**
 * Package for managing file operations with dog data
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
    
    /**
     * Reads dog data from XML file and creates a list of Dog objects
     * @param filePath the path to the XML file to read from
     * @return List of Dog objects populated from the XML file
     * @throws IOException if there's an error reading the file or file not found
     */
    List<Dog> inputFromXML(String filePath) throws IOException;
    
    /**
     * Writes dog data to XML file
     * @param filePath the path to the XML file to write to
     * @param dogs the list of Dog objects to write to file
     * @throws IOException if there's an error writing to the file
     */
    void outputToXML(String filePath, List<Dog> dogs) throws IOException;
}