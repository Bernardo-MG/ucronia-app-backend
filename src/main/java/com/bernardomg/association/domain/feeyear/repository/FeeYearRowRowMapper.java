
package com.bernardomg.association.domain.feeyear.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;

import com.bernardomg.association.domain.feeyear.model.DtoFeeYearRow;
import com.bernardomg.association.domain.feeyear.model.FeeYearRow;

public final class FeeYearRowRowMapper implements RowMapper<FeeYearRow> {

    public FeeYearRowRowMapper() {
        super();
    }

    @Override
    public final FeeYearRow mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final DtoFeeYearRow fee;
        final Calendar      calendar;

        calendar = Calendar.getInstance();
        try {

            fee = new DtoFeeYearRow();
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
