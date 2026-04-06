package com.fitness.models;

public class PlanExercise {
    private int id;
    private int planId;
    private int exerciseId;
    private int sets;
    private int reps;
    private int order;
    public PlanExercise() {}
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }
    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
    public int getReps() { return reps; }
    public void setReps(int reps) { this.reps = reps; }
    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }
}