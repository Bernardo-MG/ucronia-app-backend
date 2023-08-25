
package com.bernardomg.association.fee.calendar.persistence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.jdbc.core.RowMapper;

import com.bernardomg.association.fee.calendar.model.FeeCalendarRow;
import com.bernardomg.association.fee.calendar.model.ImmutableFeeCalendarRow;

public final class FeeCalendarRowRowMapper implements RowMapper<FeeCalendarRow> {

    public FeeCalendarRowRowMapper() {
        super();
    }

    @Override
    public final FeeCalendarRow mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final ImmutableFeeCalendarRow fee;
        final LocalDateTime           date;

        try {
            date = rs.getDate("date")
                .toLocalDate()
                .atTime(LocalTime.now());

            fee = ImmutableFeeCalendarRow.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .memberId(rs.getLong("memberId"))
                .paid(rs.getBoolean("paid"))
                .active(rs.getBoolean("active"))
                .date(date)
                .build();
        } catch (final SQLException e) {
            // TODO: Handle better
            throw new RuntimeException(e);
        }

        return fee;
    }

}
