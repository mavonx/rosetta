package org.rosetta.repositories;

import org.rosetta.model.User;
import org.rosetta.ConnectionManager;

import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;

public class UserRepository {
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, email, password FROM users");
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

    public List<User> findAll(int page, int size) {
        if(page < 1 || size < 1)
            throw new IllegalArgumentException("Page and size must be at least 1");

        int offset = (page - 1) * size;

        List<User> users = new ArrayList<>();
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, email, password FROM users ORDER BY id LIMIT " + size + " OFFSET " + offset);
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

    public Optional<User> findById(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, email, password FROM users WHERE id = " + id);
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

    public Optional<User> findByEmail(String email) {
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name, email, password FROM users WHERE email = '" + email + "'");
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

    public User save(User user) {
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("INSERT INTO users (name, email, password) VALUES ('" + user.getName() + "', '" + user.getEmail() + "', '" + user.getPassword() + "') RETURNING id, name, email, password");
        ) {
            rs.next();
            return new User(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password")
            );
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }

    public void delete(int id) {
        try(
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
        ) {
            st.executeUpdate("DELETE FROM users WHERE id = " + id);
        } catch(SQLException e) {
            throw new RuntimeException("Operation failed", e);
        }
    }
}
