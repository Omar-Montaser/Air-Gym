
package view;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
        genderChoiceBox.getItems().addAll("Male", "Female");
        specializationBox.getItems().addAll(
            "Yoga", "HIIT", "Cycling", "Pilates", "Boxing", "Zumba"
            );
        statusChoiceBox.getItems().addAll("Active", "OnLeave", "Terminated");


        Trainer currentTrainer=mainController.getCurrentTrainer();
        if(mainController.getCurrentTrainer()!=null){
            firstName=currentTrainer.getFirstName();
            lastName=currentTrainer.getLastName();
            phone=currentTrainer.getPhoneNumber();
            experience=""+currentTrainer.getExperienceYears();
            salary=""+currentTrainer.getSalary();
            branchChoiceBox.setValue(mainController.getBranchByID(currentTrainer.getBranchId()).getName());
            genderChoiceBox.setValue(currentTrainer.getGender());
            specializationBox.setValue(currentTrainer.getSpecialization());
            statusChoiceBox.setValue(currentTrainer.getStatus());
            birthDatePicker.setValue((currentTrainer.getBirthDate()).toLocalDate());
            // Print trainer details for debugging purposes
            System.out.println("Trainer Details:");
            System.out.println("ID: " + currentTrainer.getUserId());
            System.out.println("Name: " + firstName + " " + lastName);
            System.out.println("Phone: " + phone);
            System.out.println("Gender: " + currentTrainer.getGender());
            System.out.println("Specialization: " + currentTrainer.getSpecialization());
            System.out.println("Experience: " + experience + " years");
            System.out.println("Salary: $" + salary);
            System.out.println("Status: " + currentTrainer.getStatus());
            System.out.println("Birth Date: " + currentTrainer.getBirthDate());
            System.out.println("Branch: " + mainController.getBranchByID(currentTrainer.getBranchId()).getName());
        }
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        phoneField.setText(salary);
        experienceField.setText(experience);
        salaryField.setText(salary);
    }
    @FXML
    private void handleSave() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() || 
            phoneField.getText().isEmpty() || experienceField.getText().isEmpty() ||
            salaryField.getText().isEmpty() || genderChoiceBox.getValue() == null ||
            specializationBox.getValue() == null || statusChoiceBox.getValue() == null ||
            birthDatePicker.getValue() == null || branchChoiceBox.getValue() == null) {
            messageLabel.setText("Please fill in all fields");
            messageLabel.setStyle("-fx-text-fill: red");
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
            String branchName = (String) branchChoiceBox.getValue();
            
            int experienceYears = Integer.parseInt(experienceField.getText());
            double salaryValue = Double.parseDouble(salaryField.getText());
            
            if (mainController.getCurrentTrainer() != null) {
                Trainer currentTrainer = mainController.getCurrentTrainer();
                currentTrainer.setFirstName(firstName);
                currentTrainer.setLastName(lastName);
                currentTrainer.setPhoneNumber(phone);
                currentTrainer.setGender(gender);
                currentTrainer.setSpecialization(specialization);
                currentTrainer.setExperienceYears(experienceYears);
                currentTrainer.setSalary(salaryValue);
                currentTrainer.setStatus(status);
                currentTrainer.setDateOfBirth(java.sql.Date.valueOf(birthDate));
                currentTrainer.setBranchId(mainController.getBranchByName(branchName).getBranchID());
                mainController.updateTrainer();
            } else {
                    mainController.createTrainer(firstName, lastName, phone, gender, specialization, 
                    experienceYears, salaryValue, status, java.sql.Date.valueOf(birthDate),
                    mainController.getBranchByName(branchName).getBranchID());
            }
            
            messageLabel.setText("Trainer saved successfully!");
            messageLabel.setStyle("-fx-background-color: green");
            messageLabel.setVisible(true);
        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setStyle("-fx-background-color:red");
            messageLabel.setVisible(true);
        }
        mainController.switchScene(Screen.TRAINER_VIEW);
    }
    @FXML
    private void handleCancel() {
        mainController.switchScene(Screen.TRAINER_VIEW);
    }
}
