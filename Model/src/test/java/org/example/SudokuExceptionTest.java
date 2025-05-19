package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SudokuExceptionTest {
    @Test
    void testSudokuInvalidFieldException() {
        SudokuBoard board = new SudokuBoard();
        assertThrows(SudokuInvalidFieldException.class, () -> board.validateField(0));
    }
}
