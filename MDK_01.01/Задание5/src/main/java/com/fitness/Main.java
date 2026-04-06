package com.fitness;

import com.fitness.database.DatabaseConnection;
import com.fitness.services.AuthService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        DatabaseConnection.initDatabase();
        AuthService.getInstance().setPrimaryStage(primaryStage);
        AuthService.getInstance().showLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}