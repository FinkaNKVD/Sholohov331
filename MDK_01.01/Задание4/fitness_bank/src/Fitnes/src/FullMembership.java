package Fitnes.src;

import java.time.LocalDate;
import java.time.LocalTime;

public class FullMembership {
    private Client owner;
    private LocalDate startDate;
    private LocalDate endDate;

    public FullMembership(Client owner, LocalDate startDate, LocalDate endDate) {
        this.owner = owner;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isValid(LocalDate currentDate) {
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    public boolean hasAccessToGym(LocalTime currentTime) {
        return !currentTime.isBefore(LocalTime.of(8, 0)) && !currentTime.isAfter(LocalTime.of(22, 0));
    }

    public boolean hasAccessToPool(LocalTime currentTime) {
        return !currentTime.isBefore(LocalTime.of(8, 0)) && !currentTime.isAfter(LocalTime.of(22, 0));
    }

    public boolean hasAccessToGroupClass(LocalTime currentTime) {
        return !currentTime.isBefore(LocalTime.of(8, 0)) && !currentTime.isAfter(LocalTime.of(22, 0));
    }

    public Client getOwner() { return owner; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    @Override
    public String toString() {
        return "FullMembership for " + owner + " (" + startDate + " to " + endDate + ")";
    }
}