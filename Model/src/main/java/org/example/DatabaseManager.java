package org.example;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost/sudokuDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "0000";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Połączenie z bazą danych nie powiodło się!", e);
        }
    }
}
