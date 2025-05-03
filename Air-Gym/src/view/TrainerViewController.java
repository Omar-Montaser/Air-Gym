package view;

import java.sql.Date;
import java.util.List;

import controller.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.accounts.Member;
import model.accounts.Trainer;
import model.accounts.Trainer;
import model.gym.Branch;

public class TrainerViewController extends BaseController{
    @FXML
    private TableColumn<Trainer, Integer> id;
    @FXML
    private TableColumn<Trainer, String> name;
    @FXML
    private TableColumn<Trainer, String> number;
    @FXML
    private TableColumn<Trainer, String> gender;
    @FXML
    private TableColumn<Trainer, String> specialization;
    @FXML
    private TableColumn<Trainer, Integer> experience;
    @FXML
    private TableColumn<Trainer, Double> salary;
    @FXML
    private TableColumn<Trainer, String> branch;
    @FXML
    private TableColumn<Trainer, String> status;
    @FXML
    private TableView<Trainer> trainersTable;


    static ObservableList<Trainer> TrainerList = FXCollections.observableArrayList();

        public void fillTrainerTable() {
            try {
                id.setCellValueFactory(new PropertyValueFactory<>("userId"));
                name.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                number.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
                specialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
                experience.setCellValueFactory(new PropertyValueFactory<>("experienceYears"));
                salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
                branch.setCellValueFactory(new PropertyValueFactory<>("branchName"));
                status.setCellValueFactory(new PropertyValueFactory<>("status"));

                String leftPadding = "-fx-alignment: CENTER;";
                id.setStyle(leftPadding);
                name.setStyle(leftPadding);
                number.setStyle(leftPadding);
                gender.setStyle(leftPadding);
                specialization.setStyle(leftPadding);
                experience.setStyle(leftPadding);
                salary.setStyle(leftPadding);
                branch.setStyle(leftPadding);
                status.setStyle(leftPadding);
                
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Trainer> trainers = mainController.getAllTrainerDetails();
            TrainerList.clear();
            if (trainers == null || trainers.isEmpty()) {
                trainersTable.setPlaceholder(new Label("No bookings found"));
            } else {
                TrainerList.addAll(trainers);
                System.out.println("Added " + trainers.size() + " bookings to the table");
            }
            trainersTable.setItems(TrainerList);
            System.out.println("trainers in the table:");
            for (int i = 0; i < trainers.size(); i++)
                System.out.println("Trainer " + i + ": " + trainers.get(i).getFullName());
            
    
    }
    @FXML
    private void handleDashBoard(){
        mainController.switchScene(Screen.DASHBOARD);
    }
    
    @FXML
    private void handletrainers(){
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
    private void handletrainershipTypes(){
        mainController.switchScene(Screen.MEMBERSHIP_TYPE_VIEW);
    }
    @FXML
    private void handlePayments(){
        mainController.switchScene(Screen.PAYMENT_VIEW);
    }
}
