package com.fitness.models;

import java.time.LocalDate;

public class WorkoutProgress {
    private int id;
    private int planId;
    private int exerciseId;
    private LocalDate date;
    private int actualSets;
    private int actualReps;
    private String notes;
    public WorkoutProgress() {}
    // геттеры и сеттеры (сгенерируйте через IDEA)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }
    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public int getActualSets() { return actualSets; }
    public void setActualSets(int actualSets) { this.actualSets = actualSets; }
    public int getActualReps() { return actualReps; }
    public void setActualReps(int actualReps) { this.actualReps = actualReps; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}