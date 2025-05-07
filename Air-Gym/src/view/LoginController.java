package view;

import java.sql.SQLException;

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
    private Label messageLabel;

    @FXML
    private void handleLogin(){
        try{
            Validation.validatePhoneNumber(phoneNumber.getText());
            Validation.validatePassword(password.getText());
            if(!mainController.login(phoneNumber.getText(), password.getText()))
                throw new Exception("Invalid phone number or password");
            if(!mainController.isAdmin()) mainController.switchScene(Screen.HOME);
            else mainController.switchScene(Screen.DASHBOARD);
        }
        catch(Exception e){
            messageLabel.setVisible(true);
            messageLabel.setText(e.getMessage());
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }
    }
    @FXML 
    private void handleGuestLogin(){
        mainController.setIsGuest(true);
        mainController.switchScene(Screen.MEMBERSHIPS);
    }
    public void clearFields(){
        messageLabel.setVisible(false);
        phoneNumber.setText("");
        password.setText("");
    }
}
