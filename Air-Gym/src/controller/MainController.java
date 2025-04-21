package controller;
import java.lang.reflect.Member;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainController {

    private Stage stage;
    private Screen currentScreen;

    public MainController(Stage stage){
        try{
            initializeScenes();
            this.stage=stage;
            stage.setScene(dashboardScene);
            stage.setResizable(true);
            stage.setTitle("Air Gym");
            Image logo = new Image(getClass().getResourceAsStream("../assets/LOGO.png"));
            stage.getIcons().add(logo);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void initializeScenes() throws Exception{
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("../view/Dashboard.fxml"));
        dashboardScene = new Scene(dashboardLoader.load());
        // dashboardController =(DashboardController) dashboardLoader.getController();
        // dashboardController.setMain(this);

        FXMLLoader memberViewLoader= new FXMLLoader(getClass().getResource("../view/MemberView.fxml"));
        memberViewScene = new Scene(memberViewLoader.load());
        // memberViewController = (MemberViewController) memberViewLoader.getController();
        // memberViewController.setMain(this);

    }

    private Scene dashboardScene;
    private DashboardController dashboardController;
    
    private Scene memberViewScene;
    private MemberViewController memberViewController;
}
