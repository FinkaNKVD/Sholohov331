package com.fitness.models;

import java.time.LocalDate;

public class Attendance {
    private int id;
    private int clientId;
    private Integer sessionId;
    private boolean attended;
    private LocalDate date;
    public Attendance() {}
    // геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public Integer getSessionId() { return sessionId; }
    public void setSessionId(Integer sessionId) { this.sessionId = sessionId; }
    public boolean isAttended() { return attended; }
    public void setAttended(boolean attended) { this.attended = attended; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}