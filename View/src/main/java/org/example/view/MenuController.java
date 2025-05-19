package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class MenuController {
    public Button newGameButton;
    public Button exitButton;
    public Button settingsButton;
    public Button loadGameButton;

    public Text gameTitle;

    @FXML
    public ResourceBundle resources;
    private SceneManager sceneManager;

    public void initialize() {
        setButtonText();
    }

    public void newGame(ActionEvent event) {
        sceneManager = new SceneManager(event, "/DifficultySelection.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    public void loadGame(ActionEvent event) {
        sceneManager = new SceneManager(event, "/LoadSelection.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    public void settings(ActionEvent event) {
        sceneManager = new SceneManager(event, "/Settings.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    public void exit(ActionEvent event) {
        System.exit(0);
    }

    private void setButtonText() {
        newGameButton.setText(resources.getString("newGame"));
        loadGameButton.setText(resources.getString("loadGame"));
        settingsButton.setText(resources.getString("settings"));
        exitButton.setText(resources.getString("exit"));
        gameTitle.setText(resources.getString("gameTitle"));
    }
}
