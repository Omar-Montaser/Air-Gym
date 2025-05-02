package view;

import java.util.List;

import controller.Screen;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import model.gym.members.Booking;
import model.gym.members.Session;

public class HomeController extends BaseController{

    public void fillBookingsTable(){
        List<Booking> bookings = mainController.getAllMemberBookings();
        if (bookings == null || bookings.isEmpty()) {
            bookingsTable.setPlaceholder(new javafx.scene.control.Label("No bookings found"));
            return;
        }
        bookingsTable.getItems().clear();

        javafx.scene.control.TableColumn<Booking, String> sessionColumn = new javafx.scene.control.TableColumn<>("Session");
        sessionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(mainController.getSessionByID(cellData.getValue().getSessionID()).getSessionType()));
        
        javafx.scene.control.TableColumn<Booking, String> trainerColumn = new javafx.scene.control.TableColumn<>("Trainer");
        trainerColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(mainController.getSessionByID(cellData.getValue().getSessionID()).getTrainerName()));
        
        javafx.scene.control.TableColumn<Booking, String> branchColumn = new javafx.scene.control.TableColumn<>("Branch");
        branchColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(mainController.getSessionByID(cellData.getValue().getSessionID()).getBranchName()));
        
        javafx.scene.control.TableColumn<Booking, String> whenColumn = new javafx.scene.control.TableColumn<>("When");
        whenColumn.setCellValueFactory(cellData -> {
            java.sql.Timestamp dateTime = mainController.getSessionByID(cellData.getValue().getSessionID()).getDateTime();
            String formattedDate = dateTime != null ? 
                new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(dateTime) : "";
            return new javafx.beans.property.SimpleStringProperty(formattedDate);
        });

        javafx.scene.control.TableColumn<Booking, String> durationColumn = new javafx.scene.control.TableColumn<>("Duration (mins)");  
        durationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(mainController.getSessionByID(cellData.getValue().getSessionID()).getDuration())));

        javafx.scene.control.TableColumn<Booking, String> statusColumn = new javafx.scene.control.TableColumn<>("Status");
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(mainController.getSessionByID(cellData.getValue().getSessionID()).getStatus()));
    bookingsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    bookingsTable.getColumns().setAll(sessionColumn, trainerColumn, branchColumn, whenColumn, durationColumn, statusColumn);
    bookingsTable.setItems(FXCollections.observableArrayList(bookings)); 
    sessionColumn.setCellFactory(tc -> {
    TableCell<Booking, String> cell = new TableCell<>() {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(empty ? null : item);
            setTooltip(empty ? null : new Tooltip(item));
        }
    };
    cell.setStyle("-fx-alignment: CENTER-LEFT; -fx-padding: 8px;");
    return cell;
});


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
