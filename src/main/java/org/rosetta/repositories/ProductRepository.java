package org.rosetta.repositories;

import org.rosetta.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAll(int page, int size);
    List<Product> findByName(String name);
    List<Product> findByCategory(int categoryId);
    Optional<Product> findById(int id);
    Product save(Product product);
    boolean delete(int id);
}
