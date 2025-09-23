package list;

/**
 * Интерфейс для работы со списком элементов
 * Определяет основные операции для работы с коллекцией данных
 * @param <T> тип элементов списка
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface interfaceList<T> 
{
    /**
     * Добавляет элемент в конец списка
     * @param value значение для добавления
     */
    void push_back(T value);
    
    /**
     * Добавляет элемент в начало списка
     * @param value значение для добавления
     */
    void push_front(T value);
    
    /**
     * Вставляет элемент на указанную позицию
     * @param value значение для вставки
     * @param index позиция для вставки
     */
    void insert(T value, int index);
    
    /**
     * Возвращает размер списка
     * @return количество элементов в списке
     */
    int getSize();
    
    /**
     * Заменяет элемент на указанной позиции
     * @param value новое значение
     * @param index позиция для замены
     */
    void replace(T value, int index);
    
    /**
     * Возвращает элемент по указанному индексу
     * @param index позиция элемента
     * @return элемент на указанной позиции
     */
    T at(int index);
    
    /**
     * Удаляет элемент по указанному индексу
     * @param index позиция элемента для удаления
     */
    void remove(int index);
    
    /**
     * Проверяет, пуст ли список
     * @return true если список пуст, иначе false
     */
    boolean isEmpty();
    
    /**
     * Проверяет, содержит ли список указанный элемент
     * @param value значение для поиска
     * @return true если элемент найден, иначе false
     */
    boolean contains(T value);
    
    /**
     * Очищает список
     */
    void clear();
    
    /**
     * Выводит список в консоль (для отладки)
     */
    void print();
    
    /**
     * Преобразует список в массив строк
     * @return массив строковых представлений элементов
     */
    String[] convToStr();
}