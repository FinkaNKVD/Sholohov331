package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Users {
    private final List<User> userList = new ArrayList<>();
    private int nextId = 1;

    // Добавление пользователя
    public void addUser(String name, int age, User.Gender gender) {
        final int id = nextId++;
        userList.add(new User(id, name, age, gender));
    }

    // Вывод всех пользователей в консоль
    public void printAllUsers() {
        userList.forEach(System.out::println);
    }

    // Получить список всех пользователей (для тестов)
    public List<User> getAllUsers() {
        return new ArrayList<>(userList);
    }

    private List<User> getUsersByGender(User.Gender gender) {
        return userList.stream()
                .filter(u -> u.getGender() == gender)
                .collect(Collectors.toList());
    }

    // Фильтр по полу: мужчины
    public List<User> getMaleUsers() {
        return getUsersByGender(User.Gender.MALE);
    }

    // Фильтр по полу: женщины
    public List<User> getFemaleUsers() {
        return getUsersByGender(User.Gender.FEMALE);
    }

    // Общее количество пользователей
    public int getTotalCount() {
        return userList.size();
    }

    // Средний возраст
    public double getAverageAge() {
        return userList.stream()
                .mapToInt(User::getAge)
                .average()
                .orElse(0.0);
    }
}