package view;

import model.accounts.Member;

import java.sql.Date;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CheckoutController extends BaseController{
    double totalPrice;
    int months;
    int days;
    public void modifyScreen(){
        paymentLabel.setText("Payment");
        checkoutButton.setText("Checkout");
        if(mainController.isGuest()){
            homeButton.setVisible(false);
            profileButton.setVisible(false);
            logoutButton.setVisible(false);
            menuHBox.setAlignment(Pos.TOP_RIGHT);
            personalDetailsBox.setVisible(true);
            membershipLabel.setText("Buy "+mainController.getSelectedMembership().getName()+" Membership");
        
            branchChoiceBox.getItems().clear();
            mainController.getAllBranches().forEach(branch -> {
                branchChoiceBox.getItems().add(branch.getName());
            });
    
            if (!branchChoiceBox.getItems().isEmpty()) branchChoiceBox.setValue(branchChoiceBox.getItems().get(0));
            
            genderChoiceBox.getItems().clear();
            genderChoiceBox.getItems().addAll("Male", "Female");
            if (!genderChoiceBox.getItems().isEmpty()) {
                genderChoiceBox.setValue(genderChoiceBox.getItems().get(0));
            }
        }
        else{
            homeButton.setVisible(true);
            profileButton.setVisible(true);
            logoutButton.setVisible(true);
            menuHBox.setAlignment(Pos.TOP_LEFT);
            personalDetailsBox.setVisible(false);
            checkoutBox.setVisible(true);
            if(mainController.isExtending())
                membershipLabel.setText("Extend Current Membership");
            else if(mainController.isFreezing()){
                checkoutBox.setVisible(false);
                membershipLabel.setText("Freeze Current Membership");
                paymentLabel.setText("Freeze");
                checkoutButton.setText("Freeze");
            }
            else{
                membershipLabel.setText("Renew with "+mainController.getSelectedMembership().getName()+" Membership");
            }
        }
        
        if(mainController.isFreezing()){
            durationChoiceBox.getItems().clear();
            durationChoiceBox.getItems().addAll("7 days", "14 days", "30 days");
            if (!durationChoiceBox.getItems().isEmpty())
                durationChoiceBox.setValue(durationChoiceBox.getItems().get(0));
            durationChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (durationChoiceBox.getValue() != null) {
                    days = 7;
                    switch (durationChoiceBox.getValue()) {
                        case "7 days":
                            days = 7;
                            break;
                        case "14 days":
                            days = 14;
                            break;
                        case "30 days":
                            days = 30;
                            break;
                    }
                }
            });
        }
        else{
        durationChoiceBox.getItems().clear();
        if(mainController.isFreezing())
            durationChoiceBox.getItems().addAll("1 Month", "2 Months", "3 Months");
        else
            durationChoiceBox.getItems().addAll("1 Month", "3 Months (5% off)", "6 Months (10% off)", "12 Months (15% off)");
        if (!durationChoiceBox.getItems().isEmpty())
            durationChoiceBox.setValue(durationChoiceBox.getItems().get(0));
        durationChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateTotalPrice();
        });
        }
    }
    private void updateTotalPrice() {
        if (durationChoiceBox.getValue() != null) {
            months = 1;
            switch (durationChoiceBox.getValue()) {
                case "1 Month":
                    months = 1;
                    break;
                case "3 Months (5% off)":
                    months = 3;
                    break;
                case "6 Months (10% off)":
                    months = 6;
                    break;
                case "12 Months (15% off)":
                    months = 12;
                    break;
            }
            
            double monthlyPrice = mainController.getSelectedMembership().getMonthlyPrice();
            double discount = 0;
            if (months == 3) discount = 0.05;
            else if (months == 6) discount = 0.10; 
            else if (months == 12) discount = 0.15; 
            
            double totalPrice=months*monthlyPrice*(1-discount);
            totalPrice = Math.floor(totalPrice) + 0.99;
            totalPriceLabel.setText(String.format("$%.2f", totalPrice));
        }
    }
    @FXML
    private void handleCheckout(){
        if(mainController.isGuest()){
            mainController.guestCheckout(totalPrice, firstNameTextField.getText(), lastNameTextField.getText(), 
            passwordTextField.getText(), phoneNumberTextField.getText(), genderChoiceBox.getValue(), 
            java.sql.Date.valueOf(birthDatePicker.getValue()),months, branchChoiceBox.getValue());
            mainController.switchScene(Screen.PROFILE);
        }
        else {
            if(mainController.isExtending()){
                mainController.extendSubscription(months,totalPrice);
                mainController.switchScene(Screen.PROFILE);
            }
            else if(mainController.isFreezing()){
                mainController.freezeSubscription(days);
                mainController.switchScene(Screen.PROFILE);
            }
            else{
                mainController.renewSubscription(months,totalPrice);
                mainController.switchScene(Screen.PROFILE);
            }
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
    private void handleCancel(){
        if(mainController.isExtending()||mainController.isFreezing())
            mainController.switchScene(Screen.PROFILE);
        else
            mainController.switchScene(Screen.MEMBERSHIPS);
    }
    @FXML
    private void handleProfile(){
        mainController.switchScene(Screen.PROFILE);
    }
    @FXML
    private Label paymentLabel; 
    @FXML
    private VBox checkoutBox;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private HBox menuHBox;
    @FXML
    private VBox personalDetailsBox;
    @FXML
    private Label membershipLabel;
    @FXML
    private ChoiceBox<String> durationChoiceBox;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private ChoiceBox<String> genderChoiceBox;
    @FXML
    private ChoiceBox<String> branchChoiceBox;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private Button checkoutButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField passwordTextField;






}
