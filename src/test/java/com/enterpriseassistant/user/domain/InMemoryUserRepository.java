package com.enterpriseassistant.user.domain;

import com.enterpriseassistant.user.exception.UserNotFound;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {

    Map<Integer, User> inMemoryUserDatabase = new ConcurrentHashMap<>();
    private static final Integer ACCOUNT_ID = 3;

    private static final User USER_ADMIN = new User(1, "admin@admin.com", "admin",
            "admin", "Admin Admin", User.Role.ADMIN, true);
    private static final User USER2 = new User(2, "email1@example.com", "user2",
            "password1", "User Second", User.Role.USER, true);

    public InMemoryUserRepository() {
        inMemoryUserDatabase.put(1, USER_ADMIN);
        inMemoryUserDatabase.put(2, USER2);
    }

    @Override
    public boolean existsByUsername(String username) {
        return inMemoryUserDatabase.values()
                .stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return inMemoryUserDatabase.values()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return inMemoryUserDatabase.values()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return inMemoryUserDatabase.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(inMemoryUserDatabase.get(id));
    }

    @Override
    public void deleteById(Integer id) {
        inMemoryUserDatabase.remove(id);
    }

    @Override
    public User save(User user) {
        user.setId(ACCOUNT_ID);
        inMemoryUserDatabase.put(ACCOUNT_ID, user);
        return user;
    }

    @Override
    public User enableAppUser(String email) {
        User user = inMemoryUserDatabase.values()
                .stream()
                .filter(user1 -> user1.getEmail().equals(email))
                .findFirst().orElseThrow(UserNotFound::new);
        user.setEnabled(true);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(inMemoryUserDatabase.values());
    }
}
