import java.time.LocalDate;
import java.time.LocalTime;

public class SingleMembership {
    private static final LocalTime OPEN_TIME = LocalTime.of(8, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(22, 0);

    private final Client owner;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public SingleMembership(Client owner, LocalDate startDate, LocalDate endDate) {
        this.owner = owner;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isValid(LocalDate currentDate) {
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    public boolean hasAccessToGym(LocalTime currentTime) {
        return isWithinWorkingHours(currentTime);
    }

    public boolean hasAccessToPool(LocalTime currentTime) {
        return isWithinWorkingHours(currentTime);
    }

    public boolean hasAccessToGroupClass(LocalTime currentTime) {
        return false;
    }

    private static boolean isWithinWorkingHours(LocalTime time) {
        return !time.isBefore(OPEN_TIME) && !time.isAfter(CLOSE_TIME);
    }

    public Client getOwner() { return owner; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    @Override
    public String toString() {
        return "SingleMembership for " + owner + " (" + startDate + " to " + endDate + ")";
    }
}