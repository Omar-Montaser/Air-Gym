package view;

import controller.MainController;
import controller.Screen;
import javafx.fxml.FXML;

public abstract class BaseController {
        public MainController mainController;
        public void setMain(MainController main) {
            this.mainController = main;
        }
    @FXML public void handleLogout() {mainController.switchScene(Screen.LOGIN);}
}

