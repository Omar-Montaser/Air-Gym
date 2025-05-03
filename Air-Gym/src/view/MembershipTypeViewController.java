package view;

import controller.Screen;
import javafx.fxml.FXML;

public class MembershipTypeViewController extends BaseController{

    @FXML
    private void handlePayments(){
        mainController.switchScene(Screen.PAYMENT_VIEW);
    }
}
