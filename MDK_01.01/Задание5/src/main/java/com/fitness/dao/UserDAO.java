package com.fitness.dao;

import com.fitness.database.DatabaseConnection;
import com.fitness.models.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return extractUser(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<User> getUsersByRole(String role) {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) list.add(extractUser(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO users (username, password, role, name, age, gender, contact) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = DatabaseConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getName());
            stmt.setInt(5, user.getAge());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getContact());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"), rs.getString("username"), rs.getString("password"),
                rs.getString("role"), rs.getString("name"), rs.getInt("age"),
                rs.getString("gender"), rs.getString("contact")
        );
    }
}