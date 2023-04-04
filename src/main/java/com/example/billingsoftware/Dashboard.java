package com.example.billingsoftware;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.action.Action;

import java.io.IOException;

public class Dashboard {


    public AnchorPane dashboardPane;
    public Button adminButton;
    public Button cashierButton;

    @FXML
    protected void adminButtonListner(ActionEvent actionEvent) throws IOException {
        AnchorPane adminpane = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
        dashboardPane.getChildren().setAll(adminpane);
    }

    public void cashierButtonListner(ActionEvent actionEvent) throws IOException{
        AnchorPane pane = FXMLLoader.load(getClass().getResource("EmployeeLogin.fxml"));
        dashboardPane.getChildren().setAll(pane);
    }
}