package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import dB.dbUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable   {

    @FXML
    private Button button_back;
    @FXML
    private Button buttonRegister;
    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!tf_username.getText().trim().isEmpty()&& !tf_password.getText().trim().isEmpty()){
                    dbUtils.signUpUser(event, tf_username.getText(), tf_password.getText());
                }else{
                    System.out.println("Please fill in all the information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all the information to sign up");
                    alert.show();
                }
            }
        });

        button_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dbUtils.changeScene(event, "../View/LoginPage.fxml", "Login Page", null);
            }
        });
    }
}
