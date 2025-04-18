package com.example.amalitechemployeemanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeeManagementSystemApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/amalitechemployeemanagementsystem/MainView.fxml"));
            Parent root = loader.load();

            // Set up the scene
            Scene scene = new Scene(root, 300, 500);

            // Configure the stage
            primaryStage.setTitle("Employee Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}