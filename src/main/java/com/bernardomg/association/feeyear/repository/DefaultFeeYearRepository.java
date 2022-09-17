
package com.bernardomg.association.feeyear.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.feeyear.model.DtoFeeMonth;
import com.bernardomg.association.feeyear.model.DtoFeeYear;
import com.bernardomg.association.feeyear.model.DtoFeeYearRow;
import com.bernardomg.association.feeyear.model.FeeMonth;
import com.bernardomg.association.feeyear.model.FeeYear;
import com.bernardomg.association.feeyear.model.FeeYearRow;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public final class DefaultFeeYearRepository implements FeeYearRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String                     query = "SELECT f.id AS id, f.pay_date AS payDate, f.paid AS paid, TRIM(CONCAT(m.name, ' ',  m.surname)) AS member, m.id AS memberId, m.active AS active FROM fees f JOIN members m ON f.member_id = m.id";

    @Override
    public final Iterable<? extends FeeYear> findAllForYear(final Integer year, final Sort sort) {
        final Collection<FeeYearRow>      readFees;
        final Map<Long, List<FeeYearRow>> memberFees;
        final Collection<FeeYear>         years;
        List<FeeYearRow>                  fees;
        FeeYear                           feeYear;
        Boolean                           active;

        readFees = getAllFees(year, sort);
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(FeeYearRow::getMemberId));

        years = new ArrayList<>();
        for (final Long member : memberFees.keySet()) {
            fees = memberFees.get(member);
            if (fees.isEmpty()) {
                active = false;
            } else {
                active = fees.iterator()
                    .next()
                    .getActive();
            }
            feeYear = toFeeYear(member, year, active, fees);

            years.add(feeYear);
        }

        return years;
    }

    private final List<FeeYearRow> getAllFees(final Integer year, final Sort sort) {
        // TODO: Test sorting

        final SqlParameterSource namedParameters;

        namedParameters = new MapSqlParameterSource().addValue("year", year);
        return jdbcTemplate.query(query + " WHERE YEAR(f.pay_date) = :year", namedParameters, this::toFeeYearRow);
    }

    private final FeeMonth toFeeMonth(final FeeYearRow fee) {
        final DtoFeeMonth feeMonth;
        final Integer     month;

        // Calendar months start at index 0, this has to be corrected
        month = fee.getPayDate()
            .get(Calendar.MONTH) + 1;

        feeMonth = new DtoFeeMonth();
        feeMonth.setMonth(month);
        feeMonth.setPaid(fee.getPaid());

        return feeMonth;
    }

    private final FeeYear toFeeYear(final Long member, final Integer year, final Boolean active,
            final List<FeeYearRow> fees) {
        final DtoFeeYear           feeYear;
        final Collection<FeeMonth> months;
        FeeMonth                   feeMonth;

        feeYear = new DtoFeeYear();
        // TODO: Handle empty list
        feeYear.setMember(fees.iterator()
            .next()
            .getMember());
        feeYear.setMemberId(member);
        feeYear.setYear(year);
        feeYear.setActive(active);

        months = new ArrayList<>();
        for (final FeeYearRow fee : fees) {
            feeMonth = toFeeMonth(fee);
            months.add(feeMonth);
        }
        feeYear.setMonths(months);

        return feeYear;
    }

    private final FeeYearRow toFeeYearRow(final ResultSet rs, final Integer rowNum) {
        final DtoFeeYearRow fee;
        final Calendar      calendar;

        calendar = Calendar.getInstance();
        try {

            fee = new DtoFeeYearRow();
            fee.setId(rs.getLong("id"));
            fee.setMember(rs.getString("member"));
            fee.setMemberId(rs.getLong("memberId"));
            fee.setPaid(rs.getBoolean("paid"));
            fee.setActive(rs.getBoolean("active"));

            calendar.setTime(rs.getDate("payDate"));
            fee.setPayDate(calendar);
        } catch (final SQLException e) {
            // TODO: Handle better
            throw new RuntimeException(e);
        }

        return fee;
    }

}
