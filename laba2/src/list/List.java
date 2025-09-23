package list;

/**
 * Реализация односвязного списка
 * @param <T> тип элементов списка
 * @author Vadim Ustinov
 * @version 1.0
 */
public class List<T> implements interfaceList<T>
{
    /**
     * Внутренний класс для представления узла списка
     * @param <T> тип данных узла
     */
    private static class Node<T>
    {
        T val;
        Node<T> next;
        
        /**
         * Конструктор узла
         * @param data значение узла
         */
        Node(T data) 
        {
            this.val = data;
            this.next = null;
        }
    }
    
    private Node<T> head;
    private Node<T> end;
    private int sz;
    
    /**
     * Конструктор по умолчанию
     */
    public List()
    {
        head = null;
        end = null;
        sz=0;
    }
    
    // ... остальные методы без изменений ...
}