package org.rosetta.repositories.mapping;

import org.rosetta.model.Product;

import java.util.List;
import java.util.Optional;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class RowMappers {
    public static final String PRODUCT_COLUMNS = "id, name, price, stock, category_id";

    private RowMappers() {}

    public static Product toProduct(ResultSet rs) throws SQLException {
        return new Product(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBigDecimal("price"),
            rs.getInt("stock"),
            rs.getInt("category_id")
        );
    }

    public static <T> List<T> toList(PreparedStatement ps, RowMapper<T> mapper) throws SQLException {
        List<T> result = new ArrayList<>();
        try(ResultSet rs = ps.executeQuery()) {
            while(rs.next()) result.add(mapper.map(rs));
        }
        return result;
    }

    public static <T> Optional<T> toOptional(PreparedStatement ps, RowMapper<T> mapper) throws SQLException {
        try(ResultSet rs = ps.executeQuery()) {
            return rs.next() ? Optional.of(mapper.map(rs)) : Optional.empty();
        }
    }

    public static <T> T toSingle(PreparedStatement ps, RowMapper<T> mapper) throws SQLException {
        try(ResultSet rs = ps.executeQuery()) {
            if(!rs.next()) throw new SQLException("Expected one row but none was returned");
            return mapper.map(rs);
        }
    }
}
