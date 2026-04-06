import db.connection;
import db.dao.*;
import db.models.*;
import db.service.TransferService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static UserDAO userDAO = new UserDAO();
    private static MembershipDAO membershipDAO = new MembershipDAO();
    private static AccountDAO accountDAO = new AccountDAO();
    private static TransactionDAO transactionDAO = new TransactionDAO();
    private static TransferService transferService = new TransferService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        showStartBanner();
        connection.testConnection();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readMenuInt("Выберите действие: ");
            running = dispatch(choice);
        }
    }

    private static void printMainMenu() {
        System.out.println("\n=====================================");
        System.out.println("  ГЛАВНОЕ МЕНЮ");
        System.out.println("=====================================");
        System.out.println("1. Демонстрация CRUD для пользователей");
        System.out.println("2. Демонстрация CRUD для абонементов");
        System.out.println("3. Демонстрация CRUD для счетов");
        System.out.println("4. Перевод средств (с транзакцией)");
        System.out.println("5. Просмотр всех данных");
        System.out.println("0. Выход");
    }

    private static void showStartBanner() {
        System.out.println("=====================================");
        System.out.println("  ЗАДАНИЕ №4: РАБОТА С БАЗОЙ ДАННЫХ");
        System.out.println("=====================================");
    }

    private static boolean dispatch(int choice) {
        try {
            switch (choice) {
                case 1:
                    demoUserCRUD();
                    break;
                case 2:
                    demoMembershipCRUD();
                    break;
                case 3:
                    demoAccountCRUD();
                    break;
                case 4:
                    demoTransfer();
                    break;
                case 5:
                    viewAllData();
                    break;
                case 0:
                    System.out.println("👋 Выход из программы");
                    return false;
                default:
                    System.out.println("❌ Неверный выбор");
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\nНажмите Enter для продолжения...");
        scanner.nextLine();
        return true;
    }

    private static void demoUserCRUD() throws SQLException {
        System.out.println("\n--- CRUD для пользователей ---");

        // CREATE
        System.out.println("\n1. СОЗДАНИЕ нового пользователя:");
        DBUser newUser = new DBUser("Тест", "Тестовый", 2000);
        int userId = userDAO.createUser(newUser);
        System.out.println("✅ Создан пользователь с ID: " + userId);

        // READ
        System.out.println("\n2. ЧТЕНИЕ всех пользователей:");
        List<DBUser> users = userDAO.getAllUsers();
        for (DBUser u : users) {
            System.out.println("   " + u.getId() + ": " + u.getFirstName() + " " + u.getLastName() + ", " + u.getBirthYear());
        }

        // READ по фамилии
        System.out.println("\n3. ПОИСК по фамилии 'Петров':");
        List<DBUser> found = userDAO.getUsersByLastName("Петров");
        for (DBUser u : found) {
            System.out.println("   Найден: " + u.getFirstName() + " " + u.getLastName());
        }

        // UPDATE
        System.out.println("\n4. ОБНОВЛЕНИЕ пользователя:");
        DBUser userToUpdate = userDAO.getUserById(userId);
        if (userToUpdate != null) {
            userToUpdate.setFirstName("Обновленный");
            userDAO.updateUser(userToUpdate);
            System.out.println("✅ Пользователь обновлен");
        }

        // DELETE
        System.out.println("\n5. УДАЛЕНИЕ пользователя:");
        userDAO.deleteUser(userId);
        System.out.println("✅ Пользователь удален");
    }

    private static void demoTransfer() throws Exception {
        System.out.println("\n--- ПЕРЕВОД СРЕДСТВ (С ТРАНЗАКЦИЕЙ) ---");

        // Получаем список счетов
        List<DBAccount> accounts = accountDAO.getAllAccounts();
        if (accounts.size() < 2) {
            System.out.println("❌ Недостаточно счетов для перевода");
            return;
        }

        DBAccount fromAccount = null;
        DBAccount toAccount = null;
        for (DBAccount acc : accounts) {
            if (fromAccount == null) {
                fromAccount = acc;
            } else {
                toAccount = acc;
                break;
            }
        }

        System.out.println("До перевода:");
        System.out.println("   Счет отправителя " + fromAccount.getAccountNumber() +
                ": " + fromAccount.getBalance() + " руб.");
        System.out.println("   Счет получателя  " + toAccount.getAccountNumber() +
                ": " + toAccount.getBalance() + " руб.");

        BigDecimal amount = new BigDecimal("5000.00");
        System.out.println("\n🔄 Перевод " + amount + " руб....");

        try {
            transferService.transferMoney(
                    fromAccount.getId(),
                    toAccount.getId(),
                    amount,
                    "Тестовый перевод"
            );

            // Проверяем обновленные балансы
            DBAccount updatedFrom = accountDAO.getAccountById(fromAccount.getId());
            DBAccount updatedTo = accountDAO.getAccountById(toAccount.getId());

            System.out.println("\nПосле перевода:");
            System.out.println("   Счет отправителя: " + updatedFrom.getBalance() + " руб.");
            System.out.println("   Счет получателя:  " + updatedTo.getBalance() + " руб.");

        } catch (Exception e) {
            System.err.println("❌ Ошибка перевода: " + e.getMessage());
            throw e;
        }
    }

    private static void viewAllData() throws SQLException {
        System.out.println("\n--- ВСЕ ДАННЫЕ В БАЗЕ ---");

        System.out.println("\n📋 ПОЛЬЗОВАТЕЛИ:");
        for (DBUser u : userDAO.getAllUsers()) {
            System.out.println("   " + u);
        }

        System.out.println("\n📋 АБОНЕМЕНТЫ:");
        for (DBMembership m : membershipDAO.getAllMemberships()) {
            System.out.println("   " + m);
        }

        System.out.println("\n📋 СЧЕТА:");
        for (DBAccount a : accountDAO.getAllAccounts()) {
            System.out.println("   " + a.getAccountNumber() + ": " +
                    a.getBalance() + " руб. (" + a.getClientLevel() + ")");
        }

        System.out.println("\n📋 ТРАНЗАКЦИИ:");
        for (DBTransaction t : transactionDAO.getAllTransactions()) {
            System.out.println("   " + t.getTransactionDate() +
                    ": " + t.getAmount() + " руб.");
        }
    }

    private static void demoMembershipCRUD() throws SQLException {
        System.out.println("\n--- CRUD для абонементов ---");

        List<DBUser> users = userDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("❌ Нет пользователей");
            return;
        }

        // CREATE
        DBMembership membership = new DBMembership(
                users.get(0).getId(),
                "FULL",
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );
        membershipDAO.createMembership(membership);
        System.out.println("✅ Абонемент создан");

        // READ по типу
        System.out.println("Абонементы типа FULL:");
        for (DBMembership m : membershipDAO.getMembershipsByType("FULL")) {
            System.out.println("   " + m);
        }
    }

    private static void demoAccountCRUD() throws SQLException {
        System.out.println("\n--- CRUD для счетов ---");

        // READ по уровню
        System.out.println("VIP счета:");
        for (DBAccount a : accountDAO.getAccountsByLevel("VIP")) {
            System.out.println("   " + a.getAccountNumber() + ": " + a.getBalance() + " руб.");
        }
    }

    private static int readMenuInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("❌ Ожидается целое число. Попробуйте снова.");
            scanner.next();
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // чистим остаток строки
        return value;
    }
}