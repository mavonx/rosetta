package org.rosetta;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try(Connection connection = ConnectionManager.getConnection()) {
            System.out.println("Connected to: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("Version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Database: " + connection.getCatalog());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
