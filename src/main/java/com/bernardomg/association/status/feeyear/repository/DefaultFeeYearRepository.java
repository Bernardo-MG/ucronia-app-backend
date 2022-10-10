
package com.bernardomg.association.status.feeyear.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.status.feeyear.model.DtoFeeMonth;
import com.bernardomg.association.status.feeyear.model.DtoFeeYear;
import com.bernardomg.association.status.feeyear.model.FeeMonth;
import com.bernardomg.association.status.feeyear.model.FeeYear;
import com.bernardomg.association.status.feeyear.model.FeeYearRow;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public final class DefaultFeeYearRepository implements FeeYearRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String                     query = "SELECT f.id AS id, f.date AS date, f.paid AS paid, m.name AS name, m.surname AS surname, m.id AS memberId, m.active AS active FROM fees f JOIN members m ON f.member_id = m.id";

    @Override
    public final Iterable<? extends FeeYear> findAllForYear(final Integer year, final Sort sort) {
        final Collection<FeeYearRow>      readFees;
        final Map<Long, List<FeeYearRow>> memberFees;
        final Collection<FeeYear>         years;
        final Iterable<Long>              memberIds;
        List<FeeYearRow>                  fees;
        FeeYear                           feeYear;
        Boolean                           active;

        readFees = getAllFees(year, sort);
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

    private final List<FeeYearRow> getAllFees(final Integer year, final Sort sort) {
        final SqlParameterSource namedParameters;
        final String             where;
        final String             sorting;

        // TODO: Test sorting

        where = " WHERE YEAR(f.date) = :year";
        if (sort.isSorted()) {
            sorting = " ORDER BY " + sort.get()
                .map(this::toSorting)
                .collect(Collectors.joining(" ,"));
        } else {
            sorting = "";
        }

        namedParameters = new MapSqlParameterSource().addValue("year", year);
        return jdbcTemplate.query(query + where + sorting, namedParameters, new FeeYearRowRowMapper());
    }

    private final FeeMonth toFeeMonth(final FeeYearRow fee) {
        final DtoFeeMonth feeMonth;
        final Integer     month;

        // Calendar months start at index 0, this has to be corrected
        month = fee.getDate()
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
        final FeeYearRow           row;
        FeeMonth                   feeMonth;

        feeYear = new DtoFeeYear();
        // TODO: Handle empty list
        row = fees.iterator()
            .next();
        feeYear.setName(row.getName());
        feeYear.setSurname(row.getSurname());
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

    private final String toSorting(final Order order) {
        final String direction;

        if (order.isAscending()) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }

        return order.getProperty() + " " + direction;
    }

}
