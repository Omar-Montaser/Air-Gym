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
import model.gym.Branch;
import model.gym.members.Booking;

public class BranchViewController extends BaseController {
    static ObservableList<Branch> branchList = FXCollections.observableArrayList();

        public void fillBranchTable() {
            try {
                idColumn.setCellValueFactory(new PropertyValueFactory<>("branchID"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
                openningColumn.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
                statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
                adminColumn.setCellValueFactory(new PropertyValueFactory<>("adminID"));
                String leftPadding = "-fx-alignment: CENTER;";
                idColumn.setStyle(leftPadding);
                nameColumn.setStyle(leftPadding);
                locationColumn.setStyle(leftPadding);
                openningColumn.setStyle(leftPadding);
                statusColumn.setStyle(leftPadding);
                adminColumn.setStyle(leftPadding);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try{
            List<Branch> branches = mainController.getAllBranches();
            branchList.clear();
            if (branches == null || branches.isEmpty()) {
                branchTable.setPlaceholder(new Label("No branches found"));
            } else {
                branchList.addAll(branches);
            }
            branchTable.setItems(branchList);
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }
    @FXML
    private void handleDashBoard(){
        mainController.switchScene(Screen.DASHBOARD);
    }
    
    @FXML
    private void handleTrainers(){
        mainController.switchScene(Screen.TRAINER_VIEW);
    }
    @FXML 
    private void handleMembers(){
        mainController.switchScene(Screen.MEMBER_VIEW);;
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
    private void handleMembershipTypes(){
        mainController.switchScene(Screen.MEMBERSHIP_TYPE_VIEW);
    }
    @FXML
    private void handlePayments(){
        mainController.switchScene(Screen.PAYMENT_VIEW);
    }
    @FXML
    private void handleDelete(){
        Branch selectedBranch = branchTable.getSelectionModel().getSelectedItem();
        mainController.setCurrentBranch(selectedBranch);
        mainController.deleteBranch();
        fillBranchTable();
        return;
    }
    @FXML
    private void handleEdit(){
        Branch selectedBranch = branchTable.getSelectionModel().getSelectedItem();
        if(selectedBranch==null) return;
        mainController.setCurrentBranch(selectedBranch);
        mainController.switchScene(Screen.BRANCH_ENTRY);
    }
    @FXML
    private void handleEntry(){
        mainController.setCurrentBranch(null);
        mainController.switchScene(Screen.BRANCH_ENTRY);
    }
    @FXML
    private TableColumn<Member, Integer> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> locationColumn;
    @FXML
    private TableColumn<Member, String> openningColumn;
    @FXML
    private TableColumn<Member, Branch> statusColumn;
    @FXML
    private TableColumn<Member, String> adminColumn;
    @FXML
    private TableView<Branch> branchTable;  
    
}