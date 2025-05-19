package org.example;

public class SudokuInvalidFieldException extends SudokuApplicationException {

    public SudokuInvalidFieldException() {
        super("SudokuInvalidFieldException");
    }

    public SudokuInvalidFieldException(Throwable cause) {
        super("SudokuInvalidFieldException", cause);
    }
}
