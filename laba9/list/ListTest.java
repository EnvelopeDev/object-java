package list;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * JUnit test class for the List class.
 * Tests all public methods of the generic List implementation.
 * This test class verifies the data structure functionality.
 * 
 * @author Your Name
 * @version 1.0
 * @see List
 */
public class ListTest {
    
    private List<String> stringList;
    private List<Integer> integerList;
    
    /**
     * Rule for testing expected exceptions.
     */
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    /**
     * Sets up the test fixture before each test method.
     * Creates new List instances for testing.
     */
    @Before
    public void setUp() {
        stringList = new List<>();
        integerList = new List<>();
    }
    
    /**
     * Tests an empty list.
     * Verifies that a newly created list is empty.
     */
    @Test
    public void testEmptyList() {
        assertTrue("New list should be empty", stringList.isEmpty());
        assertEquals("Empty list size should be 0", 0, stringList.getSize());
    }
    
    /**
     * Tests the push_back() method.
     * Verifies that elements can be added to the end of the list.
     */
    @Test
    public void testPushBack() {
        stringList.push_back("First");
        assertFalse("List should not be empty after push_back", stringList.isEmpty());
        assertEquals("List size should be 1", 1, stringList.getSize());
        assertEquals("First element should be 'First'", "First", stringList.at(0));
    }
    
    /**
     * Tests the push_front() method.
     * Verifies that elements can be added to the beginning of the list.
     */
    @Test
    public void testPushFront() {
        stringList.push_front("First");
        assertEquals("First element should be 'First'", "First", stringList.at(0));
        
        stringList.push_front("Second");
        assertEquals("New first element should be 'Second'", "Second", stringList.at(0));
    }
    
    /**
     * Tests the insert() method.
     * Verifies that elements can be inserted at specific positions.
     */
    @Test
    public void testInsert() {
        stringList.push_back("A");
        stringList.push_back("C");
        stringList.insert("B", 1);
        
        assertEquals("List size should be 3", 3, stringList.getSize());
        assertEquals("A", stringList.at(0));
        assertEquals("B", stringList.at(1));
        assertEquals("C", stringList.at(2));
    }
    
    /**
     * Tests the getSize() method.
     * Verifies that list size is correctly reported.
     */
    @Test
    public void testGetSize() {
        assertEquals("Initial size should be 0", 0, stringList.getSize());
        stringList.push_back("A");
        assertEquals("Size after adding one element should be 1", 1, stringList.getSize());
    }
    
    /**
     * Tests the at() method with valid indices.
     * Verifies that elements can be retrieved by index.
     */
    @Test
    public void testAtValidIndex() {
        stringList.push_back("A");
        stringList.push_back("B");
        stringList.push_back("C");
        
        assertEquals("Element at index 0 should be 'A'", "A", stringList.at(0));
        assertEquals("Element at index 1 should be 'B'", "B", stringList.at(1));
        assertEquals("Element at index 2 should be 'C'", "C", stringList.at(2));
    }
    
    /**
     * Tests the at() method with invalid index.
     * Verifies that IndexOutOfBoundsException is thrown for invalid index.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAtInvalidIndex() {
        stringList.push_back("A");
        stringList.at(1); // Should throw IndexOutOfBoundsException
    }
    
    /**
     * Tests the remove() method.
     * Verifies that elements can be removed from the list.
     */
    @Test
    public void testRemove() {
        stringList.push_back("A");
        stringList.push_back("B");
        stringList.push_back("C");
        
        stringList.remove(1); // Remove "B"
        
        assertEquals("Size after removal should be 2", 2, stringList.getSize());
        assertEquals("First element should remain 'A'", "A", stringList.at(0));
        assertEquals("Second element should now be 'C'", "C", stringList.at(1));
    }
    
    /**
     * Tests the contains() method.
     * Verifies that the list correctly reports if it contains elements.
     */
    @Test
    public void testContains() {
        stringList.push_back("A");
        stringList.push_back("B");
        
        assertTrue("List should contain 'A'", stringList.contains("A"));
        assertTrue("List should contain 'B'", stringList.contains("B"));
        assertFalse("List should not contain 'C'", stringList.contains("C"));
    }
    
    /**
     * Tests the clear() method.
     * Verifies that the list can be completely cleared.
     */
    @Test
    public void testClear() {
        stringList.push_back("A");
        stringList.push_back("B");
        stringList.push_back("C");
        
        assertEquals("Initial size should be 3", 3, stringList.getSize());
        
        stringList.clear();
        
        assertTrue("List should be empty after clear", stringList.isEmpty());
        assertEquals("Size should be 0 after clear", 0, stringList.getSize());
    }
    
    /**
     * Tests the convToStr() method.
     * Verifies that the list can be converted to string array.
     */
    @Test
    public void testConvToStr() {
        stringList.push_back("First");
        stringList.push_back("Second");
        
        String[] result = stringList.convToStr();
        
        assertNotNull("Result should not be null", result);
        assertEquals("Array length should be 2", 2, result.length);
        assertEquals("First element should be 'First'", "First", result[0]);
        assertEquals("Second element should be 'Second'", "Second", result[1]);
    }
}