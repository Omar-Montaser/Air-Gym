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

public class MembershipTypeViewController extends BaseController {
    static ObservableList<MembershipType> membershipList = FXCollections.observableArrayList();

    public void fillMembershipTable() {
        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("membershipTypeID"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            accessLevelColumn.setCellValueFactory(new PropertyValueFactory<>("accessLevel"));
            monthlyPriceColumn.setCellValueFactory(new PropertyValueFactory<>("monthlyPrice"));
            sessionsColumn.setCellValueFactory(new PropertyValueFactory<>("noOfSessions"));
            privateTrainerColumn.setCellValueFactory(new PropertyValueFactory<>("privateTrainer"));
            freezeDurationColumn.setCellValueFactory(new PropertyValueFactory<>("freezeDuration"));
            inBodyColumn.setCellValueFactory(new PropertyValueFactory<>("inBody"));
            colorHexColumn.setCellValueFactory(new PropertyValueFactory<>("colorHex"));

            String center = "-fx-alignment: CENTER;";
            idColumn.setStyle(center);
            nameColumn.setStyle(center);
            descriptionColumn.setStyle(center);
            accessLevelColumn.setStyle(center);
            monthlyPriceColumn.setStyle(center);
            sessionsColumn.setStyle(center);
            privateTrainerColumn.setStyle(center);
            freezeDurationColumn.setStyle(center);
            inBodyColumn.setStyle(center);
            colorHexColumn.setStyle(center);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<MembershipType> types = mainController.getAllMembershipTypes();
            membershipList.clear();
            if (types == null || types.isEmpty()) {
                membershipTable.setPlaceholder(new Label("No membership types found"));
            } else {
                membershipList.addAll(types);
            }
            membershipTable.setItems(membershipList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        MembershipType selected = membershipTable.getSelectionModel().getSelectedItem();
        mainController.setCurrentMembershipType(selected);
        // mainController.deleteMembershipType();
        fillMembershipTable();
    }

    // Navigation
    @FXML private void handleDashBoard() { mainController.switchScene(Screen.DASHBOARD); }
    @FXML private void handleTrainers() { mainController.switchScene(Screen.TRAINER_VIEW); }
    @FXML private void handleMembers() { mainController.switchScene(Screen.MEMBER_VIEW); }
    @FXML private void handleBranches() { mainController.switchScene(Screen.BRANCH_VIEW); }
    @FXML private void handleEquipment() { mainController.switchScene(Screen.EQUIPMENT_VIEW); }
    @FXML private void handleSessions() { mainController.switchScene(Screen.SESSION_VIEW); }
    @FXML private void handleMembershipTypes() { mainController.switchScene(Screen.MEMBERSHIP_TYPE_VIEW); }
    @FXML private void handlePayments() { mainController.switchScene(Screen.PAYMENT_VIEW); }
    @FXML private void handleLogout() {}

    @FXML private TableColumn<MembershipType, Integer> idColumn;
    @FXML private TableColumn<MembershipType, String> nameColumn;
    @FXML private TableColumn<MembershipType, String> descriptionColumn;
    @FXML private TableColumn<MembershipType, Integer> accessLevelColumn;
    @FXML private TableColumn<MembershipType, Double> monthlyPriceColumn;
    @FXML private TableColumn<MembershipType, Integer> sessionsColumn;
    @FXML private TableColumn<MembershipType, Boolean> privateTrainerColumn;
    @FXML private TableColumn<MembershipType, Integer> freezeDurationColumn;
    @FXML private TableColumn<MembershipType, Boolean> inBodyColumn;
    @FXML private TableColumn<MembershipType, String> colorHexColumn;
    @FXML private TableView<MembershipType> membershipTable;
}
