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
    public void modifyScreen(){
        if(mainController.isGuest()){
            homeButton.setVisible(false);
            profileButton.setVisible(false);
            logoutButton.setVisible(false);
            menuHBox.setAlignment(Pos.TOP_RIGHT);
            personalDetailsBox.setVisible(true);
        }
        else{
            homeButton.setVisible(true);
            profileButton.setVisible(true);
            logoutButton.setVisible(true);
            menuHBox.setAlignment(Pos.TOP_LEFT);
            personalDetailsBox.setVisible(false);
        }

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

        durationChoiceBox.getItems().clear();
        durationChoiceBox.getItems().addAll("1 Month", "3 Months (5% off)", "6 Months (10% off)", "12 Months (15% off)");
        if (!durationChoiceBox.getItems().isEmpty())
            durationChoiceBox.setValue(durationChoiceBox.getItems().get(0));
        durationChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateTotalPrice();
        });
        updateTotalPrice();
    }
    double totalPrice;
    int months;
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
        else{
            // mainController.checkout(mainController.getCurrentMember(), null, 0);
        }
    }
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
