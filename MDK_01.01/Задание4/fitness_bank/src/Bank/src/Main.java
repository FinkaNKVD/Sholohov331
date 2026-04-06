package Bank.src;

import java.util.Scanner;

/**
 * Главный класс консольного приложения
 */
public class Main {
    private Bank bank;
    private Scanner scanner;

    public Main() {
        this.bank = new Bank();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("=== Банковское приложение ===");

        // Создание тестовых данных
        bank.createTestAccounts();
        bank.fundAccountsForTesting();

        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1:
                    displayAllAccounts();
                    break;
                case 2:
                    performPayment();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    applyMonthlyFees();
                    break;
                case 5:
                    testCashbackScenarios();
                    break;
                case 0:
                    running = false;
                    System.out.println("Выход из приложения.");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }

        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n=== Главное меню ===");
        System.out.println("1. Показать все счета");
        System.out.println("2. Оплатить услугу");
        System.out.println("3. Перевод между счетами");
        System.out.println("4. Списать ежемесячную плату");
        System.out.println("5. Тестирование кешбеков");
        System.out.println("0. Выход");
    }

    private void displayAllAccounts() {
        bank.displayAllAccounts();
    }

    private void performPayment() {
        System.out.println("\n=== Оплата услуги ===");
        String accountNumber = getStringInput("Введите номер счета (20 цифр): ");

        Account account = bank.findAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Счет не найден.");
            return;
        }

        double amount = getDoubleInput("Введите сумму оплаты: ");

        try {
            account.payForService(amount);
            account.displayBalance();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void performTransfer() {
        System.out.println("\n=== Перевод между счетами ===");
        String fromAccountNumber = getStringInput("Введите номер счета отправителя: ");
        String toAccountNumber = getStringInput("Введите номер счета получателя: ");

        Account fromAccount = bank.findAccountByNumber(fromAccountNumber);
        Account toAccount = bank.findAccountByNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            System.out.println("Один из счетов не найден.");
            return;
        }

        double amount = getDoubleInput("Введите сумму перевода: ");

        try {
            fromAccount.transferTo(toAccount, amount);
            System.out.println("Баланс отправителя:");
            fromAccount.displayBalance();
            System.out.println("Баланс получателя:");
            toAccount.displayBalance();
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка перевода: " + e.getMessage());
        }
    }

    private void applyMonthlyFees() {
        bank.applyMonthlyFeesForAll();
    }

    private void testCashbackScenarios() {
        System.out.println("\n=== Тестирование кешбеков ===");

        // Создаем тестовые счета для демонстрации кешбеков
        Account basicTest = new Account("Тест Базовый", "99900000000000000001", ClientLevel.BASIC);
        Account premiumTest = new Account("Тест Премиум", "99900000000000000002", ClientLevel.PREMIUM);
        Account vipTest = new Account("Тест ВИП", "99900000000000000003", ClientLevel.VIP);

        // Пополняем счета
        try {
            java.lang.reflect.Field balanceField = Account.class.getDeclaredField("balance");
            balanceField.setAccessible(true);
            balanceField.set(basicTest, 50000.0);
            balanceField.set(premiumTest, 50000.0);
            balanceField.set(vipTest, 50000.0);
        } catch (Exception e) {
            System.out.println("Ошибка при пополнении счетов: " + e.getMessage());
        }

        // Тестируем разные сценарии оплат
        double[] testAmounts = {5000.0, 15000.0, 150000.0};

        for (double amount : testAmounts) {
            System.out.printf("\n--- Тест оплаты на сумму: %.2f руб. ---%n", amount);

            testPaymentWithCashback(basicTest, amount, "Базовый");
            testPaymentWithCashback(premiumTest, amount, "Премиум");
            testPaymentWithCashback(vipTest, amount, "ВИП");
        }
    }

    private void testPaymentWithCashback(Account account, double amount, String type) {
        System.out.printf("%s клиент:%n", type);
        try {
            account.payForService(amount);
            account.displayBalance();

            // Сбрасываем флаг бонуса для следующих тестов
            java.lang.reflect.Field bonusField = Account.class.getDeclaredField("welcomeBonusReceived");
            bonusField.setAccessible(true);
            bonusField.set(account, false);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Вспомогательные методы для ввода
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите целое число.");
            scanner.next();
            System.out.print(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return input;
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Пожалуйста, введите число.");
            scanner.next();
            System.out.print(prompt);
        }
        double input = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        return input;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.run();
    }
}