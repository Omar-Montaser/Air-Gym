package core;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main {
    private Scene dashboardScene;
    // private DashboardController dashboardController;


    private Stage stage;
    private Screen currentScreen;

    public Main(Stage stage){
        this.stage=stage;
        stage.setScene(dashboardScene);
        stage.setTitle("Air-Gym");
        stage.show();
    
    }
}
