package db.models;

import java.math.BigDecimal;

public class DBAccount {
    private int id;
    private int userId;
    private String accountNumber;
    private BigDecimal balance;
    private String clientLevel; // BASIC, PREMIUM, VIP
    private boolean welcomeBonusReceived;
    private String createdAt;

    // Конструкторы
    public DBAccount() {}

    public DBAccount(int userId, String accountNumber, BigDecimal balance, String clientLevel) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.clientLevel = clientLevel;
        this.welcomeBonusReceived = false;
    }

    public DBAccount(int id, int userId, String accountNumber, BigDecimal balance, String clientLevel,
                     boolean welcomeBonusReceived, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.clientLevel = clientLevel;
        this.welcomeBonusReceived = welcomeBonusReceived;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getClientLevel() { return clientLevel; }
    public void setClientLevel(String clientLevel) { this.clientLevel = clientLevel; }

    public boolean isWelcomeBonusReceived() { return welcomeBonusReceived; }
    public void setWelcomeBonusReceived(boolean welcomeBonusReceived) { this.welcomeBonusReceived = welcomeBonusReceived; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Account{" + accountNumber + ", " + balance + " руб., " + clientLevel + "}";
    }
}