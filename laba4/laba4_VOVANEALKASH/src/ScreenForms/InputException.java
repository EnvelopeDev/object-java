package ScreenForms;

import javax.swing.JTextField;

/**
 * Универсальное исключение для обработки ошибок ввода в приложении
 * с методами валидации
 * @author Vadim Ustinov
 * @version 1.0
 */
public class InputException extends Exception {
    private final String fieldName;
    private final ErrorType errorType;
    
    public enum ErrorType {
        EMPTY_FIELD,
        INVALID_NUMBER,
        OUT_OF_RANGE,
        INVALID_FORMAT
    }
    
    public InputException(String message, String fieldName, ErrorType errorType) {
        super(message);
        this.fieldName = fieldName;
        this.errorType = errorType;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public ErrorType getErrorType() {
        return errorType;
    }
    
    /**
     * Validates that all required fields are filled
     * @param textFields array of text fields to validate
     * @throws InputException if any required field is empty
     */
    public static void validateRequiredFields(JTextField[] textFields) throws InputException {
        for (int i = 0; i < textFields.length; i++) {
            if (textFields[i].getText().trim().isEmpty()) {
                throw new InputException(
                    "Поле " + (i + 1) + " не может быть пустым", 
                    "Поле " + (i + 1), 
                    ErrorType.EMPTY_FIELD
                );
            }
        }
    }
    
    /**
     * Validates that a specific field is filled
     * @param textField the text field to validate
     * @param fieldName the name of the field for error message
     * @throws InputException if the field is empty
     */
    public static void validateFieldNotEmpty(JTextField textField, String fieldName) throws InputException {
        if (textField.getText().trim().isEmpty()) {
            throw new InputException(
                fieldName + " не может быть пустым",
                fieldName,
                ErrorType.EMPTY_FIELD
            );
        }
    }
    
    /**
     * Validates row number input
     * @param rowNumberStr the row number string to validate
     * @param maxRows maximum allowed row number
     * @throws InputException if row number is invalid or not a number
     */
    public static void validateRowNumber(String rowNumberStr, int maxRows) throws InputException {
        if (rowNumberStr.trim().isEmpty()) {
            throw new InputException(
                "Номер строки не может быть пустым",
                "Номер строки",
                ErrorType.EMPTY_FIELD
            );
        }
        
        try {
            int rowNumber = Integer.parseInt(rowNumberStr);
            if (rowNumber < 1 || rowNumber > maxRows) {
                throw new InputException(
                    "Некорректный номер строки: " + rowNumber + ". Допустимый диапазон: 1-" + maxRows,
                    "Номер строки",
                    ErrorType.OUT_OF_RANGE
                );
            }
        } catch (NumberFormatException e) {
            throw new InputException(
                "Номер строки должен быть числом",
                "Номер строки", 
                ErrorType.INVALID_NUMBER
            );
        }
    }
    
    /**
     * Validates that text contains only numbers
     * @param text the text to validate
     * @param fieldName the name of the field for error message
     * @throws InputException if text is not a number
     */
    public static void validateNumber(String text, String fieldName) throws InputException {
        if (text.trim().isEmpty()) {
            throw new InputException(
                fieldName + " не может быть пустым",
                fieldName,
                ErrorType.EMPTY_FIELD
            );
        }
        
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new InputException(
                fieldName + " должен быть числом",
                fieldName, 
                ErrorType.INVALID_NUMBER
            );
        }
    }
    
    /**
     * Validates number range
     * @param number the number to validate
     * @param fieldName the name of the field for error message
     * @param min minimum allowed value
     * @param max maximum allowed value
     * @throws InputException if number is out of range
     */
    public static void validateNumberRange(int number, String fieldName, int min, int max) throws InputException {
        if (number < min || number > max) {
            throw new InputException(
                fieldName + " должен быть в диапазоне " + min + "-" + max,
                fieldName,
                ErrorType.OUT_OF_RANGE
            );
        }
    }
}