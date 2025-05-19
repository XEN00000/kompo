package org.example;

public class SudokuFileReadException extends SudokuApplicationException {

    public SudokuFileReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public SudokuFileReadException(String message) {
        super(message);
    }
}
