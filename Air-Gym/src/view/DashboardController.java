package view;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;


public class DashboardController extends BaseController implements Initializable {
    @FXML
    private PieChart pcUsers;

    public void initialize(URL url, ResourceBundle rb) {
        pcUsers.setData(FXCollections.observableArrayList(
                new PieChart.Data("Members", 54),
                new PieChart.Data("Trainers", 12),
                new PieChart.Data("Admins", 7)
        ));
    }
}
