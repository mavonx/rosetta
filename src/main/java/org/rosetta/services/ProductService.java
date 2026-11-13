package org.rosetta.services;

import org.rosetta.model.Product;
import org.rosetta.repositories.ProductRepository;
import org.rosetta.repositories.CategoryRepository;

import java.util.List;
import java.math.BigDecimal;

public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public ProductService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getAll(int page, int size) {
        if(page < 1 || size < 1)
            throw new IllegalArgumentException("Page and size must be at least 1");

        return productRepository.findAll(page, size);
    }

    public List<Product> searchByName(String name) {
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Product name cannot be empty");

        return productRepository.findByName(name.trim());
    }

    public List<Product> getByCategory(int categoryId) {
        if(categoryId < 1)
            throw new IllegalArgumentException("Category ID must be positive");

        return productRepository.findByCategory(categoryId);
    }

    public Product create(Product product) {
        if(product == null)
            throw new IllegalArgumentException("Product cannot be null");

        if(product.getName() == null || product.getName().isBlank())
            throw new IllegalArgumentException("Product name cannot be empty");

        if(product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Product price must be greater than zero");

        if(product.getStock() < 0)
            throw new IllegalArgumentException("Product stock cannot be negative");

        if(product.getCategoryId() < 1)
            throw new IllegalArgumentException("Category ID must be positive");

        if(categoryRepository.findById(product.getCategoryId()).isEmpty())
            throw new IllegalArgumentException("Category does not exist");

        product.setName(product.getName().trim());

        return productRepository.save(product);
    }

    public void delete(int id) {
        if(id < 1)
            throw new IllegalArgumentException("Product ID must be positive");

        if(productRepository.findById(id).isEmpty())
            throw new IllegalArgumentException("Product does not exist");

        productRepository.delete(id);
    }
}
