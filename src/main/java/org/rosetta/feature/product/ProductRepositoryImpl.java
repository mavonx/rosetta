package org.rosetta.feature.product;

import org.rosetta.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.rosetta.common.persistence.RowMappers.*;

public class ProductRepositoryImpl implements ProductRepository {
    private static final String PRODUCT_COLUMNS = "id, name, price, stock, category_id";

    private static Product toProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBigDecimal("price"),
            rs.getInt("stock"),
            rs.getInt("category_id")
        );
    }

    @Override
    public List<Product> findAll(int page, int size) {
        long offset = (page - 1L) * size;
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT " + PRODUCT_COLUMNS + " FROM products ORDER BY id LIMIT ? OFFSET ?")
        ) {
            ps.setInt(1, size);
            ps.setLong(2, offset);
            return toList(ps, ProductRepositoryImpl::toProduct);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public List<Product> findByName(String name) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT " + PRODUCT_COLUMNS + " FROM products WHERE name ILIKE ? ORDER BY id")
        ) {
            ps.setString(1, "%" + name + "%");
            return toList(ps, ProductRepositoryImpl::toProduct);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public List<Product> findByCategory(int categoryId) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT " + PRODUCT_COLUMNS + " FROM products WHERE category_id = ? ORDER BY id")
        ) {
            ps.setInt(1, categoryId);
            return toList(ps, ProductRepositoryImpl::toProduct);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public Optional<Product> findById(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT " + PRODUCT_COLUMNS + " FROM products WHERE id = ?")
        ) {
            ps.setInt(1, id);
            return toOptional(ps, ProductRepositoryImpl::toProduct);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public Product save(Product product) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?) RETURNING " + PRODUCT_COLUMNS)
        ) {
            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setInt(4, product.getCategoryId());
            return toSingle(ps, ProductRepositoryImpl::toProduct);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    @Override
    public boolean delete(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("DELETE FROM products WHERE id = ?")
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }
}
