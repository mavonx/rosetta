package org.rosetta.repositories;

import org.rosetta.model.Product;
import org.rosetta.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, price, stock, category_id FROM products")
        ) {
            while(rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBigDecimal("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id")
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return products;
    }

    @Override
    public List<Product> findAll(int page, int size) {
        if(page < 1 || size < 1)
            throw new IllegalArgumentException("Page and size must be at least 1");

        int offset = (page - 1) * size;

        List<Product> products = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, price, stock, category_id FROM products ORDER BY id LIMIT " + size + " OFFSET " + offset)
        ) {
            while(rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBigDecimal("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id")
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return products;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> products = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT id, name, price, stock, category_id FROM products WHERE name ILIKE ? ORDER BY id")
        ) {
            ps.setString(1, "%" + name + "%");
            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                    ));
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return products;
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, price, stock, category_id FROM products WHERE category_id = " + categoryId)
        ) {
            while(rs.next()) {
                products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBigDecimal("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id")
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, price, stock, category_id FROM products WHERE id = " + id)
        ) {
            if(rs.next()) {
                return Optional.of(
                    new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("stock"),
                        rs.getInt("category_id")
                    )
                );
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?) RETURNING id, name, price, stock, category_id")
        ) {
            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setInt(4, product.getCategoryId());
            try(ResultSet rs = ps.executeQuery()) {
                rs.next();
                return new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getBigDecimal("price"),
                    rs.getInt("stock"),
                    rs.getInt("category_id")
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
            st.executeUpdate("DELETE FROM products WHERE id = " + id);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }
}
