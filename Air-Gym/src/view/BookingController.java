package view;

import java.sql.SQLException;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.gym.members.Session;

public class BookingController extends BaseController{

    public void loadSessions(){
        messageLabel.setVisible(false);
        
        // Yoga
        Session yogaSession = mainController.getEarliestSessionByType("Yoga");
        if(yogaSession != null) {
            yogaTrainer.setText(yogaSession.getTrainerName());
            yogaBranch.setText(yogaSession.getBranchName());
            yogaDate.setText(yogaSession.getDateTime().toLocalDateTime()
                    .format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
            yogaDuration.setText(yogaSession.getDuration() + " minutes");
            yogaBookButton.setOnAction(e -> {
                try {
                    mainController.bookSession(yogaSession.getSessionID());
                    mainController.switchScene(Screen.HOME);
                } catch (SQLException ex) {
                    messageLabel.setVisible(true);
                    messageLabel.setText(ex.getMessage());
                }
            });
        } else {
            yogaTrainer.setText("None");
            yogaBranch.setText("None");
            yogaDate.setText("None");
            yogaDuration.setText("None");
            yogaBookButton.setDisable(true);
        }
        
        // Pilates
        Session pilatesSession = mainController.getEarliestSessionByType("Pilates");
        if(pilatesSession != null) {
            pilatesTrainer.setText(pilatesSession.getTrainerName());
            pilatesBranch.setText(pilatesSession.getBranchName());
            pilatesDate.setText(pilatesSession.getDateTime().toLocalDateTime()
                    .format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
            pilatesDuration.setText(pilatesSession.getDuration() + " minutes");
            pilatesBookButton.setOnAction(e -> {
                try {
                    mainController.bookSession(pilatesSession.getSessionID());
                    mainController.switchScene(Screen.HOME);
                } catch (SQLException ex) {
                    messageLabel.setVisible(true);
                    messageLabel.setText(ex.getMessage());
                }
            });
        } else {
            pilatesTrainer.setText("None");
            pilatesBranch.setText("None");
            pilatesDate.setText("None");
            pilatesDuration.setText("None");
            pilatesBookButton.setDisable(true);
        }
        
        // Zumba
        Session zumbaSession = mainController.getEarliestSessionByType("Zumba");
        if(zumbaSession != null) {
            zumbaTrainer.setText(zumbaSession.getTrainerName());
            zumbaBranch.setText(zumbaSession.getBranchName());
            zumbaDate.setText(zumbaSession.getDateTime().toLocalDateTime()
                    .format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
            zumbaDuration.setText(zumbaSession.getDuration() + " minutes");
            zumbaBookButton.setOnAction(e -> {
                try {
                    mainController.bookSession(zumbaSession.getSessionID());
                    mainController.switchScene(Screen.HOME);
                } catch (SQLException ex) {
                    messageLabel.setVisible(true);
                    messageLabel.setText(ex.getMessage());
                }
            });
        } else {
            zumbaTrainer.setText("None");
            zumbaBranch.setText("None");
            zumbaDate.setText("None");
            zumbaDuration.setText("None");
            zumbaBookButton.setDisable(true);
        }
        
        // Boxing
        Session boxingSession = mainController.getEarliestSessionByType("Boxing");
        if(boxingSession != null) {
            boxingTrainer.setText(boxingSession.getTrainerName());
            boxingBranch.setText(boxingSession.getBranchName());
            boxingDate.setText(boxingSession.getDateTime().toLocalDateTime()
                    .format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
            boxingDuration.setText(boxingSession.getDuration() + " minutes");
            boxingBookButton.setOnAction(e -> {
                try {
                    mainController.bookSession(boxingSession.getSessionID());
                    mainController.switchScene(Screen.HOME);
                } catch (SQLException ex) {
                    messageLabel.setVisible(true);
                    messageLabel.setText(ex.getMessage());
                }
            });
        } else {
            boxingTrainer.setText("None");
            boxingBranch.setText("None");
            boxingDate.setText("None");
            boxingDuration.setText("None");
            boxingBookButton.setDisable(true);
        }
        
        // HIIT
        Session hiitSession = mainController.getEarliestSessionByType("HIIT");
        if(hiitSession != null) {
            hiitTrainer.setText(hiitSession.getTrainerName());
            hiitBranch.setText(hiitSession.getBranchName());
            hiitDate.setText(hiitSession.getDateTime().toLocalDateTime()
                    .format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
            hiitDuration.setText(hiitSession.getDuration() + " minutes");
            hiitBookButton.setOnAction(e -> {
                try {
                    mainController.bookSession(hiitSession.getSessionID());
                    mainController.switchScene(Screen.HOME);
                } catch (SQLException ex) {
                    messageLabel.setVisible(true);
                    messageLabel.setText(ex.getMessage());
                }
            });
        } else {
            hiitTrainer.setText("None");
            hiitBranch.setText("None");
            hiitDate.setText("None");
            hiitDuration.setText("None");
            hiitBookButton.setDisable(true);
        }
        
        // Cycling
        Session cyclingSession = mainController.getEarliestSessionByType("Cycling");
        if(cyclingSession != null) {
            cyclingTrainer.setText(cyclingSession.getTrainerName());
            cyclingBranch.setText(cyclingSession.getBranchName());
            cyclingDate.setText(cyclingSession.getDateTime().toLocalDateTime()
                    .format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
            cyclingDuration.setText(cyclingSession.getDuration() + " minutes");
            cyclingBookButton.setOnAction(e -> {
                try {
                    mainController.bookSession(cyclingSession.getSessionID());
                    mainController.switchScene(Screen.HOME);
                } catch (SQLException ex) {
                    messageLabel.setVisible(true);
                    messageLabel.setText(ex.getMessage());
                }
            });
        } else {
            cyclingTrainer.setText("None");
            cyclingBranch.setText("None");
            cyclingDate.setText("None");
            cyclingDuration.setText("None");
            cyclingBookButton.setDisable(true);
        }
    }
    @FXML
    private void handleMemberships(){
        mainController.switchScene(Screen.MEMBERSHIPS);
    }
    @FXML
    private void handleProfile(){
        mainController.switchScene(Screen.PROFILE);
    }
    @FXML
    private void handleContactUs(){
        mainController.switchScene(Screen.CONTACT_US);
    }
    @FXML
    private void cancel(){
        mainController.switchScene(Screen.HOME);
    }
    @FXML
    private Label yogaTrainer;
    @FXML
    private Label yogaBranch;
    @FXML
    private Label yogaDate;
    @FXML
    private Label yogaDuration;
    @FXML
    private Button yogaBookButton;
    
    @FXML
    private Label pilatesTrainer;
    @FXML
    private Label pilatesBranch;
    @FXML
    private Label pilatesDate;
    @FXML
    private Label pilatesDuration;
    @FXML
    private Button pilatesBookButton;

    @FXML
    private Label cyclingTrainer;
    @FXML
    private Label cyclingBranch;
    @FXML
    private Label cyclingDate;
    @FXML
    private Label cyclingDuration;
    @FXML
    private Button cyclingBookButton;

    @FXML
    private Label boxingTrainer;
    @FXML
    private Label boxingBranch;
    @FXML
    private Label boxingDate;
    @FXML
    private Label boxingDuration;
    @FXML
    private Button boxingBookButton;

    @FXML
    private Label hiitTrainer;
    @FXML
    private Label hiitBranch;
    @FXML
    private Label hiitDate;
    @FXML
    private Label hiitDuration;
    @FXML
    private Button hiitBookButton;

    @FXML
    private Label zumbaTrainer;
    @FXML
    private Label zumbaBranch;
    @FXML
    private Label zumbaDate;
    @FXML
    private Label zumbaDuration;
    @FXML
    private Button zumbaBookButton;
    @FXML
    private Label messageLabel;
}
