package view;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BookingController extends BaseController{


    @FXML
    private void handleLogout(){
        mainController.logout();
    }
    @FXML
    private void handleMemberships(){
        mainController.switchScene(Screen.MEMBERSHIPS);
    }
    @FXML
    private void handleProfile(){
        mainController.switchScene(Screen.PROFILE);
    }
    @FXML
    private void handleContactUs(){
        mainController.switchScene(Screen.CONTACT_US);
    }
    @FXML
    private void cancel(){
        mainController.switchScene(Screen.HOME);
    }
    @FXML
    private Label yogaTrainer;
    @FXML
    private Label yogaBranch;
    @FXML
    private Label yogaDate;
    @FXML
    private Label yogaStartTime;
    @FXML
    private Label yogaDuration;
    
    @FXML
    private Label pilatesTrainer;
    @FXML
    private Label pilatesBranch;
    @FXML
    private Label pilatesDate;
    @FXML
    private Label pilatesStartTime;
    @FXML
    private Label pilatesDuration;
    
    @FXML
    private Label cyclingTrainer;
    @FXML
    private Label cyclingBranch;
    @FXML
    private Label cyclingDate;
    @FXML
    private Label cyclingStartTime;
    @FXML
    private Label cyclingDuration;
    
    @FXML
    private Label boxingTrainer;
    @FXML
    private Label boxingBranch;
    @FXML
    private Label boxingDate;
    @FXML
    private Label boxingStartTime;
    @FXML
    private Label boxingDuration;
    
    @FXML
    private Label hiitTrainer;
    @FXML
    private Label hiitBranch;
    @FXML
    private Label hiitDate;
    @FXML
    private Label hiitStartTime;
    @FXML
    private Label hiitDuration;
    
    @FXML
    private Label zumbaTrainer;
    @FXML
    private Label zumbaBranch;
    @FXML
    private Label zumbaDate;
    @FXML
    private Label zumbaStartTime;
    @FXML
    private Label zumbaDuration;

}
