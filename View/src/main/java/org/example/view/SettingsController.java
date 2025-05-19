package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsController {

    @FXML
    private Button backToMenuButton;

    @FXML
    private Button applyButton;

    @FXML
    private ComboBox<String> languageComboBox;

    @FXML
    private VBox authorsBox;

    @FXML
    public ResourceBundle resources;
    private SceneManager sceneManager;

    public void setBundle(ResourceBundle bundle) {
        this.resources = bundle;
    }

    @FXML
    public void initialize() {
        // Dodanie opcji języków do ComboBox
        languageComboBox.getItems().addAll("en", "pl", "es", "fr");
        // Ustawienie domyślnego języka
        languageComboBox.setValue(resources.getString("language"));
        // Obsługa zmiany języka
        setAuthors();
        setButtonText();
    }


    private void setAuthors() {
        // Tworzenie elementów tekstowych
        Text authorsLabel = new Text(resources.getString("authors") + ":");
        Text authorsNames = new Text(resources.getString("authors-names"));

        // Dodawanie stylów do elementów
        authorsLabel.getStyleClass().add("authors");
        authorsNames.getStyleClass().add("authors");

        // Dodawanie do kontenera
        authorsBox.getChildren().add(authorsLabel);
        authorsBox.getChildren().add(authorsNames);
    }

    private void setButtonText() {
        backToMenuButton.setText(resources.getString("goBack"));
        applyButton.setText(resources.getString("apply"));
    }

    public void apply(ActionEvent event) {
        setBundle(ResourceBundle.getBundle("Bundle", new Locale(languageComboBox.getValue())));
        sceneManager = new SceneManager(event, "/Settings.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    public void backToMenu(ActionEvent event) {
        sceneManager = new SceneManager(event, "/StartScene.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }
}
