package controller;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import view.MemberViewController;
import view.MembershipsController;
import view.ProfileController;
import dao.*;
import db.SqlServerConnect;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import view.CheckoutController;
import view.DashboardController;
import view.HomeController;
import view.LoginController;
import javafx.stage.Stage;
import model.accounts.*;
import model.gym.Branch;
import model.gym.members.MembershipType;

public class MainController {
    public MainController(Stage stage){
        try{
            Connection conn = SqlServerConnect.getConnection();
            memberDAO = new MemberDAO(conn);
            userDAO = new UserDAO(conn);
            membershipTypeDao = new MembershipTypeDAO(conn);
            branchDAO = new BranchDAO(conn);
            trainerDAO = new TrainerDAO(conn);
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
        homeController =(HomeController) homeLoader.getController();
        homeController.setMain(this);

        // FXMLLoader bookingLoader = new FXMLLoader(getClass().getResource("../view/Booking.fxml"));
        // bookingScene = new Scene(bookingLoader.load());
        // bookingController =(BookingController) bookingLoader.getController();
        // bookingController.setMain(this);

        FXMLLoader membershipsLoader = new FXMLLoader(getClass().getResource("../view/Memberships.fxml"));
        membershipsScene = new Scene(membershipsLoader.load());
        membershipsController =(MembershipsController) membershipsLoader.getController();
        membershipsController.setMain(this);

        FXMLLoader checkoutLoader = new FXMLLoader(getClass().getResource("../view/Checkout.fxml"));
        checkoutScene = new Scene(checkoutLoader.load());
        checkoutController =(CheckoutController) checkoutLoader.getController();
        checkoutController.setMain(this);

        FXMLLoader profileLoader = new FXMLLoader(getClass().getResource("../view/ProfilePage.fxml"));
        profileScene = new Scene(profileLoader.load());
        profileController =(ProfileController) profileLoader.getController();
        profileController.setMain(this);

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
            case BOOKING:
                stage.setScene(bookingScene);
                break;
            case MEMBERSHIPS:
                membershipsController.modifyScreen();
                membershipsController.populateMembershipTypes();
                stage.setScene(membershipsScene);
                break;
            case CHECKOUT:
                checkoutController.modifyScreen();
                stage.setScene(checkoutScene);
                break;
            case PROFILE:
                profileController.fillDetails(currentMember);
                stage.setScene(profileScene);
                break;
            // case CONTACT_US:
            //     stage.setScene(contactUsScene);
            //     break;
            default: break;
        }
    } 
    public boolean login(String phoneNumber, String password) throws Exception {
        isGuest = false; isAdmin = false;
        if (userDAO.getUserByPhoneNumber(phoneNumber)==null) return false;
        else{
            String role = userDAO.validateLogin(phoneNumber, password);
            System.out.println("Role: " + role);
            currentUser = userDAO.getUserByPhoneNumber(phoneNumber);
            System.out.println("Current User: " + currentUser.getUserId());
            System.out.println("Calling getMemberByPhoneNumber");
            if(role.equals("Member")) {
                System.out.println("Inside Member role check");
                currentMember = memberDAO.getMemberByPhoneNumber(phoneNumber);
                System.out.println("After getMemberByPhoneNumber call");
            }
            
            if (currentMember == null) {
                System.out.println("NULL");
            } else {
                System.out.println("Current Member: " + currentMember.getFirstName());
            }
            return true;
        }
    }
    public void logout(){
        currentMember = null;
        currentUser = null;
        loginController.clearFields();
        switchScene(Screen.LOGIN);
    }
    public void guestCheckout(Double paymentAmount, String firstName, String lastName, String password,
    String phoneNumber, String gender, Date dateOfBirth,int duration,String branchName){
        Branch branch = branchDAO.getBranchByName(branchName);
        Member member = new Member(
            userDAO.getMaxUserId()+1,
            firstName,
            lastName,
            password,
            phoneNumber,
            gender,
            dateOfBirth,
            selectedMembership.getMembershipTypeID(),
            branch.getBranchID()
        );
        memberDAO.createMember(member,duration,paymentAmount);
        currentMember = memberDAO.getMemberById(member.getUserId());
        currentUser = userDAO.getUserByPhoneNumber(phoneNumber);
        switchScene(Screen.PROFILE);
    }
    public void extendMembership(String duration){
        memberDAO.extendSubscription(currentMember.getUserId(), Integer.parseInt(duration), "CreditCard");
    }
    public void freezeSubscription(String duration){
        memberDAO.freezeSubscription(currentMember.getUserId(), Integer.parseInt(duration));
    }
    public void cancelSubscription(){
        memberDAO.cancelSubscription(currentMember.getUserId());
    }
    
    
    
    public Member getCurrentMember(){
        return currentMember;
    }
    public List<MembershipType> getAllMembershipTypes(){
        return membershipTypeDao.getAllMembershipTypes();
    }
    public String getMembershipName(int membershipId){
        return membershipTypeDao.getMembershipTypeName(membershipId);
    }
    public Trainer getTrainerByID(int trainerId){
        return trainerDAO.getTrainerById(trainerId);
    }
    public Branch getBranchByID(int branchId){
        return branchDAO.getBranchById(branchId);
    }
    public List<Branch> getAllBranches(){
        return branchDAO.getBranches();
    }
    public boolean isGuest(){
        return isGuest;
    }
    public void setIsGuest(boolean isGuest){
        this.isGuest = isGuest;
    }
    public boolean isAdmin(){
        return isAdmin;
    }
    public void setSelectedMembership(MembershipType membershipType){
        this.selectedMembership = membershipType;
    }
    public MembershipType getSelectedMembership(){  
        return selectedMembership;
    }

    protected boolean isGuest;
    protected boolean isAdmin;
    private User currentUser;
    private Member currentMember;
    private MembershipType selectedMembership;

    private Stage stage;
    private Screen currentScreen;

    private MemberDAO memberDAO;
    private UserDAO userDAO;
    private MembershipTypeDAO membershipTypeDao;
    private BranchDAO branchDAO;
    private TrainerDAO trainerDAO;


    private Scene dashboardScene;
    private DashboardController dashboardController;

    private Scene loginScene;
    private LoginController loginController;

    private Scene homeScene;
    private HomeController homeController;

    private Scene bookingScene;
    // private BookingController bookingController;

    private Scene membershipsScene;
    private MembershipsController membershipsController;

    private Scene checkoutScene;
    private CheckoutController checkoutController;

    private Scene profileScene;
    private ProfileController profileController;
}
