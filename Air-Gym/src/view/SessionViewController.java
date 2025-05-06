package view;

import controller.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.gym.members.*;

import java.util.List;

public class SessionViewController extends BaseController {
    static ObservableList<Session> sessionList = FXCollections.observableArrayList();

    public void fillSessionTable() {
        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("sessionID"));
            trainerIDColumn.setCellValueFactory(new PropertyValueFactory<>("trainerID"));
            branchIDColumn.setCellValueFactory(new PropertyValueFactory<>("branchID"));
            sessionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("sessionType"));
            maxCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("maxCapacity"));
            dateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateTime"));
            durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

            String centerAlign = "-fx-alignment: CENTER;";
            idColumn.setStyle(centerAlign);
            trainerIDColumn.setStyle(centerAlign);
            branchIDColumn.setStyle(centerAlign);
            sessionTypeColumn.setStyle(centerAlign);
            maxCapacityColumn.setStyle(centerAlign);
            dateTimeColumn.setStyle(centerAlign);
            durationColumn.setStyle(centerAlign);
            statusColumn.setStyle(centerAlign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Session> sessions = mainController.getAllSessions();
            sessionList.clear();
            if (sessions == null || sessions.isEmpty()) {
                sessionTable.setPlaceholder(new Label("No sessions found"));
            } else {
                sessionList.addAll(sessions);
            }
            sessionTable.setItems(sessionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        // Session selected = sessionTable.getSelectionModel().getSelectedItem();
        // mainController.setCurrentSession(selected);
        // mainController.deleteSession();
        // fillSessionTable();
    }

    // Navigation handlers
    @FXML private void handleDashBoard() { mainController.switchScene(Screen.DASHBOARD); }
    @FXML private void handleTrainers() { mainController.switchScene(Screen.TRAINER_VIEW); }
    @FXML private void handleMembers() { mainController.switchScene(Screen.MEMBER_VIEW); }
    @FXML private void handleBranches() { mainController.switchScene(Screen.BRANCH_VIEW); }
    @FXML private void handleEquipment() { mainController.switchScene(Screen.EQUIPMENT_VIEW); }
    @FXML private void handleSessions() { mainController.switchScene(Screen.SESSION_VIEW); }
    @FXML private void handleMembershipTypes() { mainController.switchScene(Screen.MEMBERSHIP_TYPE_VIEW); }
    @FXML private void handlePayments() { mainController.switchScene(Screen.PAYMENT_VIEW); }


    @FXML private TableColumn<Session, Integer> idColumn;
    @FXML private TableColumn<Session, Integer> trainerIDColumn;
    @FXML private TableColumn<Session, Integer> branchIDColumn;
    @FXML private TableColumn<Session, String> sessionTypeColumn;
    @FXML private TableColumn<Session, Integer> maxCapacityColumn;
    @FXML private TableColumn<Session, String> dateTimeColumn;
    @FXML private TableColumn<Session, Integer> durationColumn;
    @FXML private TableColumn<Session, String> statusColumn;
    @FXML private TableView<Session> sessionTable;
}
