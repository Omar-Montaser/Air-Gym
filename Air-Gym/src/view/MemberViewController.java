package view;

import java.sql.Date;
import java.util.List;

import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.accounts.Member;
import model.accounts.Trainer;
import model.gym.Branch;
import model.gym.members.Booking;
import model.gym.members.Session;

public class MemberViewController extends BaseController {
    // Table columns for member data
    @FXML
    private TableColumn<Member, Integer> idColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> numberColumn;
    @FXML
    private TableColumn<Member, Integer> ageColumn;
    @FXML
    private TableColumn<Member, String> genderColumn;
    @FXML
    private TableColumn<Member, Branch> branchColumn;
    @FXML
    private TableColumn<Member, String> membershipColumn;
    @FXML
    private TableColumn<Member, Trainer> trainerColumn;
    @FXML
    private TableColumn<Member, String> statusColumn;
    @FXML
    private TableColumn<Member, Date> subscriptionEndColumn;
    @FXML
    private TableColumn<Member, Date> freezeEndColumn;
    @FXML
    private TableColumn<Member, Integer> sessionsColumn;
    @FXML
    private TableColumn<Member, Integer> freezesColumn;

 
        static ObservableList<Member> memberList = FXCollections.observableArrayList();

        public void fillBookingsTable() {
            try {
                idColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
                nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
                numberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
                ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
                genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
                branchColumn.setCellValueFactory(new PropertyValueFactory<>("branchName"));
                membershipColumn.setCellValueFactory(new PropertyValueFactory<>("membershipName"));
                trainerColumn.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
                statusColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionStatus"));
                subscriptionEndColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionEndDate"));
                freezeEndColumn.setCellValueFactory(new PropertyValueFactory<>("freezeEndDate"));
                sessionsColumn.setCellValueFactory(new PropertyValueFactory<>("sessionsAvailable"));
                freezesColumn.setCellValueFactory(new PropertyValueFactory<>("freezeAvailable"));

                String leftPadding = "-fx-alignment: CENTER;";
                idColumn.setStyle(leftPadding);
                nameColumn.setStyle(leftPadding);
                numberColumn.setStyle(leftPadding);
                ageColumn.setStyle(leftPadding);
                genderColumn.setStyle(leftPadding);
                branchColumn.setStyle(leftPadding);
                membershipColumn.setStyle(leftPadding);
                trainerColumn.setStyle(leftPadding);
                statusColumn.setStyle(leftPadding);
                subscriptionEndColumn.setStyle(leftPadding);
                freezeEndColumn.setStyle(leftPadding);
                sessionsColumn.setStyle(leftPadding);
                freezesColumn.setStyle(leftPadding);

            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Member> members = mainController.getAllMemberDetails();
            memberList.clear();

            if (members == null || members.isEmpty()) {
                bookingsTable.setPlaceholder(new Label("No bookings found"));
            } else {
                bookingList.addAll(bookings);
                System.out.println("Added " + bookings.size() + " bookings to the table");
            }

            bookingsTable.setItems(bookingList);
    }
    
    
}
