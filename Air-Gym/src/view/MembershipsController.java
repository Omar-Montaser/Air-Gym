package view;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.gym.members.MembershipType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class MembershipsController extends BaseController{
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private HBox menuHBox;


    public void populateMembershipTypes(){
        List<MembershipType> membershipTypes = mainController.getAllMembershipTypes();
        
    }
    public VBox createMembershipTypeBox(MembershipType membershipType){
        VBox membershipBox = new VBox();
        membershipBox.getStyleClass().addAll("membership-box", membershipType.getName().toLowerCase() + "-membership");
        membershipBox.setPrefHeight(200.0);
        membershipBox.setPrefWidth(254.0);
        
        // Title label
        Label titleLabel = new Label(membershipType.getName());
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        titleLabel.setPrefHeight(24.0);
        titleLabel.setPrefWidth(207.0);
        titleLabel.setStyle("-fx-font-size: 30;");
        titleLabel.getStyleClass().add("membership-title");
        
        // Description label
        Label descriptionLabel = new Label(membershipType.getDescription());
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setPrefHeight(50.0);
        descriptionLabel.setPrefWidth(221.0);
        
        // Group Sessions HBox
        HBox groupSessionsHBox = new HBox();
        groupSessionsHBox.setPrefHeight(13.0);
        groupSessionsHBox.setPrefWidth(201.0);
        
        Label groupSessionsLabel = new Label("Group Sessions");
        groupSessionsLabel.setPrefHeight(15.0);
        groupSessionsLabel.setPrefWidth(229.0);
        groupSessionsLabel.setStyle("-fx-text-fill: white;");
        
        Label groupSessionsValueLabel = new Label("Unlimited");
        groupSessionsValueLabel.setPrefHeight(39.0);
        groupSessionsValueLabel.setPrefWidth(118.0);
        groupSessionsValueLabel.setStyle("-fx-text-fill: white;");
        
        groupSessionsHBox.getChildren().addAll(groupSessionsLabel, groupSessionsValueLabel);
        
        // PT HBox
        HBox ptHBox = new HBox();
        ptHBox.setPrefHeight(17.0);
        ptHBox.setPrefWidth(201.0);
        
        Label ptLabel = new Label("PT");
        ptLabel.setPrefHeight(15.0);
        ptLabel.setPrefWidth(352.0);
        ptLabel.setStyle("-fx-text-fill: white;");
        
        Label ptValueLabel = new Label(membershipType.isPrivateTrainer() ? "Yes" : "No");
        ptValueLabel.setPrefHeight(38.0);
        ptValueLabel.setPrefWidth(172.0);
        ptValueLabel.setStyle("-fx-text-fill: white;");
        
        ptHBox.getChildren().addAll(ptLabel, ptValueLabel);
        
        // Freeze Duration HBox
        HBox freezeHBox = new HBox();
        freezeHBox.setPrefHeight(17.0);
        freezeHBox.setPrefWidth(201.0);
        
        Label freezeLabel = new Label("Freeze Duration");
        freezeLabel.setPrefHeight(31.0);
        freezeLabel.setPrefWidth(288.0);
        freezeLabel.setStyle("-fx-text-fill: white;");
        
        Label freezeValueLabel = new Label(membershipType.getFreezeDuration() + " days/month");
        freezeValueLabel.setPrefHeight(22.0);
        freezeValueLabel.setPrefWidth(222.0);
        freezeValueLabel.setStyle("-fx-text-fill: white;");
        
        freezeHBox.getChildren().addAll(freezeLabel, freezeValueLabel);
        
        // InBody HBox
        HBox inBodyHBox = new HBox();
        inBodyHBox.setPrefHeight(17.0);
        inBodyHBox.setPrefWidth(201.0);
        
        Label inBodyLabel = new Label("InBody");
        inBodyLabel.setPrefHeight(29.0);
        inBodyLabel.setPrefWidth(290.0);
        inBodyLabel.setStyle("-fx-text-fill: white;");
        
        Label inBodyValueLabel = new Label(membershipType.getInBody() > 0 ? membershipType.getInBody() + "/month" : "None");
        inBodyValueLabel.setPrefHeight(23.0);
        inBodyValueLabel.setPrefWidth(134.0);
        inBodyValueLabel.setStyle("-fx-text-fill: white;");
        
        inBodyHBox.getChildren().addAll(inBodyLabel, inBodyValueLabel);
        
        // Total Price HBox
        HBox totalHBox = new HBox();
        totalHBox.setAlignment(Pos.CENTER_RIGHT);
        totalHBox.setPrefHeight(47.0);
        totalHBox.setPrefWidth(193.0);
        
        Label totalLabel = new Label("Total");
        totalLabel.setPrefHeight(24.0);
        totalLabel.setPrefWidth(146.0);
        totalLabel.setStyle("-fx-font-size: 20;");
        
        Label priceLabel = new Label("$" + String.format("%.2f", membershipType.getMonthlyPrice()) + "/month");
        priceLabel.setPrefHeight(24.0);
        priceLabel.setPrefWidth(180.0);
        priceLabel.setStyle("-fx-font-size: 20;");
        
        totalHBox.getChildren().addAll(totalLabel, priceLabel);
        
        // Button
        Button getMembershipButton = new Button("Get Membership");
        getMembershipButton.setId("bookSessionBtn");
        getMembershipButton.setPrefHeight(45.0);
        getMembershipButton.setPrefWidth(170.0);
        getMembershipButton.getStylesheets().add("@assets/css/memberH1.css");
        VBox.setMargin(getMembershipButton, new javafx.geometry.Insets(0, 0, 0, 25.0));
        
        // Add all components to the VBox
        membershipBox.getChildren().addAll(
            titleLabel,
            descriptionLabel,
            groupSessionsHBox,
            ptHBox,
            freezeHBox,
            inBodyHBox,
            totalHBox,
            getMembershipButton
        );
        
        return membershipBox;
    }

    public void modifyScreen(){
        if(mainController.isGuest()){
            homeButton.setVisible(false);
            profileButton.setVisible(false);
            logoutButton.setVisible(false);
            menuHBox.setAlignment(Pos.TOP_RIGHT);
            System.out.println("BUTTONS SHOULDNT BE VISIBLE");
        }
        else{
            homeButton.setVisible(true);
            profileButton.setVisible(true);
            logoutButton.setVisible(true);
            menuHBox.setAlignment(Pos.TOP_LEFT);
        }
    }
}
