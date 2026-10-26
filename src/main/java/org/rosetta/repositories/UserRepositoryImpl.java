package org.rosetta.repositories;

import org.rosetta.model.User;
import org.rosetta.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class UserRepositoryImpl implements UserRepository {
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, email, password FROM users")
        ) {
            while(rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return users;
    }

    @Override
    public List<User> findAll(int page, int size) {
        if(page < 1 || size < 1)
            throw new IllegalArgumentException("Page and size must be at least 1");

        int offset = (page - 1) * size;

        List<User> users = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, email, password FROM users ORDER BY id LIMIT " + size + " OFFSET " + offset)
        ) {
            while(rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
                ));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return users;
    }

    @Override
    public Optional<User> findById(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, email, password FROM users WHERE id = " + id)
        ) {
            if(rs.next()) {
                return Optional.of(
                        new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                        )
                );
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT id, name, email, password FROM users WHERE email = ?")
        ) {
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return Optional.of(
                            new User(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password")
                            )
                    );
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        try(
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users (name, email, password) VALUES (?, ?, ?) RETURNING id, name, email, password")
        ) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            try(ResultSet rs = ps.executeQuery()) {
                rs.next();
                return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password")
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
            st.executeUpdate("DELETE FROM users WHERE id = " + id);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }
}
