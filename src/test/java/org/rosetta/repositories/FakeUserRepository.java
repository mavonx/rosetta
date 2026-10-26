package org.rosetta.repositories;

import org.rosetta.model.User;

import java.util.*;

public class FakeUserRepository implements UserRepository {
    private int nextId = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> findAll(int page, int size) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        for(User user : users.values()) {
            if(user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        User saved = new User(nextId, user.getName(), user.getEmail(), user.getPassword());
        users.put(nextId, saved);
        nextId++;
        return saved;
    }

    @Override
    public void delete(int id) {
        users.remove(id);
    }
}
