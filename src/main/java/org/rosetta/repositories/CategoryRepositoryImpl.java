package org.rosetta.repositories;

import org.rosetta.model.Category;
import org.rosetta.ConnectionManager;
import org.rosetta.repositories.mapping.RowMappers;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.rosetta.repositories.mapping.RowMappers.*;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public List<Category> findAll() {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT " + CATEGORY_COLUMNS + " FROM categories ORDER BY id")
        ) {
            return toList(ps, RowMappers::toCategory);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public Optional<Category> findByName(String name) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT " + CATEGORY_COLUMNS + " FROM categories WHERE name = ?");
        ) {
            ps.setString(1, name);
            return toOptional(ps, RowMappers::toCategory);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public Optional<Category> findById(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT " + CATEGORY_COLUMNS + " FROM categories WHERE id = ?")
        ) {
            ps.setInt(1, id);
            return toOptional(ps, RowMappers::toCategory);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public Category save(Category category) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO categories (name) VALUES (?) RETURNING " + CATEGORY_COLUMNS)
        ) {
            ps.setString(1, category.getName());
            return toSingle(ps, RowMappers::toCategory);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public boolean delete(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM categories WHERE id = ?")
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }
}
