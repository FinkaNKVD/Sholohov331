package com.fitness.controllers;

import com.fitness.services.AuthService;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboardController {
    public void start(Stage stage) {
        stage.setTitle("Панель администратора");
        Button logout = new Button("Выход");
        logout.setOnAction(e -> handleLogout(stage));
        VBox vbox = new VBox(20, new Button("Управление клиентами"), new Button("Расписание"), new Button("Планы"), logout);
        stage.setScene(new Scene(vbox, 300, 250));
        stage.show();
    }

    private void handleLogout(Stage stage) {
        AuthService.getInstance().logout();
        stage.close();
    }
}