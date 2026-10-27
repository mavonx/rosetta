package org.rosetta.repositories;

import org.rosetta.model.Category;
import org.rosetta.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name FROM categories")
        ) {
            while(rs.next()) {
                categories.add(new Category(
                    rs.getInt("id"),
                    rs.getString("name")
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return categories;
    }

    @Override
    public Optional<Category> findById(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name FROM categories WHERE id = " + id)
        ) {
            if(rs.next()) {
                return Optional.of(new Category(
                    rs.getInt("id"),
                    rs.getString("name")
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Category> findByName(String name) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT id, name FROM categories WHERE name = ?");
        ) {
            ps.setString(1, name);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                    ));
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return Optional.empty();
    }

    @Override
    public Category save(Category category) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO categories (name) VALUES (?) RETURNING id, name")
        ) {
            ps.setString(1, category.getName());
            try(ResultSet rs = ps.executeQuery()) {
                rs.next();
                return new Category(
                    rs.getInt("id"),
                    rs.getString("name")
                );
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public void delete(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement()
        ) {
            st.executeUpdate("DELETE FROM categories WHERE id = " + id);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }
}
