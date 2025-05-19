package org.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class SudokuGui extends Application {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SudokuGui.class);
    public ResourceBundle resources = ResourceBundle.getBundle("Bundle", new Locale("en"));

    @Override
    public void start(Stage primaryStage) {
        try {
            //TODO: Napisać klasę ładującą sceny aby nie powtarzać tego samego kodu ładującego sceny
            Parent root = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/StartScene.fxml")), resources
            );
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/SceneStyle.css").toExternalForm();
            scene.getStylesheets().add(css);
            Image icon = new Image("icon.png");
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Sudoku Game");
            primaryStage.setScene(scene);
            primaryStage.show();
            log.info("Sudoku Game Started");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Konfiguracja i wyświetlenie okna
        //Group root = new Group();
        //Scene scene = new Scene(root, Color.LIGHTBLUE);

        //primaryStage.setWidth(400);
        //primaryStage.setHeight(400);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
