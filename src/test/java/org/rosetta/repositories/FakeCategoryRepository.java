package org.rosetta.repositories;

import org.rosetta.model.Category;

import java.util.*;

public class FakeCategoryRepository implements CategoryRepository {
    private int nextId = 1;
    private final Map<Integer, Category> categories = new HashMap<>();

    @Override
    public List<Category> findAll() {
        return new ArrayList<>(categories.values());
    }

    @Override
    public Optional<Category> findById(int id) {
        return Optional.ofNullable(categories.get(id));
    }

    @Override
    public Optional<Category> findByName(String name) {
        for(Category category : categories.values()) {
            if(category.getName().equals(name)) {
                return Optional.of(category);
            }
        }
        return Optional.empty();
    }

    @Override
    public Category save(Category category) {
        Category saved = new Category(nextId, category.getName());
        categories.put(nextId, saved);
        nextId++;
        return saved;
    }

    @Override
    public void delete(int id) {
        categories.remove(id);
    }
}
