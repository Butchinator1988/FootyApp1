package dB;

import controller.LoggedInMenuController;
import controller.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static controller.LoggedInMenuController.CloseOffConnections;

public class dbUtils {

    public static final String PASSWORD = "Metallicar19881!";
    public static final String USER = "root";
    public static String loggedInUser = null;
    public static int loggedInId;
    public static Map<String, String> currentTeamMembers = new HashMap<>();
    public static Map<String, String> reports = new HashMap<>();
    public static ListView<String> pReports = new ListView<>();

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username){
        //parent is a base class for all the nodes that have children in the scene
        Parent root = null;
        if (username!= null){
            try{
                FXMLLoader loader = new FXMLLoader(dbUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoginController loginController = loader.getController();
                loginController.setUserInfo(username);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else{
            try{
                root = FXMLLoader.load(dbUtils.class.getResource(fxmlFile));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        // a stage is basically the window of the GUI
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 400, 800));
        stage.show();
    }
    public static void signUpUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/football", USER, PASSWORD);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username");
                alert.show();
            }else{
                psInsert = connection.prepareStatement("INSERT INTO `football`.`users` (`username`, `password`) VALUES (?, ?)");

                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();

                changeScene(event, "LoginSuccess.fxml", "Welcome!", username);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            //closing dB connections to save memory
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psCheckUserExists!=null){
                try{
                    psCheckUserExists.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert!=null){
                try{
                    psInsert.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/football", USER, PASSWORD);
            preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in database");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided details are incorrect");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        loggedInUser = username;
                        changeScene(event, "../View/LoginSuccess.fxml", "Welcome!", username);
                        }
                    else{
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided details are incorrect");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try{
                    connection.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try{
                    preparedStatement.close();
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
         }
    }
    public static void grabThisWeeksTeam(){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/football", dbUtils.USER, dbUtils.PASSWORD);
            psCheckUserExists = connection.prepareStatement("SELECT * FROM thisweeksteam");
            resultSet = psCheckUserExists.executeQuery();

            while (resultSet.next()) {
                currentTeamMembers.put(resultSet.getString("id"),resultSet.getString("baller"));
            }
//            dateAndReport.forEach((k,v) -> {
//                System.out.println(k + ": " + v);
//            });
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            CloseOffConnections(connection, psCheckUserExists, resultSet);
        }

    }
    public static void callInForTheWeek(){
        Connection connection = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/football", dbUtils.USER, dbUtils.PASSWORD);
            psCheckUserExists = connection.prepareStatement("INSERT INTO `football`.`thisweeksteam` (`baller`) VALUES (?)");
            psCheckUserExists.setString(1, dbUtils.loggedInUser);
            psCheckUserExists.executeUpdate();
        }catch(SQLException e){
            System.out.println("User not found in database");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You are already in for this week");
            alert.show();
            e.printStackTrace();
        }finally{
            CloseOffConnections(connection, psCheckUserExists, resultSet);
        }
    }
    public static void grabPaddysReports() {
        Connection connection = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/football", dbUtils.USER, dbUtils.PASSWORD);
            psCheckUserExists = connection.prepareStatement("SELECT date, theReport FROM paddysreports");
            resultSet = psCheckUserExists.executeQuery();
            while (resultSet.next()) {
                String date = resultSet.getString("date");
                String theReport = resultSet.getString("theReport");
                String newString = date + "\n" + theReport + "\n\n" ;
                pReports.getItems().add(newString);
//                reports.put(resultSet.getString("date"),resultSet.getString("theReport"));
            }
        } catch (SQLException e) {
            System.out.println("No reports posted");
            e.printStackTrace();
        } finally {
            LoggedInMenuController.CloseOffConnections(connection, psCheckUserExists, resultSet);
        }
    }
}
