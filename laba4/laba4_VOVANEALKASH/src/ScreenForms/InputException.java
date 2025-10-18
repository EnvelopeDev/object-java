package ScreenForms;

import javax.swing.JTextField;

/**
 * Универсальное исключение для обработки ошибок ввода в приложении
 * с методами валидации
 * @author Vadim Ustinov
 * @version 1.1
 */
public class InputException extends Exception {
    private final ErrorType errorType;
    
    public enum ErrorType {
        EMPTY_FIELD,
        INVALID_NUMBER,
        OUT_OF_RANGE,
        INVALID_FORMAT
    }
    
    public InputException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }
    
    public ErrorType getErrorType() {
        return errorType;
    }
    
    /**
     * Validates that all required fields are filled
     * @param textFields array of text fields to validate
     * @throws InputException if any required field is empty
     */
    public static void validEmptyField(JTextField[] textFields) throws InputException {
        for (int i = 0; i < textFields.length; i++) {
            if (textFields[i].getText().trim().isEmpty()) {
                throw new InputException(
                    "Field " + (i + 1) + " cannot be empty", 
                    ErrorType.EMPTY_FIELD
                );
            }
        }
    }
    
    /**
     * Validates row number input
     * @param rowNumberStr the row number string to validate
     * @param maxRows maximum allowed row number
     * @throws InputException if row number is invalid or not a number
     */
    public static void validRowNumber(String rowNumberStr, int maxRows) throws InputException {
        if (rowNumberStr.trim().isEmpty()) {
            throw new InputException(
                "Row number cannot be empty",
                ErrorType.EMPTY_FIELD
            );
        }
        
        try {
            int rowNumber = Integer.parseInt(rowNumberStr);
            if (rowNumber < 1 || rowNumber > maxRows) {
                throw new InputException(
                    "Invalid row number: " + rowNumber + ". Valid range: 1-" + maxRows,
                    ErrorType.OUT_OF_RANGE
                );
            }
        } catch (NumberFormatException e) {
            throw new InputException(
                "Row number must be a number",
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
    public static void validInputField(String text, String fieldName) throws InputException {
        if (text.trim().isEmpty()) {
            throw new InputException(
                fieldName + " cannot be empty",
                ErrorType.EMPTY_FIELD
            );
        }
        
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new InputException(
                fieldName + " must be a number",
                ErrorType.INVALID_NUMBER
            );
        }
    }
 
    /**
     * Validates data array for editing
     * @param data the data array to validate
     * @param expectedLength expected length of the array
     * @throws InputException if data array is invalid
     */
    public static void validDataArray(String[] data, int expectedLength) throws InputException {
        if (data == null) {
            throw new InputException(
                "Data cannot be null",
                ErrorType.INVALID_FORMAT
            );
        }
        
        if (data.length < expectedLength) {
            throw new InputException(
                "Invalid data format. Expected " + expectedLength + " fields, got " + data.length,
                ErrorType.INVALID_FORMAT
            );
        }
    }
}