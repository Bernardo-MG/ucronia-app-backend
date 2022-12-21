
package com.bernardomg.association.domain.fee.calendar.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bernardomg.association.domain.fee.calendar.model.FeeCalendarRange;
import com.bernardomg.association.domain.fee.calendar.model.ImmutableFeeCalendarRange;

public final class FeeCalendarRangeRowMapper implements RowMapper<FeeCalendarRange> {

    public FeeCalendarRangeRowMapper() {
        super();
    }

    @Override
    public final FeeCalendarRange mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Integer start;
        final Integer end;

        try {
            start = rs.getInt("start_date");
            end = rs.getInt("end_date");
        } catch (final SQLException e) {
            // TODO: Handle better
            throw new RuntimeException(e);
        }

        return new ImmutableFeeCalendarRange(start, end);
    }

}
