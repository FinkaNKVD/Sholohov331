import db.connection;

public class TestDatabase {
    public static void main(String[] args) {
        System.out.println("=== ПОДКЛЮЧЕНИЯ ===");
        connection.testConnection();
    }
}