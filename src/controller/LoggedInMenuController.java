package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import dB.dbUtils;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoggedInMenuController implements Initializable {
    @FXML
    private Button callInButton;
    @FXML
    private Label calledInTag;
    @FXML
    private Button ViewTeamButton;
    @FXML
    private Button PaddysReportsButton;
    @FXML
    private Button MatchHistoryButton;
    @FXML
    private Button StatbookButton;
    @FXML
    private Button quitButton;
    @FXML
    private Label login_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login_label.setText("You are logged in as : " + dbUtils.loggedInUser);

        callInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                calledInTag.setText("Thats you in this week, so it is!");
                dbUtils.callInForTheWeek();
            }
        });
        ViewTeamButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dbUtils.grabThisWeeksTeam();
                StringBuilder sb = new StringBuilder();
                for (String key: dbUtils.currentTeamMembers.values()) {
                    sb.append(key).append("\n");
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(sb.toString());
                alert.show();
            }
        });
        PaddysReportsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dbUtils.grabPaddysReports();
                dbUtils.changeScene(event,"../View/paddysReportPage.fxml","Paddys Reports", null);
            }
        });
        MatchHistoryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            //create new table with previous matches
                //create a field view with stats of goals and assists beside names
            }
        });
        StatbookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.exit();
            }
        });
    }

    public static void CloseOffConnections(Connection connection, PreparedStatement psCheckUserExists, ResultSet resultSet) {
        if(resultSet !=null){
            try{
                resultSet.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(psCheckUserExists !=null){
            try{
                psCheckUserExists.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(connection !=null){
            try{
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
