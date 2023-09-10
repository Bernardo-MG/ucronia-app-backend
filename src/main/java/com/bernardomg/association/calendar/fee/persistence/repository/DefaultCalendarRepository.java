
package com.bernardomg.association.calendar.fee.persistence.repository;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRange;
import com.bernardomg.association.calendar.fee.model.FeeCalendarRow;
import com.bernardomg.association.calendar.fee.model.FeeMonth;
import com.bernardomg.association.calendar.fee.model.ImmutableFeeCalendarRange;
import com.bernardomg.association.calendar.fee.model.ImmutableFeeMonth;
import com.bernardomg.association.calendar.fee.model.ImmutableUserFeeCalendar;
import com.bernardomg.association.calendar.fee.model.UserFeeCalendar;
import com.bernardomg.association.fee.persistence.model.PersistentMemberFee;
import com.bernardomg.association.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeSpecifications;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@AllArgsConstructor
@Slf4j
public final class DefaultCalendarRepository implements FeeCalendarRepository {

    private static final String              QUERY_FOR_YEAR                 = "SELECT f.id AS id, f.date AS date, f.paid AS paid, m.name AS name, m.surname AS surname, m.id AS memberId, m.active AS active FROM fees f JOIN members m ON f.member_id = m.id";

    private static final String              QUERY_RANGE                    = "SELECT extract(year from f.date) AS feeYear FROM fees f GROUP BY feeYear ORDER BY feeYear ASC";

    private static final String              QUERY_RANGE_WITH_ACTIVE_MEMBER = "SELECT extract(year from f.date) AS feeYear FROM fees f JOIN members m ON f.member_id = m.id WHERE m.active = true GROUP BY feeYear ORDER BY feeYear ASC";

    private final RowMapper<FeeCalendarRow>  feeYearRowRowMapper            = new FeeCalendarRowRowMapper();

    private final NamedParameterJdbcTemplate jdbcTemplate;
    
    private final MemberFeeRepository memberFeeRepository;

    @Override
    public final Collection<UserFeeCalendar> findAllForYear(final boolean onlyActive, final int year, final Sort sort) {
        final String query;

        if (onlyActive) {
            // TODO: Improve how the where is built
            query = QUERY_FOR_YEAR + " WHERE m.active = true";
        } else {
            query = QUERY_FOR_YEAR;
        }

        return findAllForYear(year, sort);
    }

    @Override
    public final FeeCalendarRange findRange(final boolean onlyActive) {
        final Collection<Integer> years;
        final String              query;

        if (onlyActive) {
            query = QUERY_RANGE_WITH_ACTIVE_MEMBER;
        } else {
            query = QUERY_RANGE;
        }

        years = jdbcTemplate.queryForList(query, Collections.emptyMap(), Integer.class);
        return ImmutableFeeCalendarRange.builder()
            .years(years)
            .build();
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

    private final Collection<UserFeeCalendar> findAllForYear(final Integer year, final Sort sort) {
        final Collection<PersistentMemberFee>      readFees;
        final Map<Long, List<PersistentMemberFee>> memberFees;
        final Collection<UserFeeCalendar>     years;
        final Iterable<Long>                  memberIds;
        final Specification<PersistentMemberFee> spec;
        List<PersistentMemberFee>                  fees;
        UserFeeCalendar                       feeYear;
        Boolean                               active;

        spec = MemberFeeSpecifications.between(YearMonth.of(year, Month.JANUARY), YearMonth.of(year, Month.DECEMBER));
        readFees = memberFeeRepository.findAll(spec, sort);
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(PersistentMemberFee::getMemberId));
        memberIds = readFees.stream()
            .map(PersistentMemberFee::getMemberId)
            .distinct()
            .toList();

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

    private final FeeMonth toFeeMonth(final PersistentMemberFee fee) {
        final Integer month;

        // Calendar months start at index 0, this has to be corrected
        month = fee.getDate()
            .getMonth()
            .getValue();

        return ImmutableFeeMonth.builder()
            .feeId(fee.getId())
            .month(month)
            .paid(fee.getPaid())
            .build();
    }

    private final UserFeeCalendar toFeeYear(final Long member, final Integer year, final Boolean active,
            final Collection<PersistentMemberFee> fees) {
        final Collection<FeeMonth> months;
        final PersistentMemberFee       row;
        final String               name;

        if (fees.isEmpty()) {
            // TODO: Tests this case to make sure it is handled correctly
            // TODO: Move out of the method and make sure this can't happen
            log.warn("No data found for member {}", member);
            months = Collections.emptyList();

            name = "";
        } else {
            months = fees.stream()
                .map(this::toFeeMonth)
                // Sort by month
                .sorted(Comparator.comparing(FeeMonth::getMonth))
                .toList();

            row = fees.iterator()
                .next();
            name = row.getMemberName();
        }

        return ImmutableUserFeeCalendar.builder()
            .memberId(member)
            .name(name)
            .active(active)
            .months(months)
            .year(year)
            .build();
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
