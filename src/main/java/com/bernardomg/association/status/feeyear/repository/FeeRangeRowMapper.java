
package com.bernardomg.association.status.feeyear.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public final class FeeRangeRowMapper implements RowMapper<Integer> {

    public FeeRangeRowMapper() {
        super();
    }

    @Override
    public final Integer mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Integer year;

        try {
            year = rs.getInt("date");
        } catch (final SQLException e) {
            // TODO: Handle better
            throw new RuntimeException(e);
        }

        return year;
    }

}
