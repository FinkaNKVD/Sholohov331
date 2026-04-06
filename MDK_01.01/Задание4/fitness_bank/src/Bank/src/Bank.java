package Bank.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для управления банковскими счетами
 */
public class Bank {
    private List<Account> accounts;

    public Bank() {
        this.accounts = new ArrayList<>();
    }

    /**
     * Добавление счета
     */
    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Счет не может быть null");
        }
        accounts.add(account);
    }

    /**
     * Поиск счета по номеру
     */
    public Account findAccountByNumber(String accountNumber) {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    /**
     * Создание тестовых данных - 5 базовых, 5 премиум, 5 ВИП клиентов
     */
    public void createTestAccounts() {
        // Базовые клиенты
        for (int i = 1; i <= 5; i++) {
            String accountNumber = String.format("100000000000000000%02d", i);
            Account account = new Account("Базовый Клиент " + i, accountNumber, ClientLevel.BASIC);
            accounts.add(account);
        }

        // Премиум клиенты
        for (int i = 1; i <= 5; i++) {
            String accountNumber = String.format("200000000000000000%02d", i);
            Account account = new Account("Премиум Клиент " + i, accountNumber, ClientLevel.PREMIUM);
            accounts.add(account);
        }

        // ВИП клиенты
        for (int i = 1; i <= 5; i++) {
            String accountNumber = String.format("300000000000000000%02d", i);
            Account account = new Account("ВИП Клиент " + i, accountNumber, ClientLevel.VIP);
            accounts.add(account);
        }
    }

    /**
     * Применение ежемесячной платы для всех счетов
     */
    public void applyMonthlyFeesForAll() {
        System.out.println("\n=== Списание ежемесячной платы ===");
        for (Account account : accounts) {
            try {
                account.applyMonthlyFee();
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка для " + account.getClientName() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Вывод информации о всех счетах
     */
    public void displayAllAccounts() {
        System.out.println("\n=== Информация о всех счетах ===");
        for (Account account : accounts) {
            account.displayBalance();
        }
    }

    /**
     * Пополнение счетов для тестирования
     */
    public void fundAccountsForTesting() {
        for (Account account : accounts) {
            try {
                // Пополняем счета для тестирования операций
                java.lang.reflect.Field balanceField = Account.class.getDeclaredField("balance");
                balanceField.setAccessible(true);
                balanceField.set(account, 50000.0);
            } catch (Exception e) {
                System.out.println("Ошибка при пополнении счета: " + e.getMessage());
            }
        }
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }
}