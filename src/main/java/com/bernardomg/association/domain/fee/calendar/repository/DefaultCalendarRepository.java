
package com.bernardomg.association.domain.fee.calendar.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.domain.fee.calendar.model.FeeMonth;
import com.bernardomg.association.domain.fee.calendar.model.FeeCalendar;
import com.bernardomg.association.domain.fee.calendar.model.FeeCalendarRange;
import com.bernardomg.association.domain.fee.calendar.model.FeeCalendarRow;
import com.bernardomg.association.domain.fee.calendar.model.ImmutableFeeMonth;
import com.bernardomg.association.domain.fee.calendar.model.ImmutableFeeCalendar;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public final class DefaultCalendarRepository implements FeeCalendarRepository {

    private final RowMapper<FeeCalendarRange>    feeRangeRowMapper          = new FeeCalendarRangeRowMapper();

    private final RowMapper<FeeCalendarRow>      feeYearRowRowMapper        = new FeeCalendarRowRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String                     queryForYear               = "SELECT f.id AS id, f.date AS date, f.paid AS paid, m.name AS name, m.surname AS surname, m.id AS memberId, m.active AS active FROM fees f JOIN members m ON f.member_id = m.id";

    private final String                     queryRange                 = "SELECT extract(year from s.min_date) AS start_date, extract(year from s.max_date) AS end_date FROM (SELECT min(f.date) AS min_date, max(f.date) AS max_date FROM fees f) s";

    private final String                     queryRangeWithActiveMember = "SELECT extract(year from s.min_date) AS start_date, extract(year from s.max_date) AS end_date FROM (SELECT min(f.date) AS min_date, max(f.date) AS max_date FROM fees f JOIN members m ON f.member_id = m.id WHERE m.active = true) s";

    @Override
    public final Iterable<? extends FeeCalendar> findAllForYear(final Integer year, final Sort sort) {
        return findAllForYear(queryForYear, year, sort);
    }

    @Override
    public final Iterable<? extends FeeCalendar> findAllForYearWithActiveMember(final Integer year, final Sort sort) {
        // TODO: Improve how the where is built
        return findAllForYear(queryForYear + " WHERE m.active = true", year, sort);
    }

    @Override
    public final FeeCalendarRange findRange() {
        return jdbcTemplate.queryForObject(queryRange, Collections.emptyMap(), feeRangeRowMapper);
    }

    @Override
    public final FeeCalendarRange findRangeWithActiveMember() {
        return jdbcTemplate.queryForObject(queryRangeWithActiveMember, Collections.emptyMap(), feeRangeRowMapper);
    }

    private final List<FeeCalendarRow> findAll(final String query, final Integer year, final Sort sort) {
        final SqlParameterSource namedParameters;
        final String             operand;
        final String             where;
        final String             sorting;

        if (query.contains("WHERE")) {
            operand = " AND";
        } else {
            operand = " WHERE";
        }
        where = operand + " extract(year from f.date) = :year";
        if (sort.isSorted()) {
            sorting = " ORDER BY " + sort.get()
                .map(this::toSorting)
                .collect(Collectors.joining(" ,"));
        } else {
            sorting = "";
        }

        namedParameters = new MapSqlParameterSource().addValue("year", year);
        return jdbcTemplate.query(query + where + sorting, namedParameters, feeYearRowRowMapper);
    }

    private final Iterable<? extends FeeCalendar> findAllForYear(final String query, final Integer year, final Sort sort) {
        final Collection<FeeCalendarRow>      readFees;
        final Map<Long, List<FeeCalendarRow>> memberFees;
        final Collection<FeeCalendar>         years;
        final Iterable<Long>              memberIds;
        List<FeeCalendarRow>                  fees;
        FeeCalendar                           feeYear;
        Boolean                           active;

        readFees = findAll(query, year, sort);
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(FeeCalendarRow::getMemberId));
        memberIds = readFees.stream()
            .map(FeeCalendarRow::getMemberId)
            .distinct()
            .collect(Collectors.toList());

        years = new ArrayList<>();
        for (final Long member : memberIds) {
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

    private final FeeMonth toFeeMonth(final FeeCalendarRow fee) {
        final Integer month;

        // Calendar months start at index 0, this has to be corrected
        month = fee.getDate()
            .get(Calendar.MONTH) + 1;

        return new ImmutableFeeMonth(month, fee.getPaid());
    }

    private final FeeCalendar toFeeYear(final Long member, final Integer year, final Boolean active,
            final Collection<FeeCalendarRow> fees) {
        final Collection<FeeMonth> months;
        final FeeCalendarRow           row;
        final String               name;
        final String               surname;
        FeeMonth                   feeMonth;

        if (fees.isEmpty()) {
            // TODO: Tests this case to make sure it is handled correctly
            // TODO: Move out of the method and make sure this can't happen
            log.warn("No data found for member {}", member);
            name = "";
            surname = "";
            months = Collections.emptyList();
        } else {
            months = new ArrayList<>();
            for (final FeeCalendarRow fee : fees) {
                feeMonth = toFeeMonth(fee);
                months.add(feeMonth);
            }

            row = fees.iterator()
                .next();
            name = row.getName();
            surname = row.getSurname();
        }

        return new ImmutableFeeCalendar(member, name, surname, active, months, year);
    }

    private final String toSorting(final Order order) {
        final String direction;
        // TODO: Duplicated in other repositories

        if (order.isAscending()) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }

        return order.getProperty() + " " + direction;
    }

}
