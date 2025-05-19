package org.example;

import java.sql.Connection;

public class SudokuBoardDaoFactory {
    public static Dao<SudokuBoard> getFileSudokuBoardDao(String directoryName) {    // metoda tworząca DAO
        return new FileSudokuBoardDao(directoryName);
    }

    public static Dao<SudokuBoard> getDbSaveSudokuBoardDao(Connection connection) {
        return new JdbcSudokuBoardDao(connection);
    }
}
