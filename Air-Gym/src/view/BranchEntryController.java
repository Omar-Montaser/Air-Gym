package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.gym.Branch;
import controller.Screen;

import java.sql.SQLException;

public class BranchEntryController extends BaseController {
    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> statusBox;
    @FXML private TextField locationField;
    @FXML private TextField adminField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML public Label messageLabel;
    @FXML private Label titleLabel;

    public void setScreen() {
        messageLabel.setVisible(false);

        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());

        statusBox.getItems().clear();
        nameField.clear();
        locationField.clear();
        adminField.clear();

        statusBox.getItems().addAll("Active","Maintenance", "Closed");
        Branch currentBranch = mainController.getCurrentBranch();
        if (currentBranch != null) {
            titleLabel.setText("Edit Branch");
            nameField.setText(currentBranch.getName());
            statusBox.setValue(currentBranch.getStatus());
            locationField.setText(currentBranch.getLocation());
            adminField.setText(String.valueOf(currentBranch.getAdminID()));
        } else titleLabel.setText("New Branch Entry");
    }

    @FXML
    private void handleSave() {
        if (nameField.getText().isEmpty() ||
            statusBox.getValue() == null ||
            locationField.getText().isEmpty() ||
            adminField.getText().isEmpty()) {
            messageLabel.setText("Please fill in all fields");
            messageLabel.setVisible(true);
            return;
        }

        try {
            String name = nameField.getText();
            String status = statusBox.getValue();
            String location = locationField.getText();
            int adminID = Integer.parseInt(adminField.getText());

            if (mainController.getCurrentBranch() != null) {
                Branch currentBranch = mainController.getCurrentBranch();
                currentBranch.setName(name);
                currentBranch.setStatus(status);
                currentBranch.setLocation(location);
                currentBranch.setAdminID(adminID);
                mainController.updateBranch();
            } else {
                mainController.createBranch(name, location, status, adminID);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            messageLabel.setText(e.getMessage());
            messageLabel.setVisible(true);
            return;
        }

        mainController.switchScene(Screen.BRANCH_VIEW);
    }

    @FXML
    private void handleCancel() {
        mainController.switchScene(Screen.BRANCH_VIEW);
    }
}
