package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import controller.Screen;
import model.accounts.Member;

public class MemberEditController extends BaseController {
    @FXML private TextField branchIdField;
    @FXML private TextField membershipIdField;
    @FXML private TextField trainerIdField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label messageLabel;

    public void setScreen() {
        messageLabel.setVisible(false);

        branchIdField.clear();
        membershipIdField.clear();
        trainerIdField.clear();
        Member currentMember = mainController.getCurrentMember();
        if (currentMember != null) {
            branchIdField.setText(String.valueOf(currentMember.getBranchId()));
            membershipIdField.setText(String.valueOf(currentMember.getMembershipId()));
            trainerIdField.setText(currentMember.getTrainerId() == null ? "" : String.valueOf(currentMember.getTrainerId()));
        }

        saveButton.setOnAction(e -> handleSave());
        cancelButton.setOnAction(e -> mainController.switchScene(Screen.MEMBER_VIEW));
    }
    @FXML
    private void handleSave() {
        try {
            int branchId = Integer.parseInt(branchIdField.getText());
            int membershipId = Integer.parseInt(membershipIdField.getText());
            Integer trainerId = trainerIdField.getText().isEmpty() ? null : Integer.parseInt(trainerIdField.getText());

            Member member = mainController.getCurrentMember();
            if (branchIdField.getText() != null && !branchIdField.getText().isEmpty()) {
                member.setBranchId(branchId);
            }
            if (membershipIdField.getText() != null && !membershipIdField.getText().isEmpty()) {
                member.setMembershipId(membershipId);
            }
            if (trainerIdField.getText() != null && !trainerIdField.getText().isEmpty()) {
                member.setTrainerId(trainerId);
            }

            mainController.updateMember();
            mainController.switchScene(Screen.MEMBER_VIEW);

        } catch (NumberFormatException ex) {
            messageLabel.setText("Invalid numeric input.");
            messageLabel.setVisible(true);
        } catch (Exception e) {
            messageLabel.setText(e.getMessage());
            messageLabel.setVisible(true);
        }
    }
}
