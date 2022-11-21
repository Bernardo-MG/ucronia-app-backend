
package com.bernardomg.association.status.feeyear.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bernardomg.association.status.feeyear.model.FeeYearRange;
import com.bernardomg.association.status.feeyear.model.ImmutableFeeYearRange;

public final class FeeRangeRowMapper implements RowMapper<FeeYearRange> {

    public FeeRangeRowMapper() {
        super();
    }

    @Override
    public final FeeYearRange mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Integer start;
        final Integer end;

        try {
            start = rs.getInt("start_date");
            end = rs.getInt("end_date");
        } catch (final SQLException e) {
            // TODO: Handle better
            throw new RuntimeException(e);
        }

        return new ImmutableFeeYearRange(start, end);
    }

}
