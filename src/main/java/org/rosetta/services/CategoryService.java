package org.rosetta.services;

import org.rosetta.model.Category;
import org.rosetta.repositories.CategoryRepository;

import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category create(Category category) {
        if(categoryRepository.findByName(category.getName()).isPresent())
            throw new IllegalStateException("Category name already exists");
        return categoryRepository.save(category);
    }

    public void delete(int id) {
        if(categoryRepository.findById(id).isEmpty())
            throw new IllegalArgumentException("No category with id = " + id);
        categoryRepository.delete(id);
    }
}
