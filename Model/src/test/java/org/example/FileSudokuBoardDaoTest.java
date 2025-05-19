package org.example;

import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    private static final String TEST_DIRECTORY = "test_sudoku_boards";
    private static final String TEST_FILE_NAME = "testBoard.sudoku";

    private FileSudokuBoardDao dao;

    @BeforeEach
    void setUp() {
        dao = new FileSudokuBoardDao(TEST_DIRECTORY);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up test files and directory
        Path directoryPath = Path.of(TEST_DIRECTORY);
        if (Files.exists(directoryPath)) {
            Files.walk(directoryPath)
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        Files.deleteIfExists(directoryPath);
    }

    @Test
    void testWriteAndRead() throws SudokuFileReadException {
        SudokuBoard board = new SudokuBoard();
        board.set(0, 0, 5);
        board.set(1, 1, 3);

        System.out.println("Original board:");
        board.printBoard();

        try {
            dao.write(TEST_FILE_NAME, board);
        } catch (SudokuFileWriteException e) {
            throw new RuntimeException(e);
        }


        SudokuBoard readBoard = dao.read(TEST_FILE_NAME);
        assertNotNull(readBoard, "Read board should not be null");

        System.out.println("Read board:");
        readBoard.printBoard();

        assertEquals(5, readBoard.get(0, 0), "The value at (0, 0) should match");
        assertEquals(3, readBoard.get(1, 1), "The value at (1, 1) should match");
        assertNotEquals(7, readBoard.get(0, 0), "The value at (0, 0) should not match unrelated data");
    }

    @Test
    void testNames() {
        //TODO: zamienić to na asercje
        try {
            dao.write("board1.sudoku", new SudokuBoard());
        } catch (SudokuFileWriteException e) {
            throw new RuntimeException(e);
        }
        try {
            dao.write("board2.sudoku", new SudokuBoard());
        } catch (SudokuFileWriteException e) {
            throw new RuntimeException(e);
        }

        List<String> names = dao.names();
        assertEquals(2, names.size(), "There should be 2 files listed");
        assertTrue(names.contains("board1.sudoku"), "List should contain 'board1.sudoku'");
        assertTrue(names.contains("board2.sudoku"), "List should contain 'board2.sudoku'");
    }

    //@Test
    //void testReadNonExistentFile() {
    //    Exception exception = assertThrows(SudokuFileReadException.class, () -> dao.read("nonExistentFile.sudoku"));
    //    assertTrue(exception.getMessage().contains("Error reading from file"), "Exception message should indicate failure to read file");
    //}

    @Test
    void testWriteAndReadEquality() throws SudokuFileReadException {
        SudokuBoard board = new SudokuBoard();
        board.set(0, 0, 8);
        board.set(4, 4, 6);
        board.set(8, 8, 1);

        try {
            dao.write(TEST_FILE_NAME, board);
        } catch (SudokuFileWriteException e) {
            throw new RuntimeException(e);
        }

        SudokuBoard readBoard = dao.read(TEST_FILE_NAME);
        assertNotNull(readBoard, "Read board should not be null");
        assertEquals(board, readBoard, "The original and read boards should be equal");
    }
}
