package com.fitness.models;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private String name;
    private int age;
    private String gender;
    private String contact;

    public User() {}
    public User(int id, String username, String password, String role, String name, int age, String gender, String contact) {
        this.id = id; this.username = username; this.password = password; this.role = role;
        this.name = name; this.age = age; this.gender = gender; this.contact = contact;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}