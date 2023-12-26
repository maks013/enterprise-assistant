package com.enterpriseassistant.user.domain;

import com.enterpriseassistant.user.exception.UserNotFound;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryUserRepository implements UserRepository {

    Map<Integer, User> inMemoryUserDatabase = new ConcurrentHashMap<>();
    private static final Integer ACCOUNT_ID = 3;

    User USER_ADMIN = new User(1, "admin@admin.com", "admin",
            "admin", "Admin Admin", User.Role.ADMIN, true);
    User USER2 = new User(2, "email1@example.com", "user2",
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
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void deleteById(Integer id) {
        inMemoryUserDatabase.remove(id);
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public User save(User user) {
        user.setId(ACCOUNT_ID);
        inMemoryUserDatabase.put(ACCOUNT_ID, user);
        return user;
    }

    @Override
    public void enableAppUser(Integer userId) {
        User user = inMemoryUserDatabase.get(userId);
        user.setEnabled(true);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(inMemoryUserDatabase.values());
    }

    @Override
    public List<User> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<User> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(Integer integer) {
        return null;
    }

    @Override
    public User getById(Integer integer) {
        return null;
    }

    @Override
    public User getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
