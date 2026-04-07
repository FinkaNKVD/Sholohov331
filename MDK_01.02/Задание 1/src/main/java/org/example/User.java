package com.example;

public class User {
    private final int id;
    private final String name;
    private final int age;
    private final Gender gender;

    public enum Gender {
        MALE, FEMALE
    }

    public User(int id, String name, int age, Gender gender) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public Gender getGender() { return gender; }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', age=%d, gender=%s}", id, name, age, gender);
    }
}