package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.*;

import java.io.File;
import java.sql.Connection;
import java.util.ResourceBundle;

public class SudokuBoardController {

    @FXML
    public ResourceBundle resources;

    public GridPane sudokuGrid;
    public Button checkGameButton;
    public Button resetGameButton;
    public Button saveGameButton;
    public Button backToMenuButton;
    public Text gameTitle;

    private SudokuBoard board;
    private Difficulty difficulty;
    private boolean isLoaded = false;

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
        this.board.setSolver();
        if (sudokuGrid != null) {
            fillGrid();
        }
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    @FXML
    public void initialize() {
        setButtonText();
        if (difficulty == null) {
            difficulty = Difficulty.EASY;
        }

        if (isLoaded) {
            fillGrid();
            setIsLoaded(false);
        } else {
            // Inicjalizacja planszy Sudoku z modelu
            board = new SudokuBoard();
            board.solveGame();

            board.hideFields(difficulty.getFieldsToRemove());

            fillGrid();
        }
    }

    private void fillGrid() {
        sudokuGrid.getChildren().clear(); // Usuń istniejące elementy w siatce
        sudokuGrid.getStyleClass().add("sudoku-grid");

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cell = new TextField();
                int value = board.get(row, col);

                // Jeśli pole jest zakryte (wartość 0), zostaw je puste
                cell.setText(value == 0 ? "" : String.valueOf(value));
                cell.setEditable(value == 0); // Pola zakryte są edytowalne
                cell.setPrefSize(50, 50);
                cell.setStyle("-fx-alignment: center; -fx-font-size: 16px;");
                cell.getStyleClass().add("cell");
                if (row % 3 == 0 && row != 0 && col % 3 == 0 && col != 0) {
                    cell.getStyleClass().add("thick-border-both");
                } else if (col % 3 == 0 && col != 0) {
                    cell.getStyleClass().add("thick-border-column");
                } else if (row % 3 == 0 && row != 0) {
                    cell.getStyleClass().add("thick-border-row");
                }


                // Dodaj listener do walidacji wpisów
                if (value == 0) {
                    int finalRow = row;
                    int finalCol = col;
                    cell.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("[1-9]?")) {
                            cell.setStyle("-fx-background-color: lightcoral; "
                                    + "-fx-alignment: center; "
                                    + "-fx-font-size: 16px;");
                            cell.setText(oldValue); // Przywróć poprzednią wartość
                        } else if (!newValue.isEmpty()) {
                            // Zaktualizuj model, jeśli wpis jest poprawny
                            cell.setStyle("fx-fill: white; -fx-alignment: center; -fx-font-size: 16px;");
                            int newVal = Integer.parseInt(newValue);
                            board.set(finalRow, finalCol, newVal);
                        } else {
                            // Usuń wartość z modelu, jeśli pole jest puste
                            board.set(finalRow, finalCol, newValue.isEmpty() ? 0 : Integer.parseInt(newValue));
                        }
                    });
                }

                sudokuGrid.add(cell, col, row);
            }
        }
    }

    // Metoda do sprawdzania poprawności planszy
    public void checkBoard() {
        board.solveGame();
        fillGrid();
    }

    public void saveGame() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Sudoku Board");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));
        File file = fileChooser.showSaveDialog(sudokuGrid.getScene().getWindow());

        if (file != null) {
            try {
                // Zapis do pliku
                Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getFileSudokuBoardDao(file.getParent());
                dao.write(file.getName(), board);

                // Zapis do bazy danych
                try (Connection connection = DatabaseManager.connect()) {
                    connection.setAutoCommit(false);
                    try {
                        Dao<SudokuBoard> dbDao = SudokuBoardDaoFactory.getDbSaveSudokuBoardDao(connection);
                        dbDao.write(file.getName(), board);
                        connection.commit(); // Zatwierdzenie transakcji
                    } catch (Exception e) {
                        connection.rollback(); // Wycofanie w przypadku błędu
                        throw e;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error saving Sudoku Board", e);
            }
        }
    }

    public void resetBoard() {
        initialize();
    }

    public void backToMenu(ActionEvent event) {
        SceneManager sceneManager = new SceneManager(event, "/StartScene.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    private void setButtonText() {
        checkGameButton.setText(resources.getString("checkGame"));
        resetGameButton.setText(resources.getString("resetGame"));
        saveGameButton.setText(resources.getString("saveGame"));
        backToMenuButton.setText(resources.getString("goBack"));
        gameTitle.setText(resources.getString("gameTitle"));
    }
}
