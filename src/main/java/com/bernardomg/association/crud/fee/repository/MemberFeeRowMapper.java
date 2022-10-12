
package com.bernardomg.association.crud.fee.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;

import com.bernardomg.association.crud.fee.model.DtoMemberFee;
import com.bernardomg.association.crud.fee.model.MemberFee;

public final class MemberFeeRowMapper implements RowMapper<MemberFee> {

    public MemberFeeRowMapper() {
        super();
    }

    @Override
    public final MemberFee mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final DtoMemberFee fee;
        final Calendar     calendar;

        calendar = Calendar.getInstance();
        try {

            fee = new DtoMemberFee();
            fee.setId(rs.getLong("id"));
            fee.setName(rs.getString("name"));
            fee.setSurname(rs.getString("surname"));
            fee.setMemberId(rs.getLong("member_id"));
            fee.setPaid(rs.getBoolean("paid"));

            calendar.setTime(rs.getDate("date"));
            fee.setDate(calendar);
        } catch (final SQLException e) {
            // TODO: Handle better
            throw new RuntimeException(e);
        }

        return fee;
    }

}
