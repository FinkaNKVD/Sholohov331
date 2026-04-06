package com.fitness.models;

public class WorkoutPlan {
    private int id;
    private int clientId;
    private String planName;
    public WorkoutPlan() {}
    public WorkoutPlan(int id, int clientId, String planName) { this.id = id; this.clientId = clientId; this.planName = planName; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
}