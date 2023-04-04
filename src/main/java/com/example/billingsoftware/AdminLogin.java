package com.example.billingsoftware;

import Model.User;
import Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.lang.annotation.Inherited;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminLogin implements Initializable {
    public Button Submit;
    public TextField usernameText;
    public PasswordField passwordText;
    public AnchorPane adminPane;
    public Button homeDashboardButton;

    public void submitButtonHandler(ActionEvent actionEvent) throws IOException, SQLException {

        UserService us = new UserService();
        if(us.adminLoginApproved(usernameText.getText(),passwordText.getText())) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("AdminDashBoard.fxml"));
            adminPane.getChildren().setAll(pane);
        }
        else{
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setHeaderText("Wrong Credential");
            al.setContentText("Username or password is not correct....\nTry Again later");
            al.show();
        }
       // us.closeConnection();
    }

    public void homeDashboardButtonListner(ActionEvent actionEvent) throws IOException, SQLException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        adminPane.getChildren().setAll(pane);
        DatabaseConnection.closeConnection();
        System.out.println("Database Connection CLOSED");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DatabaseConnection.startDatabaseConnection();
            System.out.println("Database Connection STARTED");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
