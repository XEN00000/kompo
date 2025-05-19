package org.example;

import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {

    @Test
    public void testSudokuBoardConstructor() {
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertNotEquals(0, board.get(row, col));
            }
        }
    }

    @Test
    public void testToString() {
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        String string = board.toString();
        assertTrue(string.contains("SudokuBoard[board="));
    }

    @Test
    public void testGetSudokuBoard() {
        SudokuBoard board = new SudokuBoard();
        board.solveGame();
        SudokuField[][] fields = board.getBoard();

        assertNotNull(fields);
        assertEquals(9, fields.length);
        for (SudokuField[] row : fields) {
            assertEquals(9, row.length);
        }
    }

    @Test
    public void testEqualsMethod() {
        SudokuBoard board = new SudokuBoard();
        board.solveGame();

        assertNotEquals(board, null, "Compairing with board with null");

        String otherObject = "other";
        assertNotEquals(board, otherObject, "Objects have different types");

        assertEquals(board, board, "An object should be equal to itself");

        SudokuBoard board2 = new SudokuBoard();
        assertNotEquals(board, board2, "An board should not be equal to other board");
    }

    @Test
    public void testCloneCreatesEqualButDistinctObject() {
        SudokuBoard original = new SudokuBoard();
        original.solveGame();

        original.set(0, 0, 5);

        SudokuBoard cloned = original.clone();

        assertEquals(original, cloned, "Cloned object should be equal to the original");
        assertNotSame(original, cloned, "Cloned object should not be the same instance as the original");
    }

    @Test
    public void testIsCloneDeepCopy() {
        SudokuBoard original = new SudokuBoard();
        original.solveGame();

        original.set(0, 0, 5);

        SudokuBoard cloned = original.clone();

        cloned.set(0, 0, 3);

        assertEquals(5, original.get(0, 0));
        assertEquals(3, cloned.get(0, 0));
    }

    @Test
    public void testCloneNewSudokuFields() {
        SudokuBoard original = new SudokuBoard();
        original.solveGame();

        SudokuBoard cloned = original.clone();
        cloned.solveGame();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertNotSame(original.getBoard()[i][j], cloned.getBoard()[i][j], "Each SudokuField in the clone should be a new instance");
            }
        }
    }
}