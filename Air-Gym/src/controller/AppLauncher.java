package controller;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    private static MainController app;
    @Override
    public void start(Stage stage) throws Exception {
        app= new MainController(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}