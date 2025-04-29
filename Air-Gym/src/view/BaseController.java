package view;

import controller.MainController;

public abstract class BaseController {
        public MainController mainController;
        public void setMain(MainController main) {
            this.mainController = main;
        }
}

