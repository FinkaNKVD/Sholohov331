import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        List<Message> messages = RandomMessageGenerator.generateMessages(20);
        System.out.println("Сгенерированные сообщения:");
        messages.forEach(System.out::println);

        // 3. Только неповторяющиеся в порядке добавления
        List<Message> unique = getUniqueInOrder(messages);
        System.out.println("\nУникальные сообщения (в порядке первого появления):");
        unique.forEach(System.out::println);

        // 4. Удалить сообщения с заданным приоритетом
        Priority target = Priority.HIGH;
        System.out.println("\nДо удаления приоритета " + target + ":");
        messages.forEach(System.out::println);
        removeByPriority(messages, target);
        System.out.println("\nПосле удаления " + target + ":");
        messages.forEach(System.out::println);

        // 5. Оставить только сообщения с заданным приоритетом
        // Сбросим список для демонстрации
        messages = RandomMessageGenerator.generateMessages(20);
        Priority keep = Priority.URGENT;
        System.out.println("\nДо фильтрации (оставить только " + keep + "):");
        messages.forEach(System.out::println);
        keepOnlyPriority(messages, keep);
        System.out.println("\nПосле фильтрации:");
        messages.forEach(System.out::println);

        // 6. Количество по приоритетам
        Map<Priority, Integer> byPriority = countByPriority(messages);
        System.out.println("\nКоличество сообщений по приоритетам:");
        byPriority.forEach((p, cnt) -> System.out.println(p + ": " + cnt));

        // 7. Количество по кодам
        Map<Integer, Integer> byCode = countByCode(messages);
        System.out.println("\nКоличество сообщений по кодам:");
        byCode.forEach((code, cnt) -> System.out.println(code + ": " + cnt));

        // 8. Количество уникальных сообщений
        int uniqueCount = countUnique(messages);
        System.out.println("\nКоличество уникальных сообщений: " + uniqueCount);
    }

    // 3. Вернуть неповторяющиеся в порядке первого появления
    public static List<Message> getUniqueInOrder(List<Message> messages) {
        return messages.stream()
                .distinct() // сохраняет порядок первого появления
                .collect(Collectors.toList());
    }

    // 4. Удалить все сообщения с заданным приоритетом
    public static void removeByPriority(List<Message> messages, Priority priority) {
        for (Iterator<Message> it = messages.iterator(); it.hasNext(); ) {
            if (it.next().getPriority() == priority) {
                it.remove();
            }
        }
    }

    // 5. Удалить все, кроме сообщений с заданным приоритетом
    public static void keepOnlyPriority(List<Message> messages, Priority priority) {
        for (Iterator<Message> it = messages.iterator(); it.hasNext(); ) {
            if (it.next().getPriority() != priority) {
                it.remove();
            }
        }
    }

    // 6. Подсчет по приоритетам
    public static Map<Priority, Integer> countByPriority(List<Message> messages) {
        Map<Priority, Long> tmp = messages.stream()
                .collect(Collectors.groupingBy(
                        Message::getPriority,
                        () -> new EnumMap<>(Priority.class),
                        Collectors.counting()
                ));

        Map<Priority, Integer> result = new EnumMap<>(Priority.class);
        tmp.forEach((p, cnt) -> result.put(p, cnt.intValue()));
        return result;
    }

    // 7. Подсчет по кодам
    public static Map<Integer, Integer> countByCode(List<Message> messages) {
        Map<Integer, Long> tmp = messages.stream()
                .collect(Collectors.groupingBy(
                        Message::getCode,
                        HashMap::new,
                        Collectors.counting()
                ));

        Map<Integer, Integer> result = new HashMap<>();
        tmp.forEach((code, cnt) -> result.put(code, cnt.intValue()));
        return result;
    }

    // 8. Количество уникальных сообщений
    public static int countUnique(List<Message> messages) {
        return (int) messages.stream().distinct().count();
    }
}

// Перечисление приоритетов
enum Priority {
    LOW, MEDIUM, HIGH, URGENT
}

// Класс сообщения
class Message {
    private final Priority priority;
    private final int code;
    private final String text;

    public Message(Priority priority, int code, String text) {
        this.priority = priority;
        this.code = code;
        this.text = text;
    }

    public Priority getPriority() { return priority; }
    public int getCode() { return code; }
    public String getText() { return text; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return code == message.code &&
                priority == message.priority &&
                Objects.equals(text, message.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority, code, text);
    }

    @Override
    public String toString() {
        return String.format("Message{priority=%s, code=%d, text='%s'}", priority, code, text);
    }
}

// Вспомогательный класс для генерации случайных сообщений
class RandomMessageGenerator {
    private static final Random RANDOM = new Random();
    private static final String[] TEXTS = {
            "Hello", "World", "Java", "Collection", "Priority",
            "Message", "Code", "Random", "Test", "Example"
    };

    public static List<Message> generateMessages(int count) {
        List<Message> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Priority priority = Priority.values()[RANDOM.nextInt(Priority.values().length)];
            int code = RANDOM.nextInt(10) + 1; // коды от 1 до 10
            String text = TEXTS[RANDOM.nextInt(TEXTS.length)];
            list.add(new Message(priority, code, text));
        }
        return list;
    }
}