package fileManager;
import list.interfaceList;

import java.io.IOException;

import list.List;
import object.dog.*;

/**
 * Интерфейс для управления файловыми операциями с данными о собаках
 * Определяет методы для чтения и записи данных в CSV формате
 * @author Vadim Ustinov
 * @version 1.0
 */
public interface interfaceFM 
{
    /**
     * Читает данные о собаках из CSV файла
     * @param filePath путь к CSV файлу
     * @return список объектов Dog
     * @throws IOException если произошла ошибка ввода-вывода
     */
    List<Dog> inputFromCSV(String filePath) throws IOException;
    
    /**
     * Записывает данные о собаках в CSV файл
     * @param filePath путь к CSV файлу
     * @param dogs список объектов Dog для сохранения
     * @throws IOException если произошла ошибка ввода-вывода
     */
    void outputToCSV(String filePath, List<Dog> dogs) throws IOException;
}