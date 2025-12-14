package object.dog;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * JUnit тесты для класса Dog
 */
public class DogTest {
    
    private Dog dog;
    
    @Before
    public void setUp() {
        // Создаем тестовую собаку перед каждым тестом
        dog = new Dog("Rex", "German Shepherd", true);
    }
    
    @Test
    public void testDogConstructor() {
        // Проверяем корректность создания объекта
        assertNotNull("Собака должна быть создана", dog);
    }
    
    @Test
    public void testGetName() {
        // Проверяем получение имени
        assertEquals("Имя должно быть 'Rex'", "Rex", dog.getName());
    }
    
    @Test
    public void testGetBreed() {
        // Проверяем получение породы
        assertEquals("Порода должна быть 'German Shepherd'", 
                     "German Shepherd", dog.getBreed());
    }
    
    @Test
    public void testHasAward() {
        // Проверяем наличие наград
        assertTrue("Собака должна иметь награды", dog.hasAward());
    }
    
    @Test
    public void testSetName() {
        // Проверяем установку нового имени
        dog.setName("Max");
        assertEquals("Имя должно измениться на 'Max'", "Max", dog.getName());
    }
    
    @Test
    public void testSetBreed() {
        // Проверяем установку новой породы
        dog.setBreed("Labrador");
        assertEquals("Порода должна измениться на 'Labrador'", 
                     "Labrador", dog.getBreed());
    }
    
    @Test
    public void testSetAwards() {
        // Проверяем установку статуса наград
        dog.setAwards(false);
        assertFalse("Собака не должна иметь наград", dog.hasAward());
        
        dog.setAwards(true);
        assertTrue("Собака должна иметь награды", dog.hasAward());
    }
    
    @Test
    public void testToString() {
        // Проверяем строковое представление
        String expected = "Rex;German Shepherd;1";
        assertEquals("Строковое представление не совпадает", 
                     expected, dog.toString());
        
        // Проверяем для собаки без наград
        Dog dogWithoutAward = new Dog("Bella", "Golden Retriever", false);
        expected = "Bella;Golden Retriever;0";
        assertEquals("Строковое представление не совпадает для собаки без наград", 
                     expected, dogWithoutAward.toString());
    }
    
    @Test
    public void testDogWithoutAwards() {
        // Тестируем собаку без наград
        Dog noAwardDog = new Dog("Charlie", "Beagle", false);
        assertFalse("Собака не должна иметь наград", noAwardDog.hasAward());
        assertEquals("Charlie;Beagle;0", noAwardDog.toString());
    }
}