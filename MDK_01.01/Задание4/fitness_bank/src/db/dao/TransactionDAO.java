package db.dao;

import db.connection;
import db.models.DBTransaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // CREATE
    public int createTransaction(DBTransaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (from_account_id, to_account_id, amount, description) VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setTransactionParameters(stmt, transaction);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Создание транзакции не удалось");
            }
        }
    }

    // CREATE в рамках существующей транзакции БД
    public int createTransaction(Connection conn, DBTransaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (from_account_id, to_account_id, amount, description) VALUES (?, ?, ?, ?) RETURNING id";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            setTransactionParameters(stmt, transaction);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Создание транзакции не удалось");
            }
        }
    }

    private void setTransactionParameters(PreparedStatement stmt, DBTransaction transaction) throws SQLException {
        if (transaction.getFromAccountId() != null) {
            stmt.setInt(1, transaction.getFromAccountId());
        } else {
            stmt.setNull(1, Types.INTEGER);
        }

        if (transaction.getToAccountId() != null) {
            stmt.setInt(2, transaction.getToAccountId());
        } else {
            stmt.setNull(2, Types.INTEGER);
        }

        stmt.setBigDecimal(3, transaction.getAmount());
        stmt.setString(4, transaction.getDescription());
    }

    // READ - все транзакции
    public List<DBTransaction> getAllTransactions() throws SQLException {
        List<DBTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";

        try (Connection conn = connection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }
        }
        return transactions;
    }

    // READ - по ID
    public DBTransaction getTransactionById(int id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapRowToTransaction(rs);
            }
        }
        return null;
    }

    // READ - по счету (отправитель или получатель)
    public List<DBTransaction> getTransactionsByAccountId(int accountId) throws SQLException {
        List<DBTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE from_account_id = ? OR to_account_id = ? ORDER BY transaction_date DESC";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountId);
            stmt.setInt(2, accountId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }
        }
        return transactions;
    }

    // READ - по дате
    public List<DBTransaction> getTransactionsByDateRange(Date startDate, Date endDate) throws SQLException {
        List<DBTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE transaction_date BETWEEN ? AND ? ORDER BY transaction_date DESC";

        try (Connection conn = connection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(mapRowToTransaction(rs));
            }
        }
        return transactions;
    }

    private DBTransaction mapRowToTransaction(ResultSet rs) throws SQLException {
        DBTransaction transaction = new DBTransaction();
        transaction.setId(rs.getInt("id"));
        transaction.setFromAccountId((Integer) rs.getObject("from_account_id"));
        transaction.setToAccountId((Integer) rs.getObject("to_account_id"));
        transaction.setAmount(rs.getBigDecimal("amount"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
        transaction.setDescription(rs.getString("description"));
        return transaction;
    }
}