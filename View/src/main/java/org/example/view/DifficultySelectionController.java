package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class DifficultySelectionController {

    @FXML
    public Button selectEasyButton;

    @FXML
    public Button selectMediumButton;

    @FXML
    public Button selectHardButton;

    @FXML
    public Button goBackButton;

    @FXML
    public Text selectDifficulty;

    @FXML
    public ResourceBundle resources;
    private SceneManager sceneManager;

    public void selectEasy(ActionEvent event) {
        startGame(Difficulty.EASY, event);
    }

    public void selectMedium(ActionEvent event) {
        startGame(Difficulty.MEDIUM, event);
    }

    public void selectHard(ActionEvent event) {
        startGame(Difficulty.HARD, event);
    }

    public void initialize() {
        setButtonText();
    }

    public void cancel(ActionEvent event) {
        sceneManager = new SceneManager(event, "/StartScene.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    @FXML
    private void startGame(Difficulty difficulty, ActionEvent event) {

        sceneManager = new SceneManager(event, "/SudokuBoard.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene(difficulty);
    }

    private void setButtonText() {
        selectEasyButton.setText(resources.getString("easy"));
        selectMediumButton.setText(resources.getString("medium"));
        selectHardButton.setText(resources.getString("hard"));
        goBackButton.setText(resources.getString("goBack"));
        selectDifficulty.setText(resources.getString("selectDifficulty"));
    }
}
