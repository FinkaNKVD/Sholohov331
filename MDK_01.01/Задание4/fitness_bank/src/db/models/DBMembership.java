package db.models;

import java.time.LocalDate;

public class DBMembership {
    private int id;
    private int userId;
    private String membershipType; // SINGLE, DAY, FULL
    private LocalDate startDate;
    private LocalDate endDate;
    private String createdAt;

    // Конструкторы
    public DBMembership() {}

    public DBMembership(int userId, String membershipType, LocalDate startDate, LocalDate endDate) {
        this.userId = userId;
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DBMembership(int id, int userId, String membershipType, LocalDate startDate, LocalDate endDate, String createdAt) {
        this.id = id;
        this.userId = userId;
        this.membershipType = membershipType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Membership{" + userId + ", " + membershipType + ", " + startDate + " to " + endDate + "}";
    }
}