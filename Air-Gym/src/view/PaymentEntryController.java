package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import controller.Screen;
import java.sql.SQLException;

public class PaymentEntryController extends BaseController {
    @FXML private ChoiceBox<String> categoryBox;
    @FXML private TextField memberIDField;
    @FXML private ChoiceBox<String> paymentMethodBox;
    @FXML private TextField amountField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML public Label messageLabel;
    @FXML private Label titleLabel;

    public void setScreen() {
        messageLabel.setVisible(false);
        titleLabel.setText("New Payment Entry");
        categoryBox.getItems().setAll("Membership", "Expense", "Other");
        paymentMethodBox.getItems().setAll("CreditCard", "DebitCard", "Cash", "BankTransfer", "Other");

        categoryBox.setValue(null);
        memberIDField.clear();
        paymentMethodBox.setValue(null);
        amountField.clear();

        saveButton.setOnAction(event -> handleSave());
        cancelButton.setOnAction(event -> mainController.switchScene(Screen.PAYMENT_VIEW));
    }

    @FXML
    private void handleSave() {
        try {
            if (categoryBox.getValue() == null ||
                memberIDField.getText().isEmpty() ||
                paymentMethodBox.getValue() == null ||
                amountField.getText().isEmpty()) {
                messageLabel.setText("Please fill in all fields.");
                messageLabel.setVisible(true);
                return;
            }

            String category = categoryBox.getValue();
            int memberID = Integer.parseInt(memberIDField.getText());
            String paymentMethod = paymentMethodBox.getValue();
            double amount = Double.parseDouble(amountField.getText());

            if (amount <= 0) {
                messageLabel.setText("Amount must be greater than 0.");
                messageLabel.setVisible(true);
                return;
            }

            mainController.createPayment(memberID, category, paymentMethod, amount);
            mainController.switchScene(Screen.PAYMENT_VIEW);

        } catch (NumberFormatException e) {
            messageLabel.setText("Amount and Member ID must be valid numbers.");
            messageLabel.setVisible(true);
        } catch (SQLException e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setVisible(true);
        }
    }
}
