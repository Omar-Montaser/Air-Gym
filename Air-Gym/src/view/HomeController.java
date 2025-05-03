package view;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import controller.Screen;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.gym.members.Booking;
import model.gym.members.Session;

public class HomeController extends BaseController{
        static ObservableList<Booking> bookingList = FXCollections.observableArrayList();

        public void fillBookingsTable() {
            try {
                Session.setCellValueFactory(new PropertyValueFactory<>("sessionName"));
                Trainer.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
                Branch.setCellValueFactory(new PropertyValueFactory<>("branchName"));
                When.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
                Duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
                Status.setCellValueFactory(new PropertyValueFactory<>("sessionStatus"));

                String leftPadding = "-fx-alignment: CENTER;";
                Session.setStyle(leftPadding);
                Trainer.setStyle(leftPadding);
                Branch.setStyle(leftPadding);
                When.setStyle(leftPadding);
                Duration.setStyle(leftPadding);
                Status.setStyle(leftPadding);

            } catch (Exception e) {
                e.printStackTrace();
            }
            List<Booking> bookings = mainController.getAllMemberBookings();
            bookingList.clear();

            if (bookings == null || bookings.isEmpty()) {
                bookingsTable.setPlaceholder(new Label("No bookings found"));
            } else {
                bookingList.addAll(bookings);
                System.out.println("Added " + bookings.size() + " bookings to the table");
            }

            bookingsTable.setItems(bookingList);
    }
    
    @FXML
    private void handleBookSession(){
        mainController.switchScene(Screen.BOOKING);
    }
           
    @FXML
    private void handleCancelSession() {
    Booking selectedBooking = bookingsTable.getSelectionModel().getSelectedItem();
            mainController.cancelBooking(selectedBooking.getBookingID());
            fillBookingsTable();
            return;
    }
    @FXML 
    private void handleProfile(){
        mainController.switchScene(Screen.PROFILE);
    }
    @FXML
    private void handleMemberships(){
        mainController.switchScene(Screen.MEMBERSHIPS);
    }
    @FXML
    private void handleContactUs(){
        mainController.switchScene(Screen.CONTACT_US);
    }
    @FXML
    private void handleLogout(){
        mainController.logout();
    }

    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private Button membershipsButton;
    @FXML
    private Button contactUsButton;
    @FXML
    private Button boookSessionButton;
    @FXML
    private Button cancelSessionButton;
    @FXML
    private TableView<Booking> bookingsTable;
    @FXML
    private TableColumn<Booking, String> Session;
    @FXML
    private TableColumn<Booking, String> Trainer;
    @FXML
    private TableColumn<Booking, String> Branch;
    @FXML
    private TableColumn<Booking, Timestamp> When;
    @FXML
    private TableColumn<Booking,Integer> Duration;
    @FXML
    private TableColumn<Booking, String> Status;
}
