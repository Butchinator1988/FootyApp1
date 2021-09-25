package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import dB.dbUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class PaddysReportController implements Initializable {
    @FXML
    private Button quitButton;
    @FXML
    private Label login_label11;
    @FXML
    private Button lastReportButton;
    @FXML
    private Button nextReportButton;
    static int count;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defaultLabelText();

        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dbUtils.changeScene(actionEvent,"../View/LoggedInMenu.fxml","Currently Logged In", null);
            }
        });

        nextReportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                count++;
                login_label11.setText(dbUtils.pReports.getItems().get(count));
                login_label11.setWrapText(true);
            }
        });
        lastReportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(!dbUtils.pReports.getItems().get(count).isEmpty()){
                    count--;
                    login_label11.setText(dbUtils.pReports.getItems().get(count));
                    login_label11.setWrapText(true);
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("No previous reports - if needed speak to Paddy");
                    alert.show();
                }
            }
        });
    }

    private void defaultLabelText() {
        login_label11.setText(dbUtils.pReports.getItems().get(count));
        login_label11.setWrapText(true);
    }
}
