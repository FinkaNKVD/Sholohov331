package db.dao;

import db.connection;
import db.models.DBMembership;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembershipDAO {

    // CREATE
    public int createMembership(DBMembership membership) throws SQLException {
        String sql = "INSERT INTO memberships (user_id, membership_type, start_date, end_date) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, membership.getUserId());
            stmt.setString(2, membership.getMembershipType());
            stmt.setDate(3, Date.valueOf(membership.getStartDate()));
            stmt.setDate(4, Date.valueOf(membership.getEndDate()));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Создание абонемента не удалось");
            }
        }
    }

    // READ - все абонементы
    public List<DBMembership> getAllMemberships() throws SQLException {
        List<DBMembership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships ORDER BY id";

        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                memberships.add(mapRowToMembership(rs));
            }
        }
        return memberships;
    }

    // READ - по ID
    public DBMembership getMembershipById(int id) throws SQLException {
        String sql = "SELECT * FROM memberships WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToMembership(rs);
            }
        }
        return null;
    }

    // READ - по пользователю
    public List<DBMembership> getMembershipsByUserId(int userId) throws SQLException {
        List<DBMembership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE user_id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberships.add(mapRowToMembership(rs));
            }
        }
        return memberships;
    }

    // READ - по типу абонемента
    public List<DBMembership> getMembershipsByType(String type) throws SQLException {
        List<DBMembership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE membership_type = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberships.add(mapRowToMembership(rs));
            }
        }
        return memberships;
    }

    // READ - активные на дату
    public List<DBMembership> getActiveMemberships(LocalDate date) throws SQLException {
        List<DBMembership> memberships = new ArrayList<>();
        String sql = "SELECT * FROM memberships WHERE start_date <= ? AND end_date >= ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(date));
            stmt.setDate(2, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                memberships.add(mapRowToMembership(rs));
            }
        }
        return memberships;
    }

    // UPDATE
    public boolean updateMembership(DBMembership membership) throws SQLException {
        String sql = "UPDATE memberships SET user_id = ?, membership_type = ?, start_date = ?, end_date = ? WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, membership.getUserId());
            stmt.setString(2, membership.getMembershipType());
            stmt.setDate(3, Date.valueOf(membership.getStartDate()));
            stmt.setDate(4, Date.valueOf(membership.getEndDate()));
            stmt.setInt(5, membership.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE
    public boolean deleteMembership(int id) throws SQLException {
        String sql = "DELETE FROM memberships WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private DBMembership mapRowToMembership(ResultSet rs) throws SQLException {
        DBMembership membership = new DBMembership();
        membership.setId(rs.getInt("id"));
        membership.setUserId(rs.getInt("user_id"));
        membership.setMembershipType(rs.getString("membership_type"));
        membership.setStartDate(rs.getDate("start_date").toLocalDate());
        membership.setEndDate(rs.getDate("end_date").toLocalDate());
        membership.setCreatedAt(rs.getString("created_at"));
        return membership;
    }
}