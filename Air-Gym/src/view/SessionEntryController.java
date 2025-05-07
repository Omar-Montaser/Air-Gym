package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.gym.members.*;
import controller.Screen;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SessionEntryController extends BaseController {
    @FXML private ChoiceBox<String> sessionTypeBox;
    @FXML private TextField trainerField;
    @FXML private TextField branchField;
    @FXML private TextField capacityField;
    @FXML private DatePicker datePicker;
    @FXML private TextField timeField; // New field for time input
    @FXML private TextField durationField;
    @FXML private ChoiceBox<String> statusBox;

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML public Label messageLabel;
    @FXML private Label titleLabel;

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public void setScreen() {
        messageLabel.setVisible(false);
        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> handleCancel());

        titleLabel.setText("New Session Entry");

        sessionTypeBox.getItems().setAll("Yoga", "HIIT", "Cycling", "Pilates", "Boxing", "Zumba");
        statusBox.getItems().setAll("Scheduled", "Completed", "Cancelled", "Full");

        sessionTypeBox.setValue(null);
        statusBox.setValue(null);
        trainerField.clear();
        branchField.clear();
        capacityField.clear();
        durationField.clear();
        datePicker.setValue(null);
        timeField.clear();

        Session currentSession = mainController.getCurrentSession();
        if (currentSession != null) {
            titleLabel.setText("Edit Session");
            sessionTypeBox.setValue(currentSession.getSessionType());
            trainerField.setText(String.valueOf(currentSession.getTrainerID()));
            branchField.setText(String.valueOf(currentSession.getBranchID()));
            capacityField.setText(String.valueOf(currentSession.getMaxCapacity()));
            durationField.setText(String.valueOf(currentSession.getDuration()));
            statusBox.setValue(currentSession.getStatus());
            datePicker.setValue(currentSession.getDateTime().toLocalDateTime().toLocalDate());
            timeField.setText(currentSession.getDateTime().toLocalDateTime().toLocalTime().format(timeFormatter));
        }
    }

    @FXML
    private void handleSave() {
        try {
            if (sessionTypeBox.getValue() == null ||
                trainerField.getText().isEmpty() ||
                branchField.getText().isEmpty() ||
                capacityField.getText().isEmpty() ||
                datePicker.getValue() == null ||
                timeField.getText().isEmpty() ||
                durationField.getText().isEmpty() ||
                statusBox.getValue() == null) {
                messageLabel.setText("Please fill in all required fields");
                messageLabel.setVisible(true);
                return;
            }

            String sessionType = sessionTypeBox.getValue();
            int trainerID = Integer.parseInt(trainerField.getText());
            int branchID = Integer.parseInt(branchField.getText());
            int maxCapacity = Integer.parseInt(capacityField.getText());
            int duration = Integer.parseInt(durationField.getText());
            LocalDate date = datePicker.getValue();
            LocalTime time = LocalTime.parse(timeField.getText(), timeFormatter);
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            String status = statusBox.getValue();

            if (mainController.getCurrentSession() != null) {
                Session currentSession = mainController.getCurrentSession();
                currentSession.setSessionType(sessionType);
                currentSession.setTrainerID(trainerID);
                currentSession.setBranchID(branchID);
                currentSession.setMaxCapacity(maxCapacity);
                currentSession.setDuration(duration);
                currentSession.setDateTime(dateTime);
                currentSession.setStatus(status);
                mainController.updateSession();
            } else {
                mainController.createSession(trainerID, branchID, sessionType, maxCapacity, dateTime, duration, status);
            }

            mainController.switchScene(Screen.SESSION_VIEW);

        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter valid numbers for ID, capacity, and duration.");
            messageLabel.setVisible(true);
        } catch (DateTimeParseException e) {
            messageLabel.setText("Time must be in HH:mm format (e.g., 14:30).");
            messageLabel.setVisible(true);
        } catch (SQLException e) {
            messageLabel.setText("Database error: " + e.getMessage());
            messageLabel.setVisible(true);
        }
    }

    @FXML
    private void handleCancel() {
        mainController.switchScene(Screen.SESSION_VIEW);
    }
}
