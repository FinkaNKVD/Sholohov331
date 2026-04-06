package db;

import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    // !!! ВАЖНО: ИЗМЕНИТЕ НА СВОИ ДАННЫЕ !!!
    private static final String URL = "jdbc:postgresql://localhost:5432/fitness_bank";
    private static final String USER = "postgres";      // ваш пользователь PostgreSQL
    private static final String PASSWORD = "4019"; // пароль, который вводили при установке

    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ Драйвер PostgreSQL загружен");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Ошибка загрузки драйвера: " + e.getMessage());
        }
    }

    public static java.sql.Connection getConnection() throws SQLException {
        System.out.println("🔄 Подключение к базе данных...");
        java.sql.Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("✅ Подключение успешно!");
        return conn;
    }

    public static void testConnection() {
        try (java.sql.Connection conn = getConnection()) {
            System.out.println("✅ Тест подключения: УСПЕШНО");
        } catch (SQLException e) {
            System.err.println("❌ Тест подключения: ОШИБКА");
            System.err.println("Проверьте данные подключения:");
            System.err.println("  URL: " + URL);
            System.err.println("  USER: " + USER);
            System.err.println("  PASSWORD: " + PASSWORD);
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}