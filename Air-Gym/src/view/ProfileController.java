package view;

import model.accounts.Member;
import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProfileController extends BaseController{
    public void fillDetails(Member member){
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
        try{
        mainController.setIsExtending(true);
        mainController.setIsFreezing(false);
        mainController.setSelectedMembership();
        mainController.switchScene(Screen.CHECKOUT);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    private void handleFreezeMembership(){
        try{
        mainController.setIsExtending(false);
        mainController.setIsFreezing(true);
        mainController.setSelectedMembership();
        mainController.switchScene(Screen.CHECKOUT);
        // mainController.freezeSubscription(durationTextField.getText());
        
        }
        catch(Exception e){
            e.printStackTrace();
            //errorlabel handle
        }
    }
    @FXML
    private void handleUnfreezeMembership(){
        mainController.unfreezeSubscription();
        fillDetails(mainController.getCurrentMember());
    }
    @FXML
    private void handleCancelMembership(){
        try{
        mainController.cancelSubscription();
        fillDetails(mainController.getCurrentMember());
        }
        catch(Exception e){
            e.printStackTrace();
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
            
            fillDetails(mainController.getCurrentMember());
            
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to update credentials: " + e.getMessage());
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
