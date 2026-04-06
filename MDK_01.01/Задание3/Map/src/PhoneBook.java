import java.util.*;
import java.util.regex.Pattern;

public class PhoneBook {
    private final Map<String, String> nameToPhone = new HashMap<>();
    private final Map<String, String> phoneToName = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    private static final Pattern DIGITS_ONLY = Pattern.compile("\\d+");

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        System.out.println("Телефонная книга. Введите имя, номер, LIST или EXIT:");

        while (true) {
            System.out.print("> ");
            String input = phoneBook.scanner.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT")) {
                break;
            }
            if (input.equalsIgnoreCase("LIST")) {
                phoneBook.listAll();
                continue;
            }

            if (isPhoneNumber(input)) {
                phoneBook.processByPhone(input);
            } else {
                phoneBook.processByName(input);
            }
        }

        phoneBook.scanner.close();
    }

    // После удаления пробелов/знаков остаются только цифры
    private static boolean isPhoneNumber(String s) {
        String cleaned = s.replaceAll("[\\s\\-+]", "");
        return DIGITS_ONLY.matcher(cleaned).matches();
    }

    private void processByName(String name) {
        String existingPhone = nameToPhone.get(name);
        if (existingPhone != null) {
            System.out.println("Имя: " + name + ", Телефон: " + existingPhone);
            return;
        }

        String phone = readTrimmedLine("Введите номер телефона для " + name + ": ");
        if (!isPhoneNumber(phone)) {
            System.out.println("Некорректный номер. Операция отменена.");
            return;
        }

        String usedBy = phoneToName.get(phone);
        if (usedBy != null) {
            System.out.println("Этот номер уже принадлежит " + usedBy);
            return;
        }

        nameToPhone.put(name, phone);
        phoneToName.put(phone, name);
        System.out.println("Контакт добавлен.");
    }

    private void processByPhone(String phone) {
        String existingName = phoneToName.get(phone);
        if (existingName != null) {
            System.out.println("Телефон: " + phone + ", Имя: " + existingName);
            return;
        }

        String name = readTrimmedLine("Введите имя для номера " + phone + ": ");
        if (name.isEmpty()) {
            System.out.println("Имя не может быть пустым.");
            return;
        }

        String alreadyUsedPhone = nameToPhone.get(name);
        if (alreadyUsedPhone != null) {
            System.out.println("Это имя уже используется для номера " + alreadyUsedPhone);
            return;
        }

        nameToPhone.put(name, phone);
        phoneToName.put(phone, name);
        System.out.println("Контакт добавлен.");
    }

    private void listAll() {
        if (nameToPhone.isEmpty()) {
            System.out.println("Телефонная книга пуста.");
            return;
        }

        List<String> sortedNames = new ArrayList<>(nameToPhone.keySet());
        Collections.sort(sortedNames);

        System.out.println("Абоненты:");
        for (String name : sortedNames) {
            System.out.println(name + " : " + nameToPhone.get(name));
        }
    }

    private String readTrimmedLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}