package ScreenForms;

public class temp {
    public static void main(String[] args) {
    	// Создание окна с текстом, заголовком и количеством полей
    	IOTextWindow inputWindow = new IOTextWindow(
    	    "Введите данные о собаке:",  // текст сообщения
    	    "Добавление собаки",         // заголовок окна  
    	    3                           // количество полей ввода
    	);

    	// Показать окно
    	inputWindow.show();

    	// Получить введенные данные
    	String[] results = inputWindow.getData();
    	if (results != null) {
    	    // Обработка данных
    	    String name = results[0];
    	    String breed = results[1];
    	    String awards = results[2];
    	}
    	for (int i = 0; i < results.length; i++) {
            System.out.println(results[i]);
        }
    }
}