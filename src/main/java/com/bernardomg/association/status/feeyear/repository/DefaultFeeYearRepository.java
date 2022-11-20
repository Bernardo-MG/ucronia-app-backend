
package com.bernardomg.association.status.feeyear.repository;

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

import com.bernardomg.association.status.feeyear.model.FeeMonth;
import com.bernardomg.association.status.feeyear.model.FeeYear;
import com.bernardomg.association.status.feeyear.model.FeeYearRange;
import com.bernardomg.association.status.feeyear.model.FeeYearRow;
import com.bernardomg.association.status.feeyear.model.ImmutableFeeMonth;
import com.bernardomg.association.status.feeyear.model.ImmutableFeeYear;
import com.bernardomg.association.status.feeyear.model.ImmutableFeeYearRange;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public final class DefaultFeeYearRepository implements FeeYearRepository {

    private final RowMapper<FeeYearRange>    feeRangeRowMapper   = new FeeRangeRowMapper();

    private final RowMapper<FeeYearRow>      feeYearRowRowMapper = new FeeYearRowRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String                     queryForYear        = "SELECT f.id AS id, f.date AS date, f.paid AS paid, m.name AS name, m.surname AS surname, m.id AS memberId, m.active AS active FROM fees f JOIN members m ON f.member_id = m.id";

    private final String                     queryRangeEnd       = "SELECT extract(year from s.d) AS end_date FROM (SELECT max(f.date) AS d FROM fees f) s GROUP BY end_date ORDER BY end_date ASC";

    private final String                     queryRangeStart     = "SELECT extract(year from s.d) AS start_date FROM (SELECT min(f.date) AS d FROM fees f) s GROUP BY start_date ORDER BY start_date ASC";

    @Override
    public final Iterable<? extends FeeYear> findAllForYear(final Integer year, final Sort sort) {
        final Collection<FeeYearRow>      readFees;
        final Map<Long, List<FeeYearRow>> memberFees;
        final Collection<FeeYear>         years;
        final Iterable<Long>              memberIds;
        List<FeeYearRow>                  fees;
        FeeYear                           feeYear;
        Boolean                           active;

        readFees = findAll(year, sort);
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(FeeYearRow::getMemberId));
        memberIds = readFees.stream()
            .map(FeeYearRow::getMemberId)
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

    @Override
    public final FeeYearRange findRange() {
        final Integer start;
        final Integer end;

        start = jdbcTemplate.queryForObject(queryRangeStart, Collections.emptyMap(), Integer.class);
        end = jdbcTemplate.queryForObject(queryRangeEnd, Collections.emptyMap(), Integer.class);
        return new ImmutableFeeYearRange(start, end);
    }

    private final List<FeeYearRow> findAll(final Integer year, final Sort sort) {
        final SqlParameterSource namedParameters;
        final String             where;
        final String             sorting;

        where = " WHERE extract(year from f.date) = :year";
        if (sort.isSorted()) {
            sorting = " ORDER BY " + sort.get()
                .map(this::toSorting)
                .collect(Collectors.joining(" ,"));
        } else {
            sorting = "";
        }

        namedParameters = new MapSqlParameterSource().addValue("year", year);
        return jdbcTemplate.query(queryForYear + where + sorting, namedParameters, feeYearRowRowMapper);
    }

    private final FeeMonth toFeeMonth(final FeeYearRow fee) {
        final Integer month;

        // Calendar months start at index 0, this has to be corrected
        month = fee.getDate()
            .get(Calendar.MONTH) + 1;

        return new ImmutableFeeMonth(month, fee.getPaid());
    }

    private final FeeYear toFeeYear(final Long member, final Integer year, final Boolean active,
            final Collection<FeeYearRow> fees) {
        final Collection<FeeMonth> months;
        final FeeYearRow           row;
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
            for (final FeeYearRow fee : fees) {
                feeMonth = toFeeMonth(fee);
                months.add(feeMonth);
            }

            row = fees.iterator()
                .next();
            name = row.getName();
            surname = row.getSurname();
        }

        return new ImmutableFeeYear(member, name, surname, active, months, year);
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
