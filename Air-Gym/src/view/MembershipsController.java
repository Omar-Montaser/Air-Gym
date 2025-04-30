package view;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import model.gym.members.MembershipType;

import java.util.List;

import controller.Screen;


public class MembershipsController extends BaseController{
    public void populateMembershipTypes(){
        scrollPane.addEventFilter(ScrollEvent.SCROLL, event->{
            double deltaY = event.getDeltaY() * 3;
            scrollPane.setVvalue(scrollPane.getVvalue() - deltaY / scrollPane.getContent().getBoundsInLocal().getHeight());
            event.consume();
        });
        List<MembershipType> membershipTypes = mainController.getAllMembershipTypes();
        membershipContainer.getChildren().clear();
        for (MembershipType membershipType : membershipTypes)
            System.out.println(membershipType.getName());
        int totalMemberships = membershipTypes.size();
        int membershipsPerRow = 3;
        int rows = (int) Math.ceil((double) totalMemberships / membershipsPerRow);
        if (rows>1) membershipContainer.setPrefHeight(membershipContainer.getPrefHeight()*(rows));
        for (int row = 0; row < rows; row++) {
            HBox membershipHBox = new HBox();
            membershipHBox.setPrefHeight(335.0);
            membershipHBox.setPrefWidth(914.0);
            membershipHBox.setStyle("-fx-background-color: #e3e3e3;");
            membershipHBox.setPadding(new javafx.geometry.Insets(0, 20.0, 0, 20.0));

            int startIndex=row * membershipsPerRow;
            int endIndex=Math.min(startIndex+membershipsPerRow,totalMemberships);

            for (int i=startIndex; i<endIndex ;i++) {
                MembershipType membershipType=membershipTypes.get(i);
                VBox membershipBox=createMembershipTypeBox(membershipType);
                
                if (i<endIndex-1) HBox.setMargin(membershipBox, new javafx.geometry.Insets(0, 30.0, 0, 0));                
                membershipHBox.getChildren().add(membershipBox);
            }

            membershipContainer.getChildren().add(membershipHBox);
        }
        System.out.println("Membership types populated");
    }
    public VBox createMembershipTypeBox(MembershipType membershipType){
        VBox membershipBox = new VBox();
        membershipBox.getStyleClass().add("membership-box");
        membershipBox.getStyleClass().add("memberH1");
        membershipBox.setPrefHeight(200.0);
        membershipBox.setPrefWidth(254.0);
        
        Label titleLabel = new Label(membershipType.getName());
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
        titleLabel.setPrefHeight(24.0);
        titleLabel.setPrefWidth(207.0);
        titleLabel.setStyle("-fx-font-size: 30;");
        titleLabel.getStyleClass().add("membership-title");
        
        Label descriptionLabel = new Label(membershipType.getDescription());
        descriptionLabel.setAlignment(Pos.CENTER);
        descriptionLabel.setPrefHeight(65.0);
        descriptionLabel.setPrefWidth(221.0);
        
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
        
        HBox ptHBox = new HBox();
        ptHBox.setPrefHeight(17.0);
        ptHBox.setPrefWidth(201.0);
        
        Label ptLabel = new Label("PT");
        ptLabel.setPrefHeight(15.0);
        ptLabel.setPrefWidth(352.0);
        ptLabel.setStyle("-fx-text-fill: white;");
        
        Label ptValueLabel = new Label(membershipType.hasPrivateTrainer() ? "Yes" : "No");
        ptValueLabel.setPrefHeight(38.0);
        ptValueLabel.setPrefWidth(172.0);
        ptValueLabel.setStyle("-fx-text-fill: white;");
        
        ptHBox.getChildren().addAll(ptLabel, ptValueLabel);
        
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
        
        HBox inBodyHBox = new HBox();
        inBodyHBox.setPrefHeight(17.0);
        inBodyHBox.setPrefWidth(201.0);
        
        Label inBodyLabel = new Label("InBody");
        inBodyLabel.setPrefHeight(29.0);
        inBodyLabel.setPrefWidth(250.0);
        inBodyLabel.setStyle("-fx-text-fill: white;");
        
        Label inBodyValueLabel = new Label((membershipType.getInBody()>0 )?(membershipType.getInBody()>29?"Unlimited": membershipType.getInBody() + "/month") : "None");
        inBodyValueLabel.setPrefHeight(23.0);
        inBodyValueLabel.setPrefWidth(130.0);
        inBodyValueLabel.setStyle("-fx-text-fill: white;");
        
        inBodyHBox.getChildren().addAll(inBodyLabel, inBodyValueLabel);
        
        HBox totalHBox = new HBox();
        totalHBox.setAlignment(Pos.CENTER_RIGHT);
        totalHBox.setPrefHeight(47.0);
        totalHBox.setPrefWidth(193.0);
        
        Label totalLabel = new Label("Total");
        totalLabel.setPrefHeight(24.0);
        totalLabel.setPrefWidth(100.0);
        totalLabel.setStyle("-fx-font-size: 20;");
        
        Label priceLabel = new Label("$" + String.format("%.2f", membershipType.getMonthlyPrice()) + "/month");
        priceLabel.setPrefHeight(24.0);
        priceLabel.setPrefWidth(180.0);
        priceLabel.setStyle("-fx-font-size: 20;");
        
        totalHBox.getChildren().addAll(totalLabel, priceLabel);
        
        Button membershipButton; 
        if(mainController.isGuest())membershipButton = new Button("Get Membership");
        else membershipButton= new Button("Renew Membership");
        membershipButton.setPrefHeight(45.0);
        membershipButton.setPrefWidth(170.0);
        membershipButton.getStyleClass().add("membershipBtn");
        VBox.setMargin(membershipButton, new javafx.geometry.Insets(0, 0, 0, 25.0));

        membershipBox.getChildren().addAll(
            titleLabel,
            descriptionLabel,
            groupSessionsHBox,
            ptHBox,
            freezeHBox,
            inBodyHBox,
            totalHBox,
            membershipButton
        );

        String colorStyle = "-fx-background-color: " + membershipType.getColorHex() + ";";
        membershipBox.setStyle(colorStyle);
        
        membershipButton.setOnAction(event -> {
            try {
                mainController.setSelectedMembership(membershipType);
                mainController.switchScene(Screen.CHECKOUT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button logoutButton;
    @FXML
    private HBox menuHBox;
    @FXML
    private VBox membershipContainer;
    @FXML
    private ScrollPane scrollPane;
}
