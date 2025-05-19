package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BacktrackingSudokuSolverTest {

    @Test
    public void testSudokuBoardNullException() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> solver.solve(null));
        assertEquals("SudokuBoard cannot be null", exception.getMessage());
    }

    @Test
    public void testFilledSudokuBoard() {
        SudokuBoard board = new SudokuBoard();
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        board.set(5, 5, 0);
        solver.solve(board);
    }
}