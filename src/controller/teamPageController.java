package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import dB.dbUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class teamPageController implements Initializable {

    @FXML
    private Label textDisplay;
    @FXML
    private Button backButton;
    @FXML
    private Label login_label;
    @FXML
    private Button quitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbUtils.grabThisWeeksTeam();
    }
}
