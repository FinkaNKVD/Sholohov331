package com.fitness.models;

import java.time.LocalDateTime;

public class TrainingSession {
    private int id;
    private int clientId;
    private int trainerId;
    private LocalDateTime sessionTime;
    private int duration;
    private String status;

    public TrainingSession() {}
    public TrainingSession(int id, int clientId, int trainerId, LocalDateTime sessionTime, int duration, String status) {
        this.id = id; this.clientId = clientId; this.trainerId = trainerId;
        this.sessionTime = sessionTime; this.duration = duration; this.status = status;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public int getTrainerId() { return trainerId; }
    public void setTrainerId(int trainerId) { this.trainerId = trainerId; }
    public LocalDateTime getSessionTime() { return sessionTime; }
    public void setSessionTime(LocalDateTime sessionTime) { this.sessionTime = sessionTime; }
    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}