package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.FileSudokuBoardDao;
import org.example.SudokuBoard;

import java.io.File;
import java.util.ResourceBundle;

public class LoadGameController {

    @FXML
    private TextField filePathField;

    @FXML
    private Text loadSudokuGame;

    @FXML
    private Button browseButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button goBackButton;

    @FXML
    public ResourceBundle resources;
    private SceneManager sceneManager;
    private SudokuBoard loadedBoard;

    public void initialize() {
        setButtonText();
    }

    public void browseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Sudoku File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sudoku Files", "*.sudoku"));

        File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null) {
            filePathField.setText(file.getAbsolutePath());
        }
    }

    public void loadGame(ActionEvent event) {
        String filePath = filePathField.getText();
        if (filePath != null && !filePath.isEmpty()) {
            //TODO: Skorzystać z interfejsu DAO
            try (FileSudokuBoardDao dao = new FileSudokuBoardDao(new File(filePath).getParent())) {
                loadedBoard = dao.read(new File(filePath).getName());

                sceneManager = new SceneManager(event, "/SudokuBoard.fxml", "/SceneStyle.css", resources);
                sceneManager.loadScene(loadedBoard);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cancel(ActionEvent event) {
        sceneManager = new SceneManager(event, "/LoadSelection.fxml", "/SceneStyle.css", resources);
        sceneManager.loadScene();
    }

    private void setButtonText() {
        loadButton.setText(resources.getString("load"));
        goBackButton.setText(resources.getString("goBack"));
        loadSudokuGame.setText(resources.getString("loadSudokuGame"));
        browseButton.setText(resources.getString("browseFile"));
        filePathField.setPromptText(resources.getString("noFileSelected"));
    }
}
