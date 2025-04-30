package controller;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    private static MainController app;
    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("../view/assets/fonts/Poppins-Regular.ttf"), 10);
        Font.loadFont(getClass().getResourceAsStream("../view/assets/fonts/Poppins-Bold.ttf"), 10);
        app= new MainController(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}