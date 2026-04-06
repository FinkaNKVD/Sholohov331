package com.fitness.services;

import com.fitness.controllers.LoginController;
import com.fitness.dao.UserDAO;
import com.fitness.models.User;
import javafx.stage.Stage;

public class AuthService {
    private static AuthService instance;
    private Stage primaryStage;
    private User currentUser;

    private AuthService() {}

    public static AuthService getInstance() {
        if (instance == null) instance = new AuthService();
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public boolean login(String username, String password) {
        UserDAO dao = new UserDAO();
        User user = dao.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void logout() {
        currentUser = null;
        showLoginScreen();
    }

    public void showLoginScreen() {
        LoginController login = new LoginController();
        login.start(primaryStage);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}