package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.gym.Equipment;
import controller.Screen;

import java.sql.SQLException;

public class EquipmentEntryController extends BaseController {
    @FXML private TextField nameField;
    @FXML private ChoiceBox<String> statusBox;
    @FXML private TextField branchField;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML public Label messageLabel;
    @FXML private Label titleLabel;

    public void setScreen() {
        messageLabel.setVisible(false);

        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());

        titleLabel.setText("New Equipment Entry");
        nameField.clear();
        branchField.clear();

        statusBox.getItems().clear();
        statusBox.getItems().addAll("Available", "Maintenance", "Retired");

        Equipment currentEquipment = mainController.getCurrentEquipment();
        if (currentEquipment != null) {
            titleLabel.setText("Edit Equipment");
            nameField.setText(currentEquipment.getName());
            statusBox.setValue(currentEquipment.getStatus());
            branchField.setText(String.valueOf(currentEquipment.getBranchID()));
        }
    }

    @FXML
    private void handleSave() {
        if (nameField.getText().isEmpty() ||
            statusBox.getValue() == null ||
            branchField.getText().isEmpty()) {
            messageLabel.setText("Please fill in all required fields");
            messageLabel.setVisible(true);
            return;
        }

        try {
            String name = nameField.getText();
            String status = statusBox.getValue();
            int branchID = Integer.parseInt(branchField.getText());

            if (mainController.getCurrentEquipment() != null) {
                Equipment currentEquipment = mainController.getCurrentEquipment();
                currentEquipment.setName(name);
                currentEquipment.setStatus(status);
                currentEquipment.setBranchID(branchID);
                mainController.updateEquipment();
            } else {
                mainController.createEquipment(name, status, branchID);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            messageLabel.setText(e.getMessage());
            messageLabel.setVisible(true);
            return;
        }

        mainController.switchScene(Screen.EQUIPMENT_VIEW);
    }
                                                            
    @FXML
    private void handleCancel() {
        mainController.switchScene(Screen.EQUIPMENT_VIEW);
    }
}
