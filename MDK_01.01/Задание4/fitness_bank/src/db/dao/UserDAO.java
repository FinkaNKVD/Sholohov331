package db.dao;

import db.connection;
import db.models.DBUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public int createUser(DBUser user) throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, birth_year) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setInt(3, user.getBirthYear());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Создание пользователя не удалось");
        }
    }

    public List<DBUser> getAllUsers() throws SQLException {
        List<DBUser> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";

        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }
        return users;
    }

    public DBUser getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToUser(rs);
            }
        }
        return null;
    }

    public List<DBUser> getUsersByLastName(String lastNamePattern) throws SQLException {
        List<DBUser> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE last_name ILIKE ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + lastNamePattern + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }
        return users;
    }

    public List<DBUser> getUsersByBirthYear(int birthYear) throws SQLException {
        List<DBUser> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE birth_year = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, birthYear);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }
        return users;
    }

    public boolean updateUser(DBUser user) throws SQLException {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, birth_year = ? WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setInt(3, user.getBirthYear());
            stmt.setInt(4, user.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private DBUser mapRowToUser(ResultSet rs) throws SQLException {
        DBUser user = new DBUser();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setBirthYear(rs.getInt("birth_year"));
        user.setCreatedAt(rs.getString("created_at"));
        return user;
    }
}