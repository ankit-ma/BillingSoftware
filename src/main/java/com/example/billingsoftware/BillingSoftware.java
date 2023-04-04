package com.example.billingsoftware;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BillingSoftware extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BillingSoftware.class.getResource("Dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 650);
        stage.setTitle("Janta Store");

        stage.setMinWidth(900);
        stage.setMaxWidth(900);
        stage.setMinHeight(650);
        stage.setMaxHeight(650);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}