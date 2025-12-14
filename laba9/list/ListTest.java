package list;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class ListTest {
    
    private List<String> stringList;
    
    @Before
    public void setUp() {
        stringList = new List<>();
    }
    
    @Test
    public void testEmptyList() {
        assertTrue("Список должен быть пустым", stringList.isEmpty());
        assertEquals("Размер должен быть 0", 0, stringList.getSize());
    }
    
    @Test
    public void testPushBack() {
        stringList.push_back("First");
        assertFalse("Список не должен быть пустым", stringList.isEmpty());
        assertEquals("Размер должен быть 1", 1, stringList.getSize());
        assertEquals("Первый элемент", "First", stringList.at(0));
        
        stringList.push_back("Second");
        assertEquals("Размер должен быть 2", 2, stringList.getSize());
        assertEquals("Второй элемент", "Second", stringList.at(1));
    }
    
    @Test
    public void testPushFront() {
        stringList.push_front("First");
        assertEquals("First", stringList.at(0));
        
        stringList.push_front("Second");
        assertEquals("Second должен быть первым", "Second", stringList.at(0));
        assertEquals("First должен быть вторым", "First", stringList.at(1));
    }
    
    @Test
    public void testGetSize() {
        assertEquals(0, stringList.getSize());
        stringList.push_back("A");
        assertEquals(1, stringList.getSize());
        stringList.push_back("B");
        assertEquals(2, stringList.getSize());
    }
    
    @Test
    public void testAt() {
        stringList.push_back("A");
        stringList.push_back("B");
        stringList.push_back("C");
        
        assertEquals("A", stringList.at(0));
        assertEquals("B", stringList.at(1));
        assertEquals("C", stringList.at(2));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAtInvalidIndex() {
        stringList.push_back("A");
        stringList.at(1); // Должно бросить исключение
    }
    
    @Test
    public void testRemove() {
        stringList.push_back("A");
        stringList.push_back("B");
        stringList.push_back("C");
        
        stringList.remove(1); // Удаляем "B"
        
        assertEquals(2, stringList.getSize());
        assertEquals("A", stringList.at(0));
        assertEquals("C", stringList.at(1));
    }
    
    @Test
    public void testContains() {
        stringList.push_back("A");
        stringList.push_back("B");
        
        assertTrue("Должен содержать A", stringList.contains("A"));
        assertTrue("Должен содержать B", stringList.contains("B"));
        assertFalse("Не должен содержать C", stringList.contains("C"));
    }
    
    @Test
    public void testClear() {
        stringList.push_back("A");
        stringList.push_back("B");
        stringList.push_back("C");
        
        assertEquals(3, stringList.getSize());
        
        stringList.clear();
        
        assertTrue("Список должен быть пустым", stringList.isEmpty());
        assertEquals("Размер должен быть 0", 0, stringList.getSize());
    }
    
    @Test
    public void testConvToStr() {
        stringList.push_back("First");
        stringList.push_back("Second");
        
        String[] result = stringList.convToStr();
        
        assertNotNull(result);
        assertEquals(2, result.length);
        assertEquals("First", result[0]);
        assertEquals("Second", result[1]);
    }
}