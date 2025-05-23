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

public class MemberViewController extends BaseController {
    static ObservableList<Member> memberList = FXCollections.observableArrayList();

        public void fillMemberTable() {
            try {
                idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("FullName"));
                numberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                ageColumn.setCellValueFactory(new PropertyValueFactory<>("Age"));
                genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
                branchColumn.setCellValueFactory(new PropertyValueFactory<>("BranchName"));
                membershipColumn.setCellValueFactory(new PropertyValueFactory<>("membershipName"));
                trainerColumn.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
                statusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
                subscriptionEndColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
                freezeEndColumn.setCellValueFactory(new PropertyValueFactory<>("freezeEndDate"));
                sessionsColumn.setCellValueFactory(new PropertyValueFactory<>("sessionsAvailable"));
                freezesColumn.setCellValueFactory(new PropertyValueFactory<>("freezeAvailable"));

                String leftPadding = "-fx-alignment: CENTER;";
                idColumn.setStyle(leftPadding);
                nameColumn.setStyle(leftPadding);
                numberColumn.setStyle(leftPadding);
                ageColumn.setStyle(leftPadding);
                genderColumn.setStyle(leftPadding);
                branchColumn.setStyle(leftPadding);
                membershipColumn.setStyle(leftPadding);
                trainerColumn.setStyle(leftPadding);
                statusColumn.setStyle(leftPadding);
                subscriptionEndColumn.setStyle(leftPadding);
                freezeEndColumn.setStyle(leftPadding);
                sessionsColumn.setStyle(leftPadding);
                freezesColumn.setStyle(leftPadding);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Member> members = mainController.getAllMemberDetails();
            memberList.clear();
            //Print member details for debugging
            if (members != null) {
                System.out.println("Members retrieved from database: " + members.size());
                for (Member member : members) {
                    System.out.println("Member ID: " + member.getUserId() + 
                                     ", Name: " + member.getFullName() + 
                                     ", Phone: " + member.getPhoneNumber() + 
                                     ", Branch: " + member.getBranchName() +
                                     ", Membership: " + member.getMembershipName() +
                                     ", Trainer: " + member.getTrainerName() +
                                     ", Status: " + member.getSubscriptionStatus());
                }
            }

            if (members == null || members.isEmpty()) {
                memberTable.setPlaceholder(new Label("No members found"));
            } else {
                memberList.addAll(members);
            }
            memberTable.setItems(memberList);
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
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        mainController.deleteUser(selectedMember.getUserId());
        fillMemberTable();
        return;
    }
    @FXML
    private void handleEdit(){
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        if(selectedMember==null) return;
        mainController.setCurrentMember(selectedMember);
        mainController.switchScene(Screen.MEMBER_EDIT);
    }

    @FXML
    private TableColumn<Member, Integer> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> numberColumn;
    @FXML
    private TableColumn<Member, Integer> ageColumn;
    @FXML
    private TableColumn<Member, String> genderColumn;
    @FXML
    private TableColumn<Member, Branch> branchColumn;
    @FXML
    private TableColumn<Member, String> membershipColumn;
    @FXML
    private TableColumn<Member, Trainer> trainerColumn;
    @FXML
    private TableColumn<Member, String> statusColumn;
    @FXML
    private TableColumn<Member, Date> subscriptionEndColumn;
    @FXML
    private TableColumn<Member, Date> freezeEndColumn;
    @FXML
    private TableColumn<Member, Integer> sessionsColumn;
    @FXML
    private TableColumn<Member, Integer> freezesColumn;
    @FXML
    private TableView<Member> memberTable;
    
}