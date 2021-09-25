package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import dB.dbUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button buttonRegister;
    @FXML
    private Label label_baller;
    @FXML
    private Label ballerNameDisplay;
    @FXML
    private Button button_back;
    @FXML
    private Button proceedButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dbUtils.changeScene(event,"../View/signup.fxml","Logged in", null);
            }
        });
        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        proceedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dbUtils.changeScene(event,"../View/LoggedInMenu.fxml","Currently Logged In", null);
            }
        });
    }

    public void setUserInfo(String username){
        label_baller.setText(username);
    }
}
