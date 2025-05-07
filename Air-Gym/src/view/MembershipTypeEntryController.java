// package view;

// import javafx.fxml.FXML;
// import javafx.scene.control.*;
// import model.gym.members.*;
// import controller.Screen;

// import java.sql.SQLException;

// public class MembershipTypeEntryController extends BaseController {
//     @FXML private TextField nameField;
//     @FXML private TextArea descriptionField;
//     @FXML private ChoiceBox<Integer> accessLevelBox;
//     @FXML private TextField monthlyPriceField;
//     @FXML private TextField sessionsField;
//     @FXML private ChoiceBox<String> privateTrainerBox;
//     @FXML private TextField freezeDurationField;
//     @FXML private TextField inBodyField;
//     @FXML private TextField colorHexField;

//     @FXML private Button saveButton;
//     @FXML private Button cancelButton;
//     @FXML public Label messageLabel;
//     @FXML private Label titleLabel;

//     public void setScreen() {
//         messageLabel.setVisible(false);
//         titleLabel.setText("New Membership Type");

//         accessLevelBox.getItems().setAll(1, 2, 3, 4);
//         privateTrainerBox.getItems().setAll("Yes", "No");

//         nameField.clear();
//         descriptionField.clear();
//         accessLevelBox.setValue(null);
//         monthlyPriceField.clear();
//         sessionsField.clear();
//         privateTrainerBox.setValue(null);
//         freezeDurationField.clear();
//         inBodyField.clear();
//         colorHexField.clear();

//         MembershipType current = mainController.getCurrentMembershipType();
//         if (current != null) {
//             titleLabel.setText("Edit Membership Type");
//             nameField.setText(current.getName());
//             descriptionField.setText(current.getDescription());
//             accessLevelBox.setValue(current.getAccessLevel());
//             monthlyPriceField.setText(String.valueOf(current.getMonthlyPrice()));
//             sessionsField.setText(String.valueOf(current.getSessions()));
//             privateTrainerBox.setValue(current.isPrivateTrainer() ? "Yes" : "No");
//             freezeDurationField.setText(String.valueOf(current.getFreezeDuration()));
//             inBodyField.setText(String.valueOf(current.getInBody()));
//             colorHexField.setText(current.getColorHex());
//         }

//         saveButton.setOnAction(event -> handleSave());
//         cancelButton.setOnAction(event -> mainController.switchScene(Screen.MEMBERSHIPTYPE_VIEW));
//     }

//     @FXML
//     private void handleSave() {
//         try {
//             if (nameField.getText().isEmpty() ||
//                 accessLevelBox.getValue() == null ||
//                 monthlyPriceField.getText().isEmpty() ||
//                 sessionsField.getText().isEmpty() ||
//                 privateTrainerBox.getValue() == null ||
//                 freezeDurationField.getText().isEmpty() ||
//                 inBodyField.getText().isEmpty() ||
//                 colorHexField.getText().isEmpty()) {
//                 messageLabel.setText("Please fill in all required fields.");
//                 messageLabel.setVisible(true);
//                 return;
//             }

//             String name = nameField.getText();
//             String description = descriptionField.getText();
//             int accessLevel = accessLevelBox.getValue();
//             double monthlyPrice = Double.parseDouble(monthlyPriceField.getText());
//             int sessions = Integer.parseInt(sessionsField.getText());
//             boolean privateTrainer = privateTrainerBox.getValue().equals("Yes");
//             int freezeDuration = Integer.parseInt(freezeDurationField.getText());
//             int inBody = Integer.parseInt(inBodyField.getText());
//             String colorHex = colorHexField.getText();

//             if (!colorHex.matches("^#[0-9A-Fa-f]{6}$")) {
//                 messageLabel.setText("Color must be in format #RRGGBB.");
//                 messageLabel.setVisible(true);
//                 return;
//             }

//             if (mainController.getCurrentMembershipType() != null) {
//                 MembershipType current = mainController.getCurrentMembershipType();
//                 current.setName(name);
//                 current.setDescription(description);
//                 current.setAccessLevel(accessLevel);
//                 current.setMonthlyPrice(monthlyPrice);
//                 current.setSessions(sessions);
//                 current.setPrivateTrainer(privateTrainer);
//                 current.setFreezeDuration(freezeDuration);
//                 current.setInBody(inBody);
//                 current.setColorHex(colorHex);
//                 mainController.updateMembershipType();
//             } else {
//                 mainController.createMembershipType(name, description, accessLevel, monthlyPrice,
//                     sessions, privateTrainer, freezeDuration, inBody, colorHex);
//             }

//             mainController.switchScene(Screen.MEMBERSHIPTYPE_VIEW);

//         } catch (NumberFormatException e) {
//             messageLabel.setText("Numeric fields must contain valid numbers.");
//             messageLabel.setVisible(true);
//         } catch (SQLException e) {
//             messageLabel.setText("Database error: " + e.getMessage());
//             messageLabel.setVisible(true);
//         }
//     }
// }
