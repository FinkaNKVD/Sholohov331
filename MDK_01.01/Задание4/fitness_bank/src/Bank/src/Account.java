package Bank.src;

/**
 * Класс банковского счета
 */
public class Account {
    private String clientName;
    private String accountNumber;
    private double balance;
    private ClientLevel clientLevel;
    private boolean welcomeBonusReceived;

    // Конструктор
    public Account(String clientName, String accountNumber, ClientLevel clientLevel) {
        setClientName(clientName);
        setAccountNumber(accountNumber);
        this.clientLevel = clientLevel;
        this.balance = 0.0;
        this.welcomeBonusReceived = false;
    }

    // Геттеры и сеттеры с валидацией
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        if (clientName == null || clientName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя клиента не может быть пустым");
        }
        this.clientName = clientName.trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        if (accountNumber == null || !accountNumber.matches("\\d{20}")) {
            throw new IllegalArgumentException("Номер счета должен содержать ровно 20 цифр");
        }
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public ClientLevel getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(ClientLevel clientLevel) {
        this.clientLevel = clientLevel;
    }

    // Основные методы

    /**
     * Вывод информации о балансе
     */
    public void displayBalance() {
        System.out.printf("Клиент: %s, Счет: %s, Баланс: %.2f руб., Уровень: %s%n",
                clientName, accountNumber, balance, clientLevel.getDescription());
    }

    /**
     * Перевод на другой счет
     */
    public void transferTo(Account targetAccount, double amount) {
        if (targetAccount == null) {
            throw new IllegalArgumentException("Целевой счет не может быть null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма перевода должна быть положительной");
        }
        if (balance < amount) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }

        this.balance -= amount;
        targetAccount.balance += amount;

        System.out.printf("Перевод выполнен: %.2f руб. -> %s%n", amount, targetAccount.getClientName());
    }

    /**
     * Оплата услуги
     */
    public void payForService(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма оплаты должна быть положительной");
        }
        if (balance < amount) {
            throw new IllegalArgumentException("Недостаточно средств для оплаты услуги");
        }

        // Начисление приветственного бонуса при первой оплате
        if (!welcomeBonusReceived) {
            balance += 1000;
            welcomeBonusReceived = true;
            System.out.println("Начислен приветственный бонус: 1000 руб.");
        }

        // Списание суммы за услугу
        balance -= amount;

        // Начисление кешбека в зависимости от уровня клиента
        double cashback = clientLevel.calculateCashback(amount);
        if (cashback > 0) {
            balance += cashback;
            System.out.printf("Начислен кешбек: %.2f руб.%n", cashback);
        }

        System.out.printf("Оплата услуги: %.2f руб.%n", amount);
    }

    /**
     * Списание ежемесячной платы
     */
    public void applyMonthlyFee() {
        double monthlyFee = clientLevel.getMonthlyFee();
        if (monthlyFee > 0) {
            if (balance < monthlyFee) {
                throw new IllegalArgumentException("Недостаточно средств для списания ежемесячной платы");
            }
            balance -= monthlyFee;
            System.out.printf("Списана ежемесячная плата: %.2f руб.%n", monthlyFee);
        }
    }

    // Метод для пополнения счета (для тестирования)
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма пополнения должна быть положительной");
        }
        balance += amount;
        System.out.printf("Счет пополнен на: %.2f руб.%n", amount);
    }

    @Override
    public String toString() {
        return String.format("Account{clientName='%s', accountNumber='%s', balance=%.2f, level=%s}",
                clientName, accountNumber, balance, clientLevel);
    }
}