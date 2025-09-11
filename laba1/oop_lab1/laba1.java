package oop_lab1;
/**
 * Лабораторная работа №1
 * Тема: Освоение среды разработки Eclipse, программирование, запуск и отладка конслольного приложения
 * @author Kirill Gushchin
 * @version 1.0
 */
public class laba1 {
	/**
	 * Метод для вывода массива int
	 * @param arr массив целых чисел для вывода
	 */
	public static void printArr(int[] arr) {
		for(int i=0;i<arr.length;i++) {
			System.out.print(arr[i]);
			System.out.print(' ');
		}
		System.out.print('\n');
	}
	
	/**
	 * Главный метод программы: инициализируется массив int, сортируется пузырьком
	 * @param args аргументы командной строки
	 */
	public static void main(String[] args) {
		int arr[] = {5, 1, 3, 2, 10, 56, 5, 10, 1, -4};
		boolean flag = true;
		int temp;
		System.out.print("Исходный массив: ");
		printArr(arr);
		while(flag) {
			flag=false;
			for(int i=0;i<arr.length-1;i++) {
				if(arr[i] > arr[i+1]) {
					temp = arr[i];
					arr[i] = arr[i+1];
					arr[i+1] = temp;
					flag=true;
				}
			}
		}
		System.out.print("Отсортированный массив (по возрастанию):");
		printArr(arr);
	}
}
