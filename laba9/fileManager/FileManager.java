package fileManager;

import list.List;
import object.dog.Dog;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;

/**
 * Class for managing file operations with dog data
 * Implements reading and writing data in CSV format
 * @author Vadim Ustinov
 * @version 1.0
 */
public class FileManager  // УБРАЛИ "implements interfaceFM"
{
    /**
     * Default Constructor
     */
    public FileManager() {
    }

    /**
     * Reads dog data from CSV file
     */
    public List<Dog> inputFromCSV(String filePath) throws IOException 
    {
        List<Dog> dogList = new List<>();
        
        // Read all lines from file
        java.util.List<String> lines = Files.readAllLines(Paths.get(filePath));
        boolean hasAward;
        
        for(String line: lines) 
        {
            String[] dogInfo = line.split(";");
            if (dogInfo.length >= 3) {
                hasAward = Integer.parseInt(dogInfo[2]) == 1;
                dogList.push_back(new Dog(dogInfo[0], dogInfo[1], hasAward));
            }
        }
        
        return dogList;
    }

    /**
     * Writes dog data to CSV file
     */
    public void outputToCSV(String filePath, List<Dog> dogs) throws IOException 
    {
        String[] listString = dogs.convToStr();
        if (listString != null && listString.length > 0) {
            Files.write(Paths.get(filePath), Arrays.asList(listString));
        } else {
            // If list is empty, create empty file
            Files.write(Paths.get(filePath), new byte[0]);
        }
    }
}