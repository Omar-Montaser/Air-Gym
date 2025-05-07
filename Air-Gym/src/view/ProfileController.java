package view;

import model.accounts.Member;

import java.sql.SQLException;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController extends BaseController{
    @FXML private Label messageLabel;
    public void fillDetails(){
        Member member=mainController.getCurrentMember();
        messageLabel.setVisible(false);
        fistNameLabel.setText(member.getFirstName());
        lastNameLabel.setText(member.getLastName());
        phoneNumberLabel.setText(member.getPhoneNumber());
        birthDateLabel.setText(member.getBirthDate().toString());
        genderLabel.setText(member.getGender());
        membershipTypeLabel.setText(mainController.getMembershipName(member.getMembershipId()));
        branchLabel.setText(mainController.getBranchByID(member.getBranchId()).getName());
        subscriptionStatusLabel.setText(member.getSubscriptionStatus());
        subscriptionStartDateLabel.setText(member.getSubscriptionStartDate().toString());
        subscriptionEndDateLabel.setText(member.getSubscriptionEndDate().toString());
        ptNameLabel.setText(mainController.getTrainerByID(member.getTrainerId())==null ? "No Trainer" : 
        mainController.getTrainerByID(member.getTrainerId()).getFirstName() + " " 
        + mainController.getTrainerByID(member.getTrainerId()).getLastName());
    }
    @FXML
    private void handleExtendMembership(){
        mainController.setIsExtending(true);
        mainController.setIsFreezing(false);
        mainController.setSelectedMembership();
        mainController.switchScene(Screen.CHECKOUT);
    }
    @FXML
    private void handleFreezeMembership(){
        mainController.setIsExtending(false);
        mainController.setIsFreezing(true);
        mainController.setSelectedMembership();
        mainController.switchScene(Screen.CHECKOUT);
    }
    @FXML
    private void handleUnfreezeMembership(){
        try{
        mainController.unfreezeSubscription();
        fillDetails();}
        catch(SQLException e){
            messageLabel.setText(e.getMessage());
        }
    }
    @FXML
    private void handleCancelMembership(){
        try{
        mainController.cancelSubscription();
        fillDetails();
        }
        catch(Exception e){
            messageLabel.setText(e.getMessage());
        }
    }
    @FXML
    private void handleHome(){
        mainController.switchScene(Screen.HOME);
    }
    @FXML
    private void handleMemberships(){
        mainController.switchScene(Screen.MEMBERSHIPS);
    }
    @FXML
    private void handleContactUs(){
        mainController.switchScene(Screen.CONTACT_US);
    }
    @FXML
    private void handleEditCredentials(){
        try {
            String newPassword = passwordChange.getText().trim();
            String newPhoneNumber = numberChange.getText().trim();
            
            if (newPassword.isEmpty() || newPhoneNumber.isEmpty()) {
                throw new IllegalArgumentException("Password and phone number cannot be empty");
            }            
            mainController.updateMemberCredentials(newPhoneNumber, newPassword);
            
            passwordChange.clear();
            numberChange.clear();
            
            fillDetails();
            
        } catch (IllegalArgumentException e) {
            messageLabel.setVisible(true);
            messageLabel.setText(e.getMessage());
        } catch (SQLException e) {
            messageLabel.setVisible(true);
            messageLabel.setText(e.getMessage());
        }
    }
    
    @FXML
    private Button homeButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button membershipsButton;
    @FXML
    private Button contactUsButton; 
    @FXML
    private Button editButton;
    @FXML
    private Button cancelMembershipButton;
    @FXML
    private Button freezeMembershipButton;
    @FXML
    private Button extendMembershipButton;
    @FXML
    private TextField durationTextField;
    @FXML
    private Label fistNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label phoneNumberLabel;
    @FXML
    private Label birthDateLabel;
    @FXML
    private Label genderLabel;
    @FXML
    private Label membershipTypeLabel;
    @FXML
    private Label branchLabel;
    @FXML
    private Label subscriptionStatusLabel;
    @FXML
    private Label subscriptionStartDateLabel;
    @FXML
    private Label subscriptionEndDateLabel;
    @FXML
    private Label ptNameLabel;
    @FXML
    private TextField passwordChange;
    @FXML
    private TextField numberChange;    
}
