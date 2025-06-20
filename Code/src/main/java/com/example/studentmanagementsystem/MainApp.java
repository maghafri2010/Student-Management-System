package com.example.studentmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class MainApp extends Application {

    private static Stage primaryStage;
    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;
        showLoginScene();

    }



    public static void showLoginScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("Login.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load(), 1024, 768);
        primaryStage.setTitle("System Management system - Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void dashboard() throws IOException {

        FXMLLoader fxmlLoader1 = new FXMLLoader(MainApp.class.getResource("dashboard.fxml"));
        Scene dashboardScene = new Scene(fxmlLoader1.load(), 1024, 768);
        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(dashboardScene);
        primaryStage.show();
    }

    public static void register() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("register.fxml"));
        Scene registerscene = new Scene(fxmlLoader.load(),1024, 768);
        primaryStage.setTitle("Register");
        primaryStage.setScene(registerscene);
        primaryStage.show();
    }

    public static void resetPassword() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("resetPassword.fxml"));
        Scene resetScene = new Scene(fxmlLoader.load(),1024,768);
        primaryStage.setTitle("Reset Password");
        primaryStage.setScene(resetScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();


    }
}