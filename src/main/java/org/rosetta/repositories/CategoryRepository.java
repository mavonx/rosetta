package org.rosetta.repositories;

import org.rosetta.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> findAll();
    Optional<Category> findByName(String name);
    Optional<Category> findById(int id);
    Category save(Category category);
    boolean delete(int id);
}
