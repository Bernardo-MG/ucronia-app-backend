
package com.bernardomg.association.calendar.fee.persistence.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRow;
import com.bernardomg.association.calendar.fee.model.ImmutableFeeCalendarRow;

public final class FeeCalendarRowRowMapper implements RowMapper<FeeCalendarRow> {

    public FeeCalendarRowRowMapper() {
        super();
    }

    @Override
    public final FeeCalendarRow mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final ImmutableFeeCalendarRow fee;
        final LocalDate               date;

        try {
            date = rs.getDate("date")
                .toLocalDate();

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