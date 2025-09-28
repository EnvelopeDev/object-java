package ScreenForms;

public class temp {
    public static void main(String[] args) {
        IOTextW ioDialog = new IOTextW();

        // Тестируем
        String[] result = ioDialog.use("Введите данные собаки:", "hui",  4);
        
        if (result != null) {
            System.out.println("=== РЕЗУЛЬТАТ ===");
            for (int i = 0; i < result.length; i++) {
                System.out.println("Поле " + (i + 1) + ": '" + result[i] + "'");
            }
        } else {
            System.out.println("Ввод отменен");
        }
    }
}