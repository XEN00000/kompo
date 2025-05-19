package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {

    @Test
    void testGetFileSudokuBoardDaoCreatesValidDao() {
        // Arrange
        String validDirectory = "test_sudoku_boards";

        // Act
        Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileSudokuBoardDao(validDirectory);

        // Assert
        assertNotNull(dao, "Factory should create a non-null org.sudoku.Dao instance.");
        assertTrue(dao instanceof FileSudokuBoardDao, "Factory should create an instance of org.sudoku.FileSudokuBoardDao.");

        new File(validDirectory).delete();
    }

    @Test
    void testFileSudokuBoardDaoWorksCorrectly() {
        // Arrange
        String validDirectory = "test_sudoku_boards";
        Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileSudokuBoardDao(validDirectory);
        SudokuBoard boardToSave = new SudokuBoard();
        String fileName = "testBoard.ser";

        // Act
        try {
            dao.write(fileName, boardToSave);  // Save board
        } catch (SudokuFileWriteException e) {
            throw new RuntimeException(e);
        }
        SudokuBoard loadedBoard;  // Load board
        try {
            loadedBoard = dao.read(fileName);
        } catch (SudokuFileReadException e) {
            throw new RuntimeException(e);
        }

        // Assert
        assertNotNull(loadedBoard, "Loaded board should not be null.");
        assertEquals(boardToSave, loadedBoard, "Saved and loaded boards should be equal.");

        // Cleanup
        new File(validDirectory, fileName).delete();
        new File(validDirectory).delete();
    }
}
