package org.example;

import java.util.ResourceBundle;

public class SudokuValidationException extends SudokuApplicationException {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages");

    public SudokuValidationException() {
        super(bundle.getString("sudoku.validation.error"));
    }

    public SudokuValidationException(Throwable cause) {
        super(bundle.getString("sudoku.validation.error"), cause);
    }
}
