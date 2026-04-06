package com.fitness.controllers;

import com.fitness.services.AuthService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TrainerDashboardController {
    public void start(Stage stage) {
        stage.setTitle("Панель тренера");
        Button logout = new Button("Выход");
        logout.setOnAction(e -> handleLogout(stage));
        VBox vbox = new VBox(20, new Button("Расписание"), new Button("Планы тренировок"), logout);
        stage.setScene(new Scene(vbox, 300, 200));
        stage.show();
    }

    private void handleLogout(Stage stage) {
        AuthService.getInstance().logout();
        stage.close();
    }
}