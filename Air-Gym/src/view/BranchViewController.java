package view;

import controller.Screen;
import javafx.fxml.FXML;

public class BranchViewController extends BaseController{
    @FXML
    private void handleMember(){
        mainController.switchScene(Screen.MEMBER_VIEW);
    }
    
    @FXML
    private void handleTrainer(){
        mainController.switchScene(Screen.TRAINER_VIEW);
    }
    
    @FXML
    private void handleBranches(){
        mainController.switchScene(Screen.BRANCH_VIEW);
    }
    
    @FXML
    private void handleEquipment(){
        mainController.switchScene(Screen.EQUIPMENT_VIEW);
    }
    
    @FXML
    private void handleSessions(){
        mainController.switchScene(Screen.SESSION_VIEW);
    }
    
    @FXML
    private void handlePayments(){
        mainController.switchScene(Screen.PAYMENT_VIEW);
    }
}
