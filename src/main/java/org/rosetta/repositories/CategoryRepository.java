package org.rosetta.repositories;

import org.rosetta.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findById(int id);
    Optional<Category> findByName(String name);
    Category save(Category category);
    void delete(int id);
}
