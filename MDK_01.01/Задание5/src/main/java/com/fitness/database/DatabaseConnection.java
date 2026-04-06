package com.fitness.database;

import java.sql.*;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:h2:~/fitness_db;DB_CLOSE_DELAY=-1";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, "sa", "");
        }
        return connection;
    }

    public static void initDatabase() {
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE NOT NULL," +
                    "password VARCHAR(100) NOT NULL," +
                    "role VARCHAR(20) NOT NULL," +
                    "name VARCHAR(100)," +
                    "age INT," +
                    "gender VARCHAR(10)," +
                    "contact VARCHAR(100))");

            stmt.execute("CREATE TABLE IF NOT EXISTS training_sessions (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "client_id INT NOT NULL," +
                    "trainer_id INT NOT NULL," +
                    "session_time TIMESTAMP NOT NULL," +
                    "duration INT," +
                    "status VARCHAR(20))");

            stmt.execute("CREATE TABLE IF NOT EXISTS exercises (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(100) NOT NULL," +
                    "description VARCHAR(255))");

            stmt.execute("CREATE TABLE IF NOT EXISTS workout_plans (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "client_id INT NOT NULL," +
                    "plan_name VARCHAR(100) NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS plan_exercises (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "plan_id INT NOT NULL," +
                    "exercise_id INT NOT NULL," +
                    "sets INT," +
                    "reps INT," +
                    "exercise_order INT)");

            stmt.execute("CREATE TABLE IF NOT EXISTS workout_progress (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "plan_id INT NOT NULL," +
                    "exercise_id INT NOT NULL," +
                    "progress_date DATE NOT NULL," +
                    "actual_sets INT," +
                    "actual_reps INT," +
                    "notes VARCHAR(255))");

            stmt.execute("CREATE TABLE IF NOT EXISTS attendance (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "client_id INT NOT NULL," +
                    "session_id INT," +
                    "attended BOOLEAN," +
                    "attendance_date DATE NOT NULL)");

            addDefaultExercises(stmt);
            addTestUsers(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addDefaultExercises(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM exercises");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.execute("INSERT INTO exercises (name, description) VALUES ('Жим лёжа', 'Грудные мышцы')");
            stmt.execute("INSERT INTO exercises (name, description) VALUES ('Приседания', 'Ноги и ягодицы')");
            stmt.execute("INSERT INTO exercises (name, description) VALUES ('Тяга штанги', 'Спина')");
            stmt.execute("INSERT INTO exercises (name, description) VALUES ('Отжимания', 'Грудь, трицепс')");
        }
        rs.close();
    }

    private static void addTestUsers(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
        rs.next();
        if (rs.getInt(1) == 0) {
            stmt.execute("INSERT INTO users (username, password, role, name, age, gender, contact) VALUES ('admin', 'admin123', 'ADMIN', 'Главный админ', 30, 'Мужской', 'admin@fitness.com')");
            stmt.execute("INSERT INTO users (username, password, role, name, age, gender, contact) VALUES ('trainer', 'trainer123', 'TRAINER', 'Иван Тренер', 28, 'Мужской', 'trainer@fitness.com')");
            stmt.execute("INSERT INTO users (username, password, role, name, age, gender, contact) VALUES ('client', 'client123', 'CLIENT', 'Анна Клиент', 25, 'Женский', 'client@fitness.com')");
        }
        rs.close();
    }
}