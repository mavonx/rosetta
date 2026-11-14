package org.rosetta.common.persistence;

import java.util.List;
import java.util.Optional;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public final class RowMappers {
    private RowMappers() {}

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
