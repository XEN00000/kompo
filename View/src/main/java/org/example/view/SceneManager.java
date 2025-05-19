package org.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.SudokuBoard;

import java.io.IOException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SceneManager {

    private String nextScene;
    private String cssStyle;
    private ResourceBundle resourceBundle;
    private ActionEvent actionEvent;

    public SceneManager(ActionEvent actionEvent, String nextScene, String cssStyle,
                        ResourceBundle resourceBundle) {
        setActionEvent(actionEvent);
        setNextScene(nextScene);
        setResourceBundle(resourceBundle);
        setCssStyle(cssStyle);
    }

    public void setNextScene(String ns) {
        this.nextScene = ns;
    }

    public void setResourceBundle(ResourceBundle r) {
        this.resourceBundle = r;
    }

    public void setActionEvent(ActionEvent e) {
        this.actionEvent = e;
    }

    public void setCssStyle(String cssS) {
        this.cssStyle = cssS;
    }

    public void loadScene() {
        try {
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(
                            getClass().getResource(nextScene)), resourceBundle);
            loadSceneShow(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadScene(SudokuBoard loadedBoard) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene), resourceBundle);
            Parent root = loader.load();

            SudokuBoardController controller = loader.getController();

            controller.setIsLoaded(true);
            controller.setBoard(loadedBoard);

            loadSceneInitializeShow(root, controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadScene(Difficulty difficulty) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nextScene), resourceBundle);
            Parent root = loader.load();

            SudokuBoardController controller = loader.getController();

            controller.setDifficulty(difficulty);

            loadSceneInitializeShow(root, controller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSceneInitializeShow(Parent root, SudokuBoardController controller) {
        controller.initialize();
        makeStage(root);
    }

    private void loadSceneShow(Parent root) {
        makeStage(root);
    }

    private void makeStage(Parent root) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(this.getClass().getResource(cssStyle)).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}
