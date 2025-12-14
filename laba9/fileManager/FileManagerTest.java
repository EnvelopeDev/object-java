package fileManager;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.*;
import java.nio.file.*;

// Импортируем только ваши классы
import list.List;
import object.dog.Dog;

/**
 * JUnit тесты для класса FileManager
 */
public class FileManagerTest {
    
    private FileManager fileManager;
    private static final String TEST_INPUT_FILE = "test_dogs.csv";
    private static final String TEST_OUTPUT_FILE = "test_output.csv";
    
    @Before
    public void setUp() throws IOException {
        fileManager = new FileManager();
        
        // Создаем тестовый CSV файл
        String testData = "Rex;German Shepherd;1\n" +
                         "Bella;Golden Retriever;0\n" +
                         "Max;Labrador;1";
        Files.write(Paths.get(TEST_INPUT_FILE), testData.getBytes());
    }
    
    @After
    public void tearDown() {
        // Удаляем тестовые файлы
        try {
            Files.deleteIfExists(Paths.get(TEST_INPUT_FILE));
            Files.deleteIfExists(Paths.get(TEST_OUTPUT_FILE));
        } catch (IOException e) {
            // Игнорируем ошибки удаления
        }
    }
    
    @Test
    public void testFileManagerConstructor() {
        assertNotNull("FileManager должен быть создан", fileManager);
    }
    
    @Test
    public void testInputFromCSV() throws IOException {
        List<Dog> dogs = fileManager.inputFromCSV(TEST_INPUT_FILE);
        
        assertNotNull("Список не должен быть null", dogs);
        assertEquals("Должно быть 3 собаки", 3, dogs.getSize());
        
        // Проверяем собак
        assertEquals("Rex", dogs.at(0).getName());
        assertEquals("German Shepherd", dogs.at(0).getBreed());
        assertTrue(dogs.at(0).hasAward());
        
        assertEquals("Bella", dogs.at(1).getName());
        assertEquals("Golden Retriever", dogs.at(1).getBreed());
        assertFalse(dogs.at(1).hasAward());
        
        assertEquals("Max", dogs.at(2).getName());
        assertEquals("Labrador", dogs.at(2).getBreed());
        assertTrue(dogs.at(2).hasAward());
    }
    
    @Test(expected = IOException.class)
    public void testInputFromCSVFileNotFound() throws IOException {
        fileManager.inputFromCSV("non_existent_file.csv");
    }
    
    @Test
    public void testOutputToCSV() throws IOException {
        List<Dog> dogs = new List<>();
        dogs.push_back(new Dog("Charlie", "Beagle", true));
        dogs.push_back(new Dog("Lucy", "Poodle", false));
        dogs.push_back(new Dog("Rocky", "Boxer", true));
        
        fileManager.outputToCSV(TEST_OUTPUT_FILE, dogs);
        
        assertTrue("Файл должен быть создан", Files.exists(Paths.get(TEST_OUTPUT_FILE)));
        
        // Читаем и проверяем
        java.util.List<String> lines = Files.readAllLines(Paths.get(TEST_OUTPUT_FILE));
        assertEquals(3, lines.size());
        assertEquals("Charlie;Beagle;1", lines.get(0));
        assertEquals("Lucy;Poodle;0", lines.get(1));
        assertEquals("Rocky;Boxer;1", lines.get(2));
    }
    
    @Test
    public void testEmptyDogListOutput() throws IOException {
        List<Dog> emptyList = new List<>();
        fileManager.outputToCSV(TEST_OUTPUT_FILE, emptyList);
        
        assertTrue("Файл должен быть создан", Files.exists(Paths.get(TEST_OUTPUT_FILE)));
    }
}