package view;

import controller.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.gym.members.Session;

public class BookingController extends BaseController{

    public void loadSessions(){
        Session yogaSession = mainController.getEarliestSessionByType("Yoga");
        yogaTrainer.setText(yogaSession.getTrainerName());
        yogaBranch.setText(yogaSession.getBranchName());
        yogaDate.setText(yogaSession.getDateTime().toString());
        yogaDate.setText(yogaSession.getDateTime().toLocalDateTime().format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
        yogaDuration.setText(yogaSession.getDuration() + " minutes");
        yogaBookButton.setOnAction(e -> {
            mainController.bookSession(yogaSession.getSessionID());
            mainController.switchScene(Screen.HOME);
        });

        Session pilatesSession = mainController.getEarliestSessionByType("Pilates");
        pilatesTrainer.setText(pilatesSession.getTrainerName());
        pilatesBranch.setText(pilatesSession.getBranchName());
        pilatesDate.setText(pilatesSession.getDateTime().toLocalDateTime().format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
        pilatesDuration.setText(pilatesSession.getDuration() + " minutes");
        pilatesBookButton.setOnAction(e -> {
            mainController.bookSession(pilatesSession.getSessionID());
            mainController.switchScene(Screen.HOME);
        });
        
        Session zumbaSession = mainController.getEarliestSessionByType("Zumba");
        zumbaTrainer.setText(zumbaSession.getTrainerName());
        zumbaBranch.setText(zumbaSession.getBranchName());
        zumbaDate.setText(zumbaSession.getDateTime().toLocalDateTime().format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
        zumbaDuration.setText(zumbaSession.getDuration() + " minutes");
        zumbaBookButton.setOnAction(e -> {
            mainController.bookSession(zumbaSession.getSessionID());
            mainController.switchScene(Screen.HOME);
        });

        Session boxingSession = mainController.getEarliestSessionByType("Boxing");
        boxingTrainer.setText(boxingSession.getTrainerName());
        boxingBranch.setText(boxingSession.getBranchName());
        boxingDate.setText(boxingSession.getDateTime().toLocalDateTime().format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
        boxingDuration.setText(boxingSession.getDuration() + " minutes");
        boxingBookButton.setOnAction(e -> {
            mainController.bookSession(boxingSession.getSessionID());
            mainController.switchScene(Screen.HOME);
        });

        Session hiitSession = mainController.getEarliestSessionByType("HIIT");
        hiitTrainer.setText(hiitSession.getTrainerName());
        hiitBranch.setText(hiitSession.getBranchName());
        hiitDate.setText(hiitSession.getDateTime().toLocalDateTime().format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
        hiitDuration.setText(hiitSession.getDuration() + " minutes");
        hiitBookButton.setOnAction(e -> {
            mainController.bookSession(hiitSession.getSessionID());
            mainController.switchScene(Screen.HOME);
        });

        Session cyclingSession = mainController.getEarliestSessionByType("Cycling");
        cyclingTrainer.setText(cyclingSession.getTrainerName());
        cyclingBranch.setText(cyclingSession.getBranchName());
        cyclingDate.setText(cyclingSession.getDateTime().toLocalDateTime().format(java.time.format.DateTimeFormatter.ofPattern("EEE dd MMM, hh:mm a")));
        cyclingDuration.setText(cyclingSession.getDuration() + " minutes");
        cyclingBookButton.setOnAction(e -> {
            mainController.bookSession(cyclingSession.getSessionID());
            mainController.switchScene(Screen.HOME);
        });
    }

    @FXML
    private void handleLogout(){
        mainController.logout();
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

}
