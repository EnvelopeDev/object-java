package fileManager;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.*;
import java.nio.file.*;

// Import custom classes
import list.List;
import object.dog.Dog;

/**
 * JUnit test class for the FileManager class.
 * Tests file input/output operations with CSV files.
 * This test class handles integration testing with the file system.
 * 
 * @author Your Name
 * @version 1.0
 * @see FileManager
 */
public class FileManagerTest {
    
    private FileManager fileManager;
    private static final String TEST_INPUT_FILE = "test_dogs.csv";
    private static final String TEST_OUTPUT_FILE = "test_output.csv";
    
    /**
     * Sets up the test fixture before each test method.
     * Creates a FileManager instance and test CSV file.
     * @throws IOException if file creation fails
     */
    @Before
    public void setUp() throws IOException {
        fileManager = new FileManager();
        
        // Create test CSV file
        String testData = "Rex;German Shepherd;1\n" +
                         "Bella;Golden Retriever;0\n" +
                         "Max;Labrador;1";
        Files.write(Paths.get(TEST_INPUT_FILE), testData.getBytes());
    }
    
    /**
     * Cleans up the test fixture after each test method.
     * Deletes test files created during testing.
     */
    @After
    public void tearDown() {
        // Clean up test files
        try {
            Files.deleteIfExists(Paths.get(TEST_INPUT_FILE));
            Files.deleteIfExists(Paths.get(TEST_OUTPUT_FILE));
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }
    
    /**
     * Tests the FileManager constructor.
     * Verifies that a FileManager object can be created.
     */
    @Test
    public void testFileManagerConstructor() {
        assertNotNull("FileManager instance should be created", fileManager);
    }
    
    /**
     * Tests the inputFromCSV() method.
     * Verifies that data can be read from a CSV file.
     * @throws IOException if file reading fails
     */
    @Test
    public void testInputFromCSV() throws IOException {
        List<Dog> dogs = fileManager.inputFromCSV(TEST_INPUT_FILE);
        
        assertNotNull("Dog list should not be null", dogs);
        assertEquals("Should read 3 dogs from file", 3, dogs.getSize());
        
        // Verify first dog
        assertEquals("First dog name should be 'Rex'", "Rex", dogs.at(0).getName());
        assertEquals("First dog breed should be 'German Shepherd'", 
                     "German Shepherd", dogs.at(0).getBreed());
        assertTrue("First dog should have awards", dogs.at(0).hasAward());
    }
    
    /**
     * Tests the inputFromCSV() method with non-existent file.
     * Verifies that IOException is thrown when file doesn't exist.
     * @throws IOException expected to be thrown
     */
    @Test(expected = IOException.class)
    public void testInputFromCSVFileNotFound() throws IOException {
        fileManager.inputFromCSV("non_existent_file.csv");
    }
    
    /**
     * Tests the outputToCSV() method.
     * Verifies that data can be written to a CSV file.
     * @throws IOException if file writing fails
     */
    @Test
    public void testOutputToCSV() throws IOException {
        List<Dog> dogs = new List<>();
        dogs.push_back(new Dog("Charlie", "Beagle", true));
        dogs.push_back(new Dog("Lucy", "Poodle", false));
        dogs.push_back(new Dog("Rocky", "Boxer", true));
        
        fileManager.outputToCSV(TEST_OUTPUT_FILE, dogs);
        
        assertTrue("Output file should be created", 
                   Files.exists(Paths.get(TEST_OUTPUT_FILE)));
        
        // Verify file content
        java.util.List<String> lines = Files.readAllLines(Paths.get(TEST_OUTPUT_FILE));
        assertEquals("File should contain 3 lines", 3, lines.size());
        assertEquals("First line should match", "Charlie;Beagle;1", lines.get(0));
    }
    
    /**
     * Tests the complete input-output cycle.
     * Verifies that data can be read, written, and read again without corruption.
     * @throws IOException if any file operation fails
     */
    @Test
    public void testInputOutputCycle() throws IOException {
        // Read original data
        List<Dog> originalDogs = fileManager.inputFromCSV(TEST_INPUT_FILE);
        
        // Write to new file
        fileManager.outputToCSV(TEST_OUTPUT_FILE, originalDogs);
        
        // Read back from new file
        List<Dog> readDogs = fileManager.inputFromCSV(TEST_OUTPUT_FILE);
        
        // Verify data integrity
        assertEquals("Number of dogs should match", 
                     originalDogs.getSize(), readDogs.getSize());
        
        for (int i = 0; i < originalDogs.getSize(); i++) {
            assertEquals("Dog names should match", 
                         originalDogs.at(i).getName(), readDogs.at(i).getName());
        }
    }
    
    /**
     * Tests outputToCSV() with empty list.
     * Verifies that empty lists can be handled correctly.
     * @throws IOException if file writing fails
     */
    @Test
    public void testEmptyDogListOutput() throws IOException {
        List<Dog> emptyList = new List<>();
        fileManager.outputToCSV(TEST_OUTPUT_FILE, emptyList);
        
        assertTrue("File should be created even for empty list", 
                   Files.exists(Paths.get(TEST_OUTPUT_FILE)));
    }
}