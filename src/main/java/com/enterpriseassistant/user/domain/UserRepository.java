package com.enterpriseassistant.user.domain;

import java.util.List;
import java.util.Optional;

interface UserRepository {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Integer id);

    void deleteById(Integer id);

    User save(User user);

    User enableAppUser(String email);

    List<User> findAll();
}

