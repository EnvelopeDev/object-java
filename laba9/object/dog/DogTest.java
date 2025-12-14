package object.dog;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * JUnit test class for the Dog class.
 * Tests all public methods and constructor of the Dog class.
 * This test class verifies the business logic of dog entities.
 * 
 * @author Your Name
 * @version 1.0
 * @see Dog
 */
public class DogTest {
    
    private Dog dog;
    
    /**
     * Sets up the test fixture before each test method.
     * Creates a new Dog instance for testing.
     */
    @Before
    public void setUp() {
        dog = new Dog("Rex", "German Shepherd", true);
    }
    
    /**
     * Tests the Dog constructor.
     * Verifies that a Dog object can be created successfully.
     */
    @Test
    public void testDogConstructor() {
        assertNotNull("Dog object should be created", dog);
    }
    
    /**
     * Tests the getName() method.
     * Verifies that the dog's name is correctly returned.
     */
    @Test
    public void testGetName() {
        assertEquals("Dog name should be 'Rex'", "Rex", dog.getName());
    }
    
    /**
     * Tests the getBreed() method.
     * Verifies that the dog's breed is correctly returned.
     */
    @Test
    public void testGetBreed() {
        assertEquals("Dog breed should be 'German Shepherd'", 
                     "German Shepherd", dog.getBreed());
    }
    
    /**
     * Tests the hasAward() method.
     * Verifies that the award status is correctly returned.
     */
    @Test
    public void testHasAward() {
        assertTrue("Dog should have awards", dog.hasAward());
    }
    
    /**
     * Tests the setName() method.
     * Verifies that the dog's name can be changed.
     */
    @Test
    public void testSetName() {
        dog.setName("Max");
        assertEquals("Dog name should be changed to 'Max'", "Max", dog.getName());
    }
    
    /**
     * Tests the setBreed() method.
     * Verifies that the dog's breed can be changed.
     */
    @Test
    public void testSetBreed() {
        dog.setBreed("Labrador");
        assertEquals("Dog breed should be changed to 'Labrador'", 
                     "Labrador", dog.getBreed());
    }
    
    /**
     * Tests the setAwards() method.
     * Verifies that the award status can be changed.
     */
    @Test
    public void testSetAwards() {
        dog.setAwards(false);
        assertFalse("Dog should not have awards after setAwards(false)", dog.hasAward());
        
        dog.setAwards(true);
        assertTrue("Dog should have awards after setAwards(true)", dog.hasAward());
    }
    
    /**
     * Tests the toString() method.
     * Verifies that the string representation is in correct CSV format.
     */
    @Test
    public void testToString() {
        String expected = "Rex;German Shepherd;1";
        assertEquals("String representation should match CSV format", 
                     expected, dog.toString());
    }
    
    /**
     * Tests a dog without awards.
     * Verifies that toString() correctly represents dogs without awards.
     */
    @Test
    public void testDogWithoutAwards() {
        Dog noAwardDog = new Dog("Bella", "Golden Retriever", false);
        assertFalse("Dog should not have awards", noAwardDog.hasAward());
        assertEquals("Bella;Golden Retriever;0", noAwardDog.toString());
    }
}