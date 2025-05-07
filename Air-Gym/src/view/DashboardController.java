package view;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import utils.SqlServerConnect;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import controller.Screen;
import dao.AdminService;

public class DashboardController extends BaseController implements Initializable {
    @FXML
    private PieChart pcUsers;
    
    // Added missing labels
    @FXML
    private Label activeMembers;
    
    @FXML
    private Label allMembers;
    
    @FXML
    private Label activeTrainers;
    
    @FXML
    private Label allTrainers;
    
    @FXML
    private Label allAdmins;
    
    @FXML
    private Label allAvailableEquipment;
    
    @FXML
    private Label totalExpenses;
    
    @FXML
    private Label totalRevenue;
    
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Connection conn = SqlServerConnect.getConnection();
            AdminService adminService = new AdminService(conn);
        pcUsers.setData(FXCollections.observableArrayList(
                new PieChart.Data("Members", adminService.countActiveMembers()),
                new PieChart.Data("Trainers", adminService.countActiveTrainers()),
                new PieChart.Data("Admins", adminService.countAllAdmins())
        ));            
            activeMembers.setText("" + adminService.countActiveMembers());
            allMembers.setText("" + adminService.countAllMembers());
            activeTrainers.setText("" + adminService.countActiveTrainers());
            allAvailableEquipment.setText("" + adminService.countAllAvailableEquipment());
            totalExpenses.setText("$ "+adminService.getTotalExpenses());
            totalRevenue.setText("$ "+adminService.getTotalRevenue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    
    @FXML
    private void handleMembershipTypes(){
        mainController.switchScene(Screen.MEMBERSHIP_TYPE_VIEW);
    }
}
