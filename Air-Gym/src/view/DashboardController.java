package view;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Screen;


public class DashboardController extends BaseController implements Initializable {
    @FXML
    private PieChart pcUsers;

    public void initialize(URL url, ResourceBundle rb) {
        pcUsers.setData(FXCollections.observableArrayList(
                new PieChart.Data("Members", 54),
                new PieChart.Data("Trainers", 12),
                new PieChart.Data("Admins", 7)
        ));
    }
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
