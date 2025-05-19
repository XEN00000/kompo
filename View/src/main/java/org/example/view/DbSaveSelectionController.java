package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.*;

import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class DbSaveSelectionController {

    public Button goBackButton;
    public Text selectSave;
    public VBox placeForButtons;

    @FXML
    public ResourceBundle resources;
    private SceneManager sceneManager;

    public void initialize() {
        setButtonText();
        buttonFill();
    }

    private void buttonFill() {
        try (Connection connection = DatabaseManager.connect();
             JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDbSaveSudokuBoardDao(connection)) {
            List<String> boardNames = dao.names();
            for (String boardName : boardNames) {
                Button button = new Button();
                button.setText(boardName);
                button.getStyleClass().add("menu-button");
                button.setOnAction((ActionEvent event) -> {
                    loadGame(event, boardName);
                });
                placeForButtons.getChildren().add(button);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame(ActionEvent event, String boardName) {
        try (Connection connection = DatabaseManager.connect();
             JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) SudokuBoardDaoFactory.getDbSaveSudokuBoardDao(connection)) {

            SudokuBoard board = dao.read(boardName);

            sceneManager = new SceneManager(event, "/SudokuBoard.fxml", "/SceneStyle.css", resources);
            sceneManager.loadScene(board);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(ActionEvent event) {
        sceneManager = new SceneManager(event, "/LoadSelection.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    private void setButtonText() {
        goBackButton.setText(resources.getString("goBack"));
        selectSave.setText(resources.getString("loadSudokuGame"));
    }
}
