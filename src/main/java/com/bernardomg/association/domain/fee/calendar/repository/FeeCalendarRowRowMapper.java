
package com.bernardomg.association.domain.fee.calendar.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;

import com.bernardomg.association.domain.fee.calendar.model.DtoFeeCalendarRow;
import com.bernardomg.association.domain.fee.calendar.model.FeeCalendarRow;

public final class FeeCalendarRowRowMapper implements RowMapper<FeeCalendarRow> {

    public FeeCalendarRowRowMapper() {
        super();
    }

    @Override
    public final FeeCalendarRow mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final DtoFeeCalendarRow fee;
        final Calendar      calendar;

        calendar = Calendar.getInstance();
        try {

            fee = new DtoFeeCalendarRow();
            fee.setId(rs.getLong("id"));
            fee.setName(rs.getString("name"));
            fee.setSurname(rs.getString("surname"));
            fee.setMemberId(rs.getLong("memberId"));
            fee.setPaid(rs.getBoolean("paid"));
            fee.setActive(rs.getBoolean("active"));

            calendar.setTime(rs.getDate("date"));
            fee.setDate(calendar);
        } catch (final SQLException e) {
            // TODO: Handle better
            throw new RuntimeException(e);
        }

        return fee;
    }

}
