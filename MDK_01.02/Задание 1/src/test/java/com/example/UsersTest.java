package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UsersTest {
    private Users users;

    @BeforeEach
    void setUp() {
        users = new Users();
        users.addUser("Анна", 25, User.Gender.FEMALE);
        users.addUser("Иван", 30, User.Gender.MALE);
        users.addUser("Мария", 22, User.Gender.FEMALE);
        users.addUser("Петр", 35, User.Gender.MALE);
    }

    @Test
    void testAddUserAndIdAutoIncrement() {
        List<User> all = users.getAllUsers();
        assertEquals(4, all.size());
        assertEquals(1, all.get(0).getId());
        assertEquals(2, all.get(1).getId());
        assertEquals(3, all.get(2).getId());
        assertEquals(4, all.get(3).getId());
    }

    @Test
    void testGetMaleUsers() {
        List<User> males = users.getMaleUsers();
        assertEquals(2, males.size());
        assertTrue(males.stream().allMatch(u -> u.getGender() == User.Gender.MALE));
    }

    @Test
    void testGetFemaleUsers() {
        List<User> females = users.getFemaleUsers();
        assertEquals(2, females.size());
        assertTrue(females.stream().allMatch(u -> u.getGender() == User.Gender.FEMALE));
    }

    @Test
    void testGetTotalCount() {
        assertEquals(4, users.getTotalCount());
    }

    @Test
    void testGetAverageAge() {
        double avg = users.getAverageAge();
        double expected = (25 + 30 + 22 + 35) / 4.0;
        assertEquals(expected, avg, 0.001);
    }

    @Test
    void testPrintAllUsers() {
        // Просто проверяем, что метод не падает с ошибкой
        assertDoesNotThrow(() -> users.printAllUsers());
    }
}