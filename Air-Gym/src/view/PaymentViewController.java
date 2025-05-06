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

public class PaymentViewController extends BaseController {
    static ObservableList<Payment> paymentList = FXCollections.observableArrayList();

    public void fillPaymentTable() {
        try {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            memberIDColumn.setCellValueFactory(new PropertyValueFactory<>("memberID"));
            methodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
            amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

            String center = "-fx-alignment: CENTER;";
            idColumn.setStyle(center);
            categoryColumn.setStyle(center);
            memberIDColumn.setStyle(center);
            methodColumn.setStyle(center);
            dateColumn.setStyle(center);
            amountColumn.setStyle(center);
            statusColumn.setStyle(center);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<Payment> payments = mainController.getAllPayments();
            paymentList.clear();
            if (payments == null || payments.isEmpty()) {
                paymentTable.setPlaceholder(new Label("No payments found"));
            } else {
                paymentList.addAll(payments);
            }
            paymentTable.setItems(paymentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDelete() {
        Payment selected = paymentTable.getSelectionModel().getSelectedItem();
        mainController.setCurrentPayment(selected);
        // mainController.deletePayment();
        fillPaymentTable();
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

    @FXML private TableColumn<Payment, Integer> idColumn;
    @FXML private TableColumn<Payment, String> categoryColumn;
    @FXML private TableColumn<Payment, Integer> memberIDColumn;
    @FXML private TableColumn<Payment, String> methodColumn;
    @FXML private TableColumn<Payment, String> dateColumn;
    @FXML private TableColumn<Payment, Double> amountColumn;
    @FXML private TableColumn<Payment, String> statusColumn;
    @FXML private TableView<Payment> paymentTable;
}
