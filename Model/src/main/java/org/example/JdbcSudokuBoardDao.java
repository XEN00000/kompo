package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    private final Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);

    public JdbcSudokuBoardDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public SudokuBoard read(String name) throws DaoException {
        String queryBoard = "SELECT id FROM sudoku_boards WHERE name = ?";
        String queryFields = "SELECT row_index, col_index, value FROM sudoku_fields WHERE board_id = ?";

        try (PreparedStatement boardStmt = connection.prepareStatement(queryBoard)) {
            boardStmt.setString(1, name);
            ResultSet rs = boardStmt.executeQuery();
            if (!rs.next()) {
                throw new DaoException("Board not found");
            }
            int boardId = rs.getInt("id");

            SudokuBoard board = new SudokuBoard();
            try (PreparedStatement fieldStmt = connection.prepareStatement(queryFields)) {
                fieldStmt.setInt(1, boardId);
                ResultSet fieldRs = fieldStmt.executeQuery();
                while (fieldRs.next()) {
                    int row = fieldRs.getInt("row_index");
                    int col = fieldRs.getInt("col_index");
                    int value = fieldRs.getInt("value");
                    board.set(row, col, value);
                }
            }
            return board;
        } catch (SQLException e) {
            throw new DaoException("Error reading from database", e);
        }
    }

    @Override
    public void write(String name, SudokuBoard board) throws DaoException {
        String insertBoard = "INSERT INTO sudoku_boards (name) VALUES (?) RETURNING id";
        String insertField = "INSERT INTO sudoku_fields (board_id, row_index, col_index, value) VALUES (?, ?, ?, ?)";
        logger.info("Inserts created");
        try (PreparedStatement boardStmt = connection.prepareStatement(insertBoard)) {
            boardStmt.setString(1, name);
            ResultSet rs = boardStmt.executeQuery();
            if (!rs.next()) {
                rs.next();
            }
            int boardId = rs.getInt("id");

            logger.info("Statements prepared");

            try (PreparedStatement fieldStmt = connection.prepareStatement(insertField)) {
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        fieldStmt.setInt(1, boardId);
                        fieldStmt.setInt(2, row);
                        fieldStmt.setInt(3, col);
                        fieldStmt.setInt(4, board.get(row, col));
                        fieldStmt.addBatch();
                    }
                }
                fieldStmt.executeBatch();
                logger.info("Fields added");
            }
        } catch (SQLException e) {
            throw new DaoException("Error writing to database", e);
        }
    }

    @Override
    public List<String> names() throws DaoException {
        String query = "SELECT name FROM sudoku_boards";
        List<String> boardNames = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                boardNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new DaoException("Error retrieving board names from database", e);
        }

        return boardNames;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
