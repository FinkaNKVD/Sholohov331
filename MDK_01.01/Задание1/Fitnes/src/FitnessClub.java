import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FitnessClub {
    private List<Object> gymRegistry;
    private List<Object> poolRegistry;
    private List<Object> groupClassRegistry;

    public FitnessClub() {
        this.gymRegistry = new ArrayList<>();
        this.poolRegistry = new ArrayList<>();
        this.groupClassRegistry = new ArrayList<>();
    }

    public boolean registerVisit(Object membership, String zone, LocalDate currentDate, LocalTime currentTime) {
        if (membership instanceof SingleMembership) {
            return processVisit((SingleMembership) membership, zone, currentDate, currentTime);
        } else if (membership instanceof DayMembership) {
            return processVisit((DayMembership) membership, zone, currentDate, currentTime);
        } else if (membership instanceof FullMembership) {
            return processVisit((FullMembership) membership, zone, currentDate, currentTime);
        } else {
            System.out.println("Ошибка: Неизвестный тип абонемента.");
            return false;
        }
    }

    private boolean processVisit(SingleMembership membership, String zone, LocalDate currentDate, LocalTime currentTime) {
        if (!membership.isValid(currentDate)) {
            System.out.println("Ошибка: Абонемент " + membership + " просрочен.");
            return false;
        }

        switch (zone) {
            case "gym":
                if (!membership.hasAccessToGym(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ в тренажерный зал в это время.");
                    return false;
                }
                if (gymRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже в тренажерном зале.");
                    return false;
                }
                gymRegistry.add(membership);
                System.out.println("Клиент зарегистрирован в тренажерном зале.");
                return true;

            case "pool":
                if (!membership.hasAccessToPool(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ в бассейн в это время.");
                    return false;
                }
                if (poolRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже в бассейне.");
                    return false;
                }
                poolRegistry.add(membership);
                System.out.println("Клиент зарегистрирован в бассейне.");
                return true;

            case "group_class":
                if (!membership.hasAccessToGroupClass(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ на групповые занятия в это время.");
                    return false;
                }
                if (groupClassRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже на групповых занятиях.");
                    return false;
                }
                groupClassRegistry.add(membership);
                System.out.println("Клиент зарегистрирован на групповых занятиях.");
                return true;

            default:
                System.out.println("Ошибка: Неизвестная зона '" + zone + "'.");
                return false;
        }
    }

    private boolean processVisit(DayMembership membership, String zone, LocalDate currentDate, LocalTime currentTime) {
        if (!membership.isValid(currentDate)) {
            System.out.println("Ошибка: Абонемент " + membership + " просрочен.");
            return false;
        }

        switch (zone) {
            case "gym":
                if (!membership.hasAccessToGym(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ в тренажерный зал в это время.");
                    return false;
                }
                if (gymRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже в тренажерном зале.");
                    return false;
                }
                gymRegistry.add(membership);
                System.out.println("Клиент зарегистрирован в тренажерном зале.");
                return true;

            case "pool":
                if (!membership.hasAccessToPool(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ в бассейн.");
                    return false;
                }
                if (poolRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже в бассейне.");
                    return false;
                }
                poolRegistry.add(membership);
                System.out.println("Клиент зарегистрирован в бассейне.");
                return true;

            case "group_class":
                if (!membership.hasAccessToGroupClass(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ на групповые занятия в это время.");
                    return false;
                }
                if (groupClassRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже на групповых занятиях.");
                    return false;
                }
                groupClassRegistry.add(membership);
                System.out.println("Клиент зарегистрирован на групповых занятиях.");
                return true;

            default:
                System.out.println("Ошибка: Неизвестная зона '" + zone + "'.");
                return false;
        }
    }

    private boolean processVisit(FullMembership membership, String zone, LocalDate currentDate, LocalTime currentTime) {
        if (!membership.isValid(currentDate)) {
            System.out.println("Ошибка: Абонемент " + membership + " просрочен.");
            return false;
        }

        switch (zone) {
            case "gym":
                if (!membership.hasAccessToGym(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ в тренажерный зал в это время.");
                    return false;
                }
                if (gymRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже в тренажерном зале.");
                    return false;
                }
                gymRegistry.add(membership);
                System.out.println("Клиент зарегистрирован в тренажерном зале.");
                return true;

            case "pool":
                if (!membership.hasAccessToPool(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ в бассейн в это время.");
                    return false;
                }
                if (poolRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже в бассейне.");
                    return false;
                }
                poolRegistry.add(membership);
                System.out.println("Клиент зарегистрирован в бассейне.");
                return true;

            case "group_class":
                if (!membership.hasAccessToGroupClass(currentTime)) {
                    System.out.println("Ошибка: Абонемент не дает доступ на групповые занятия в это время.");
                    return false;
                }
                if (groupClassRegistry.contains(membership)) {
                    System.out.println("Ошибка: Клиент уже на групповых занятиях.");
                    return false;
                }
                groupClassRegistry.add(membership);
                System.out.println("Клиент зарегистрирован на групповых занятиях.");
                return true;

            default:
                System.out.println("Ошибка: Неизвестная зона '" + zone + "'.");
                return false;
        }
    }

    public void closeClub() {
        System.out.println("\n--- Фитнес-клуб закрывается! Все клиенты покидают залы. ---");
        gymRegistry.clear();
        poolRegistry.clear();
        groupClassRegistry.clear();
        System.out.println("Все зоны очищены.");
    }

    public void printCurrentVisitors() {
        System.out.println("\n--- Текущие посетители ---");

        System.out.print("Тренажерный зал (" + gymRegistry.size() + "): ");
        for (Object membership : gymRegistry) {
            printClientName(membership);
        }
        System.out.println();

        System.out.print("Бассейн (" + poolRegistry.size() + "): ");
        for (Object membership : poolRegistry) {
            printClientName(membership);
        }
        System.out.println();

        System.out.print("Групповые занятия (" + groupClassRegistry.size() + "): ");
        for (Object membership : groupClassRegistry) {
            printClientName(membership);
        }
        System.out.println();
    }

    private void printClientName(Object membership) {
        if (membership instanceof SingleMembership) {
            System.out.print(((SingleMembership) membership).getOwner().getFirstName() + " ");
        } else if (membership instanceof DayMembership) {
            System.out.print(((DayMembership) membership).getOwner().getFirstName() + " ");
        } else if (membership instanceof FullMembership) {
            System.out.print(((FullMembership) membership).getOwner().getFirstName() + " ");
        }
    }
}