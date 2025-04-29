package view;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController extends BaseController{
    @FXML 
    private TextField UserID;
    @FXML
    private TextField Password;
    @FXML
    private Button loginButton;

    @FXML
    private void hLogin(){
        try{
            // String userID=validateID(UserID.getText());
            // String pass=validatePassword(Password.getText());
            
            // if (!mainController.logIn(UserID,Password))
            //     throw new Exception("Invalid ID or password. Please try again.");
            // mainController.getHomeController().handleCategoryAll();
            mainController.switchScene(Screen.HOME);
        }
        catch(Exception e){
            // errorLabel.setVisible(true);
            // errorLabel.setText(e.getMessage());
        }        
    }
}
