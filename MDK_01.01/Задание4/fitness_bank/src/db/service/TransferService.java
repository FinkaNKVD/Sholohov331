package db.service;

import db.connection;
import db.dao.AccountDAO;
import db.dao.TransactionDAO;
import db.models.DBAccount;
import db.models.DBTransaction;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

// Исключения
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) { super(message); }
}

class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) { super(message); }
}

public class TransferService {
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void transferMoney(int fromAccountId, int toAccountId, BigDecimal amount, String description)
            throws SQLException, InsufficientFundsException, AccountNotFoundException {
        requirePositiveAmount(amount);

        try (Connection conn = connection.getConnection()) {
            System.out.println("🔄 Начинаем транзакцию перевода...");
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            try {
                System.out.println("   Блокировка счета отправителя...");
                DBAccount fromAcc = loadAccountForUpdate(conn, fromAccountId, "Счет отправителя не найден");

                System.out.println("   Блокировка счета получателя...");
                DBAccount toAcc = loadAccountForUpdate(conn, toAccountId, "Счет получателя не найден");

                System.out.println("   Проверка баланса...");
                System.out.println("      Баланс отправителя: " + fromAcc.getBalance() + " руб.");
                System.out.println("      Сумма перевода: " + amount + " руб.");
                ensureSufficientFunds(fromAcc, amount);

                System.out.println("   Обновление балансов...");
                BigDecimal updatedFromBalance = fromAcc.getBalance().subtract(amount);
                BigDecimal updatedToBalance = toAcc.getBalance().add(amount);

                accountDAO.updateAccountBalance(conn, fromAccountId, updatedFromBalance);
                accountDAO.updateAccountBalance(conn, toAccountId, updatedToBalance);

                System.out.println("   Запись транзакции в историю...");
                DBTransaction tx = new DBTransaction(fromAccountId, toAccountId, amount, description);
                transactionDAO.createTransaction(conn, tx);

                conn.commit();
                System.out.println("✅ Перевод выполнен успешно! Сумма: " + amount + " руб.");
            } catch (SQLException | InsufficientFundsException | AccountNotFoundException e) {
                rollbackSilently(conn);
                System.out.println("❌ Транзакция отменена. Выполнен ROLLBACK.");
                throw e;
            } finally {
                // На случай, если соединение будет использовано повторно (в рамках try-with-resources это редко нужно)
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ignore) {
                    // намеренно игнорируем
                }
            }
        }
    }

    private void requirePositiveAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }
    }

    private DBAccount loadAccountForUpdate(Connection conn, int accountId, String notFoundMessage)
            throws SQLException, AccountNotFoundException {
        DBAccount acc = accountDAO.getAccountByIdForUpdate(conn, accountId);
        if (acc == null) {
            throw new AccountNotFoundException(notFoundMessage);
        }
        return acc;
    }

    private void ensureSufficientFunds(DBAccount fromAccount, BigDecimal amount)
            throws InsufficientFundsException {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Недостаточно средств");
        }
    }

    private void rollbackSilently(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException ex) {
            System.err.println("❌ Ошибка при откате транзакции: " + ex.getMessage());
        }
    }
}