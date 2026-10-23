package org.rosetta;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DbConfig {
    private static String require(String name) {
        String value = System.getenv(name);
        if(value == null || value.isBlank())
            throw new IllegalStateException("Missing environment variable: " + name);
        return value;
    }

    public static Connection getConnection() throws SQLException {
        String url = require("DB_URL");
        String username = require("DB_USER");
        String password = require("DB_PASSWORD");
        return DriverManager.getConnection(url, username, password);
    }
}
