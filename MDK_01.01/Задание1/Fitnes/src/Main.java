import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Client client1 = new Client("Иван", "Петров", 1990);
        Client client2 = new Client("Мария", "Сидорова", 1991);
        Client client3 = new Client("Алексей", "Павлов", 2009);

        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(30);

        SingleMembership singleMembership = new SingleMembership(client1, today, today);
        DayMembership dayMembership = new DayMembership(client2, today, futureDate);
        FullMembership fullMembership = new FullMembership(client3, today, futureDate);

        FitnessClub club = new FitnessClub();

        LocalTime currentTime = LocalTime.of(10, 0);

        System.out.println("=== Попытка посещений в 10:00 ===");
        club.registerVisit(singleMembership, "gym", today, currentTime);
        club.registerVisit(singleMembership, "pool", today, currentTime);
        club.registerVisit(singleMembership, "group_class", today, currentTime);

        club.registerVisit(dayMembership, "gym", today, currentTime);
        club.registerVisit(dayMembership, "pool", today, currentTime);
        club.registerVisit(dayMembership, "group_class", today, currentTime);

        club.registerVisit(fullMembership, "gym", today, currentTime);
        club.registerVisit(fullMembership, "pool", today, currentTime);
        club.registerVisit(fullMembership, "group_class", today, currentTime);

        club.printCurrentVisitors();

        System.out.println("\n=== Попытка повторной регистрации ===");
        club.registerVisit(fullMembership, "gym", today, currentTime);

        LocalTime eveningTime = LocalTime.of(18, 0);
        System.out.println("\n=== Попытка посещения в 18:00 ===");
        club.registerVisit(dayMembership, "gym", today, eveningTime);

        club.closeClub();
        club.printCurrentVisitors();
    }
}