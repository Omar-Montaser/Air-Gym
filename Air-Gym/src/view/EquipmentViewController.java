package view;

import controller.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.gym.Equipment;

import java.util.List;

public class EquipmentViewController extends BaseController {
    static ObservableList<Equipment> equipmentList = FXCollections.observableArrayList();

    public void fillEquipmentTable() {
        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("equipmentID"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            purchaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
            maintenanceDateColumn.setCellValueFactory(new PropertyValueFactory<>("maintenanceDate"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            branchIDColumn.setCellValueFactory(new PropertyValueFactory<>("branchID"));

            String centerAlign = "-fx-alignment: CENTER;";
            idColumn.setStyle(centerAlign);
            nameColumn.setStyle(centerAlign);
            purchaseDateColumn.setStyle(centerAlign);
            maintenanceDateColumn.setStyle(centerAlign);
            statusColumn.setStyle(centerAlign);
            branchIDColumn.setStyle(centerAlign);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Equipment> equipment = mainController.getAllEquipment();
            equipmentList.clear();
            if (equipment == null || equipment.isEmpty()) {
                equipmentTable.setPlaceholder(new Label("No equipment found"));
            } else {
                equipmentList.addAll(equipment);
            }
            equipmentTable.setItems(equipmentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        // Equipment selected = equipmentTable.getSelectionModel().getSelectedItem();
        // mainController.setCurrentEquipment(selected);
        // mainController.deleteEquipment();
        // fillEquipmentTable();
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

    @FXML private TableColumn<Equipment, Integer> idColumn;
    @FXML private TableColumn<Equipment, String> nameColumn;
    @FXML private TableColumn<Equipment, String> purchaseDateColumn;
    @FXML private TableColumn<Equipment, String> maintenanceDateColumn;
    @FXML private TableColumn<Equipment, String> statusColumn;
    @FXML private TableColumn<Equipment, Integer> branchIDColumn;
    @FXML private TableView<Equipment> equipmentTable;
}
