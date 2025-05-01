package view;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeController extends BaseController{
    @FXML 
    private void handleProfile(){
        mainController.switchScene(Screen.PROFILE);
    }
    @FXML
    private void handleMemberships(){
        mainController.switchScene(Screen.MEMBERSHIPS);
    }
    @FXML
    private void handleContactUs(){
        mainController.switchScene(Screen.CONTACT_US);
    }
    @FXML
    private void handleLogout(){
        mainController.logout();
    }
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button membershipsButton;
    @FXML
    private Button contactUsButton;
    @FXML
    private Button boookSessionButton;
    @FXML
    private Button cancelSessionButton;

}
