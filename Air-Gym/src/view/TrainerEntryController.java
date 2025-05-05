
package view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import java.time.LocalDate;

import controller.Screen;
import model.accounts.*;


public class TrainerEntryController extends BaseController{
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneField;
    @FXML private ChoiceBox<String> specializationBox;
    @FXML private TextField experienceField;
    @FXML private TextField salaryField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML public Label messageLabel;
    @FXML private ChoiceBox branchChoiceBox;
    @FXML private ChoiceBox<String> genderChoiceBox;
    @FXML private ChoiceBox<String> statusChoiceBox;
    @FXML private DatePicker birthDatePicker;
    @FXML private VBox personalBox;


    public void setScreen() {
        messageLabel.setVisible(false);
        
        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());
        String firstName="";
        String lastName ="";
        String phone ="";
        String experience ="";
        String salary="";

        branchChoiceBox.getItems().clear();
        mainController.getAllBranches().forEach(branch -> {
            branchChoiceBox.getItems().add(branch.getName());
        });
        genderChoiceBox.getItems().clear();
        genderChoiceBox.getItems().addAll("Male", "Female");
        specializationBox.getItems().clear();
        specializationBox.getItems().addAll(
            "Yoga", "HIIT", "Cycling", "Pilates", "Boxing", "Zumba"
        );
        statusChoiceBox.getItems().clear();
        statusChoiceBox.getItems().addAll("Active", "OnLeave", "Terminated");

        Trainer currentTrainer=mainController.getCurrentTrainer();
        if(mainController.getCurrentTrainer()!=null){
            personalBox.setVisible(false);
            phone=currentTrainer.getPhoneNumber();
            experience=""+currentTrainer.getExperienceYears();
            salary=""+currentTrainer.getSalary();
            branchChoiceBox.setValue(mainController.getBranchByID(currentTrainer.getBranchId()).getName());
            specializationBox.setValue(currentTrainer.getSpecialization());
            statusChoiceBox.setValue(currentTrainer.getStatus());
        } else personalBox.setVisible(true);
        phoneField.setText(phone);
        experienceField.setText(experience);
        salaryField.setText(salary);
    }
    @FXML
    private void handleSave() {
        if (((firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || 
            phoneField.getText().isEmpty() || experienceField.getText().isEmpty() ||
            salaryField.getText().isEmpty() || genderChoiceBox.getValue() == null ||
            specializationBox.getValue() == null || statusChoiceBox.getValue() == null ||
            birthDatePicker.getValue() == null || branchChoiceBox.getValue() == null)
            &&mainController.getCurrentTrainer()==null) || 
            (mainController.getCurrentTrainer()!=null&&(phoneField.getText().isEmpty() || 
            experienceField.getText().isEmpty() || salaryField.getText().isEmpty()|| specializationBox.getValue() == null || statusChoiceBox.getValue() == null 
            || branchChoiceBox.getValue() == null))) {
            messageLabel.setText("Please fill in all fields");
            messageLabel.setVisible(true);
            return;
        }
        try {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String phone = phoneField.getText();
            String gender = genderChoiceBox.getValue();
            String specialization = specializationBox.getValue();
            String status = statusChoiceBox.getValue();
            LocalDate birthDate = birthDatePicker.getValue();
            String branchName = (String)branchChoiceBox.getValue();
            
            int experienceYears = Integer.parseInt(experienceField.getText());
            double salaryValue = Double.parseDouble(salaryField.getText());
            
            if (mainController.getCurrentTrainer() != null) {
                Trainer currentTrainer = mainController.getCurrentTrainer();
                currentTrainer.setPhoneNumber(phone);
                currentTrainer.setSpecialization(specialization);
                currentTrainer.setExperienceYears(experienceYears);
                currentTrainer.setSalary(salaryValue);
                currentTrainer.setStatus(status);
                currentTrainer.setBranchId(mainController.getBranchByName(branchName).getBranchID());
                mainController.updateTrainer();
            } else {
                mainController.createTrainer(firstName, lastName, phone, gender, specialization, 
                experienceYears, salaryValue, status, java.sql.Date.valueOf(birthDate),
                mainController.getBranchByName(branchName).getBranchID());
            }
            mainController.setTrainerViewLabel(true);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            messageLabel.setVisible(true);
            messageLabel.setText(e.getMessage());
            return;
        }
        mainController.switchScene(Screen.TRAINER_VIEW);
    }
    @FXML
    private void handleCancel() {
        mainController.switchScene(Screen.TRAINER_VIEW);
    }
}
