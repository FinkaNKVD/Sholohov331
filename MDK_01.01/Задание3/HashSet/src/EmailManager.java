import java.util.*;
import java.util.regex.Pattern;

public class EmailManager {
    private final Set<String> emails = new TreeSet<>(); // автоматическая сортировка
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static void main(String[] args) {
        EmailManager manager = new EmailManager();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Команды: add <email> , list , exit");

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) {
                break;
            }
            manager.handleCommand(line);
        }
        scanner.close();
    }

    private void handleCommand(String line) {
        String lower = line.toLowerCase(Locale.ROOT);

        if (lower.startsWith("add ")) {
            String email = line.substring(4).trim();
            addEmail(email);
            return;
        }

        if (lower.equals("list")) {
            listEmails();
            return;
        }

        System.out.println("Неизвестная команда. Используйте: add <email>, list, exit");
    }

    public void addEmail(String email) {
        if (!isValidEmail(email)) {
            System.out.println("Ошибка: некорректный email.");
            return;
        }
        if (emails.add(email)) {
            System.out.println("Email добавлен.");
        } else {
            System.out.println("Такой email уже существует.");
        }
    }

    public void listEmails() {
        if (emails.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        System.out.println("Список email'ов:");
        for (String email : emails) {
            System.out.println(email);
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
}