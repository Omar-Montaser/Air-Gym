package controller;
import java.lang.reflect.Member;
import java.sql.Connection;

import view.MemberViewController;
import dao.MemberDAO;
import db.SqlServerConnect;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import view.DashboardController;
import view.LoginController;
import javafx.stage.Stage;

public class MainController {

    private Stage stage;
    private Screen currentScreen;

    private MemberDAO memberDAO;

    protected boolean isGuest;

    public MainController(Stage stage){
        try{
            Connection conn = SqlServerConnect.getConnection();
            memberDAO = new MemberDAO(conn);
            initializeScenes();
            this.stage=stage;
            stage.setScene(loginScene);
            stage.setResizable(true);
            stage.setTitle("Air Gym");
            Image logo = new Image(getClass().getResourceAsStream("../view/assets/images/LOGO.png"));
            stage.getIcons().add(logo);
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void initializeScenes() throws Exception{
        // FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("../view/Dashboard.fxml"));
        // dashboardScene = new Scene(dashboardLoader.load());
        // // dashboardController =(DashboardController) dashboardLoader.getController();
        // // dashboardController.setMain(this);

        // FXMLLoader memberViewLoader= new FXMLLoader(getClass().getResource("../view/MemberView.fxml"));
        // memberViewScene = new Scene(memberViewLoader.load());
        // // memberViewController = (MemberViewController) memberViewLoader.getController();
        // // memberViewController.setMain(this);

        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
        loginScene = new Scene(loginLoader.load());
        loginController =(LoginController) loginLoader.getController();
        loginController.setMain(this);

        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("../view/MemberHome.fxml"));
        homeScene = new Scene(homeLoader.load());
        // // homeController =(HomeController) homeLoader.getController();
        // // homeController.setMain(this);

    }
    public void switchScene(Screen nextScreen){
        currentScreen = nextScreen;
        switch(currentScreen){
            case LOGIN:
                stage.setScene(loginScene);
                break;
            case HOME:
                stage.setScene(homeScene);
                break;  
            // case BOOKING:
            //     stage.setScene(bookingScene);
            //     break;
            // case MEMBERSHIP_VIEW:
            //     stage.setScene(membershipViewScene);
            //     break;
            // case CHECKOUT:
            //     stage.setScene(checkoutScene);
            //     break;
            // case PROFILE:
            //     stage.setScene(profileScene);
            //     break;
            // case CONTACT_US:
            //     stage.setScene(contactUsScene);
            //     break;
            default: break;
        }
    } 
    public void login()

    private Scene dashboardScene;
    private DashboardController dashboardController;

    private Scene loginScene;
    private LoginController loginController;

    private Scene homeScene;
    // private HomeController homeController;
}
