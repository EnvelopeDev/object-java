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
public class FileManager implements interfaceFM
{
    /**
     * Default Constructor
     */
    public FileManager() {
    }

    /**
     * Reads dog data from CSV file and creates a list of Dog objects
     * CSV format: name;breed;awards (where awards is 0 or 1)
     * @param filePath the path to the CSV file to read from
     * @return List of Dog objects populated from the CSV file
     * @throws IOException if there's an error reading the file or file not found
     */
    @Override
    public List<Dog> inputFromCSV(String filePath) throws IOException 
    {
        List<Dog> dogList = new List<>();
        java.util.List<String> lines = Files.readAllLines(Paths.get(filePath));
        boolean hasAward;
        
        for(String line:lines) 
        {
            String[] dogInfo = line.split(";");
            hasAward = Integer.parseInt(dogInfo[2])==1;
            dogList.push_back(new Dog(dogInfo[0], dogInfo[1], hasAward));
        }
        
        return dogList;
    }

    /**
     * Writes dog data to CSV file in format: name;breed;awards
     * Awards are converted to 1 (true) or 0 (false)
     * @param filePath the path to the CSV file to write to
     * @param dogs the list of Dog objects to write to file
     * @throws IOException if there's an error writing to the file
     */
    @Override
    public void outputToCSV(String filePath, List<Dog> dogs) throws IOException 
    {
        String[] listString = dogs.convToStr();
        Files.write(Paths.get(filePath), Arrays.asList(listString));
    }
}