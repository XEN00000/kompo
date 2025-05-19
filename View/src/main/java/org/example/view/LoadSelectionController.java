package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class LoadSelectionController {

    public Button loadFileButton;
    public Button loadSaveButton;
    public Button goBackButton;
    public Text loadFrom;

    @FXML
    public ResourceBundle resources;
    private SceneManager sceneManager;

    public void initialize() {
        setButtonText();
    }

    public void loadFile(ActionEvent event) {
        sceneManager = new SceneManager(event, "/LoadGameScene.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    public void loadSave(ActionEvent event) {
        sceneManager = new SceneManager(event, "/SaveSelection.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    public void cancel(ActionEvent event) {
        sceneManager = new SceneManager(event, "/StartScene.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    private void setButtonText() {
        loadFrom.setText(resources.getString("loadFrom") + ":");
        loadFileButton.setText(resources.getString("file"));
        loadSaveButton.setText(resources.getString("database"));
        goBackButton.setText(resources.getString("goBack"));
    }
}
