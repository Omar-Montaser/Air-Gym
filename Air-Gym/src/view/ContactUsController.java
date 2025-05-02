package view;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ContactUsController extends BaseController{
@FXML
private void handleLogout(){
    mainController.logout();
}
@FXML
private void handleHome(){
    mainController.switchScene(Screen.HOME);
}
@FXML
private void handleMemberships(){
    mainController.switchScene(Screen.MEMBERSHIPS);
}
@FXML
private void handleProfile(){
    mainController.switchScene(Screen.CHECKOUT);
}











}
