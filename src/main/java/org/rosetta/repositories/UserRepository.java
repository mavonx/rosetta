package org.rosetta.repositories;

import org.rosetta.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    List<User> findAll(int page, int size);
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(int id);
}
