package view;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ContactUsController extends BaseController{
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;
public void setView(){
    if(mainController.isGuest()){
    homeButton.setVisible(false);
    profileButton.setVisible(false);
    logoutButton.setVisible(false);
    }
    else{
    homeButton.setVisible(true);
    profileButton.setVisible(true);
    logoutButton.setVisible(true);}
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
