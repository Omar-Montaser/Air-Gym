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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import model.gym.members.Booking;
import model.gym.members.Session;

public class HomeController extends BaseController{
        static ObservableList<Booking> bookingList = FXCollections.observableArrayList();
    
        public void fillBookingsTable() {
            List<Booking> bookings = mainController.getAllMemberBookings();
            bookingList.clear();
    
            if (bookings == null || bookings.isEmpty()) {
                bookingsTable.setPlaceholder(new Label("No bookings found"));
                return;
            }
    
            bookingList.addAll(bookings);
    
            TableColumn<Booking, String> sessionColumn = new TableColumn<>("Session");
            sessionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                mainController.getSessionByID(cellData.getValue().getSessionID()).getSessionType()
            ));
    
            TableColumn<Booking, String> trainerColumn = new TableColumn<>("Trainer");
            trainerColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                mainController.getSessionByID(cellData.getValue().getSessionID()).getTrainerName()
            ));
    
            TableColumn<Booking, String> branchColumn = new TableColumn<>("Branch");
            branchColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                mainController.getSessionByID(cellData.getValue().getSessionID()).getBranchName()
            ));
    
            TableColumn<Booking, String> whenColumn = new TableColumn<>("When");
            whenColumn.setCellValueFactory(cellData -> {
                Timestamp dateTime = mainController.getSessionByID(cellData.getValue().getSessionID()).getDateTime();
                String formattedDate = (dateTime != null)
                    ? new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dateTime)
                    : "";
                return new SimpleStringProperty(formattedDate);
            });
    
            TableColumn<Booking, String> durationColumn = new TableColumn<>("Duration (mins)");
            durationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                String.valueOf(mainController.getSessionByID(cellData.getValue().getSessionID()).getDuration())
            ));
    
            TableColumn<Booking, String> statusColumn = new TableColumn<>("Status");
            statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                mainController.getSessionByID(cellData.getValue().getSessionID()).getStatus()
            ));
            sessionColumn.setStyle("-fx-alignment: CENTER; -fx-padding: 8px;");
            trainerColumn.setStyle("-fx-alignment: CENTER; -fx-padding: 8px;");
            branchColumn.setStyle("-fx-alignment: CENTER; -fx-padding: 8px;");
            whenColumn.setStyle("-fx-alignment: CENTER; -fx-padding: 8px;");
            durationColumn.setStyle("-fx-alignment: CENTER; -fx-padding: 8px;");
            statusColumn.setStyle("-fx-alignment: CENTER; -fx-padding: 8px;");
    
            bookingsTable.getColumns().setAll(
                sessionColumn, trainerColumn, branchColumn, whenColumn, durationColumn, statusColumn
            );
    
            bookingsTable.setItems(bookingList);
    }
    
    @FXML
    private void handleBookSession(){
        mainController.switchScene(Screen.BOOKING);
    }
           
    @FXML
    private void handleCancelSession() {
    Booking selectedBooking = bookingsTable.getSelectionModel().getSelectedItem();
    if (selectedBooking != null) {
        Session session = mainController.getSessionByID(selectedBooking.getSessionID());
            mainController.cancelBooking(selectedBooking.getBookingID());
            System.out.println("its NOT null");
            fillBookingsTable();
            return;
        }
    System.out.println("its null");
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
}
