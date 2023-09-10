
package com.bernardomg.association.calendar.fee.persistence.repository;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bernardomg.association.calendar.fee.model.FeeCalendarRange;
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

    private static final String              QUERY_RANGE                    = "SELECT extract(year from f.date) AS feeYear FROM fees f GROUP BY feeYear ORDER BY feeYear ASC";

    private static final String              QUERY_RANGE_WITH_ACTIVE_MEMBER = "SELECT extract(year from f.date) AS feeYear FROM fees f JOIN members m ON f.member_id = m.id WHERE m.active = true GROUP BY feeYear ORDER BY feeYear ASC";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final MemberFeeRepository        memberFeeRepository;

    @Override
    public final Collection<UserFeeCalendar> findAllForYear(final boolean onlyActive, final int year, final Sort sort) {
        final Collection<PersistentMemberFee>      readFees;
        final Map<Long, List<PersistentMemberFee>> memberFees;
        final Collection<UserFeeCalendar>          years;
        final Iterable<Long>                       memberIds;
        Specification<PersistentMemberFee>         spec;
        List<PersistentMemberFee>                  fees;
        UserFeeCalendar                            feeYear;
        Boolean                                    active;

        spec = MemberFeeSpecifications.between(YearMonth.of(year, Month.JANUARY), YearMonth.of(year, Month.DECEMBER));
        if (onlyActive) {
            spec = spec.and(MemberFeeSpecifications.active(true));
        }
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
        final PersistentMemberFee  row;
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
            .memberName(name)
            .active(active)
            .months(months)
            .year(year)
            .build();
    }

}
