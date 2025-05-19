package org.example;

public class SudokuApplicationException extends Exception {
    public SudokuApplicationException(String message) {
        super(message);
    }

    public SudokuApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
