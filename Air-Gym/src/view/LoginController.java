package view;

import controller.Screen;
import controller.Validation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends BaseController{
    @FXML 
    private TextField phoneNumber;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin(){
        try{
            Validation.validatePhoneNumber(phoneNumber.getText());
            Validation.validatePassword(password.getText());
            if(!mainController.login(phoneNumber.getText(), password.getText()))
                throw new Exception("Invalid phone number or password");
            if(!mainController.isAdmin()) mainController.switchScene(Screen.HOME);
            // else mainController.switchScene(Screen.ADMIN_DASHBOARD);
        }
        catch(Exception e){
            // errorLabel.setVisible(true);
            // errorLabel.setText(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML 
    private void handleGuestLogin(){
        mainController.switchScene(Screen.MEMBERSHIPS);
    }
}
