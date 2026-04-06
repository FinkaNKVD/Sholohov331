package db.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DBTransaction {
    private int id;
    private Integer fromAccountId;
    private Integer toAccountId;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String description;

    // Конструкторы
    public DBTransaction() {}

    public DBTransaction(Integer fromAccountId, Integer toAccountId, BigDecimal amount, String description) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.description = description;
    }

    public DBTransaction(int id, Integer fromAccountId, Integer toAccountId, BigDecimal amount,
                         LocalDateTime transactionDate, String description) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(Integer fromAccountId) { this.fromAccountId = fromAccountId; }

    public Integer getToAccountId() { return toAccountId; }
    public void setToAccountId(Integer toAccountId) { this.toAccountId = toAccountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Transaction{" + amount + " руб., " + transactionDate + "}";
    }
}