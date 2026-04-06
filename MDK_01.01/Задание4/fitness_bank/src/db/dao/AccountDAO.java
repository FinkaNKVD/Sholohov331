package db.dao;

import db.connection;
import db.models.DBAccount;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public int createAccount(DBAccount account) throws SQLException {
        String sql = "INSERT INTO accounts (user_id, account_number, balance, client_level, welcome_bonus_received) VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getAccountNumber());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setString(4, account.getClientLevel());
            stmt.setBoolean(5, account.isWelcomeBonusReceived());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            throw new SQLException("Создание счета не удалось");
        }
    }

    public List<DBAccount> getAllAccounts() throws SQLException {
        List<DBAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts ORDER BY id";

        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    public DBAccount getAccountById(int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToAccount(rs);
            }
        }
        return null;
    }

    public DBAccount getAccountByNumber(String accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToAccount(rs);
            }
        }
        return null;
    }

    public List<DBAccount> getAccountsByUserId(int userId) throws SQLException {
        List<DBAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE user_id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    public List<DBAccount> getAccountsByLevel(String level) throws SQLException {
        List<DBAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE client_level = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, level);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                accounts.add(mapRowToAccount(rs));
            }
        }
        return accounts;
    }

    public DBAccount getAccountByIdForUpdate(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE id = ? FOR UPDATE";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToAccount(rs);
            }
        }
        return null;
    }

    public void updateAccountBalance(Connection conn, int accountId, BigDecimal newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, newBalance);
            stmt.setInt(2, accountId);
            stmt.executeUpdate();
        }
    }

    public boolean updateAccount(DBAccount account) throws SQLException {
        String sql = "UPDATE accounts SET user_id = ?, account_number = ?, balance = ?, client_level = ?, welcome_bonus_received = ? WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getAccountNumber());
            stmt.setBigDecimal(3, account.getBalance());
            stmt.setString(4, account.getClientLevel());
            stmt.setBoolean(5, account.isWelcomeBonusReceived());
            stmt.setInt(6, account.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteAccount(int id) throws SQLException {
        String sql = "DELETE FROM accounts WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private DBAccount mapRowToAccount(ResultSet rs) throws SQLException {
        DBAccount account = new DBAccount();
        account.setId(rs.getInt("id"));
        account.setUserId(rs.getInt("user_id"));
        account.setAccountNumber(rs.getString("account_number"));
        account.setBalance(rs.getBigDecimal("balance"));
        account.setClientLevel(rs.getString("client_level"));
        account.setWelcomeBonusReceived(rs.getBoolean("welcome_bonus_received"));
        account.setCreatedAt(rs.getString("created_at"));
        return account;
    }
}