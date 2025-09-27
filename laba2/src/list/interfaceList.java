package list;

/**
 * Package for working with a list of items
 * @param <T> type of list items
 * @author Gushchin Kirill
 * @version 1.0
 */
public interface interfaceList<T> 
{
    /**
     * Function adds an item to the end of the list
     * @param value the value to add
     */
    void push_back(T value);
    
    /**
     * Function adds an item to the top of the list
     * @param value the value to add
     */
    void push_front(T value);
    
    /**
     * Function inserts the element at the specified position
     * @param value the value to add
     * @param index position to insert
     */
    void insert(T value, int index);
    
    /**
     * Function returns the size of the list
     * @return number of items in the list
     */
    int getSize();
    
    /**
     * Function replaces the element at the specified position
     * @param value new value
     * @param index position to replace
     */
    void replace(T value, int index);
    
    /**
     * Function returns an element at the specified index
     * @param index position of the element
     * @return element at the specified position
     */
    T at(int index);
    
    /**
     * Function deletes an element by the specified index
     * @param index position of the item to delete
     */
    void remove(int index);
    
    /**
     * Function checks if the list is empty
     * @return true if the list is empty, otherwise false
     */
    boolean isEmpty();
    
    /**
     * Function checks whether the list contains the specified element.
     * @param value for the search
     * @return true if the element is found, otherwise false
     */
    boolean contains(T value);
    
    /**
     * Function clears the list
     */
    void clear();
    
    /**
     * Function converts a list into an array of strings
     * @return array of string representations of elements
     */
    String[] convToStr();
}