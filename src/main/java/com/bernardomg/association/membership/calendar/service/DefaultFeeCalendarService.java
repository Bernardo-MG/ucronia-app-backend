
package com.bernardomg.association.membership.calendar.service;

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
import org.springframework.stereotype.Service;

import com.bernardomg.association.membership.calendar.model.FeeCalendarRange;
import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.calendar.model.ImmutableFeeCalendarRange;
import com.bernardomg.association.membership.calendar.model.ImmutableFeeMonth;
import com.bernardomg.association.membership.calendar.model.ImmutableUserFeeCalendar;
import com.bernardomg.association.membership.calendar.model.UserFeeCalendar;
import com.bernardomg.association.membership.fee.persistence.model.PersistentMemberFee;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.membership.member.model.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public final class DefaultFeeCalendarService implements FeeCalendarService {

    private final MemberFeeRepository memberFeeRepository;

    @Override
    public final FeeCalendarRange getRange() {
        final Collection<Integer> years;

        years = memberFeeRepository.findYears();
        return ImmutableFeeCalendarRange.builder()
            .years(years)
            .build();
    }

    @Override
    public final Iterable<UserFeeCalendar> getYear(final int year, final MemberStatus active, final Sort sort) {
        final Collection<PersistentMemberFee>      readFees;
        final Map<Long, List<PersistentMemberFee>> memberFees;
        final Collection<UserFeeCalendar>          years;
        final Iterable<Long>                       memberIds;
        Specification<PersistentMemberFee>         spec;
        List<PersistentMemberFee>                  fees;
        UserFeeCalendar                            feeYear;
        Boolean                                    activeFilter;

        spec = MemberFeeSpecifications.between(YearMonth.of(year, Month.JANUARY), YearMonth.of(year, Month.DECEMBER));
        switch (active) {
            case ACTIVE:
                spec = spec.and(MemberFeeSpecifications.active(true));
                break;
            case INACTIVE:
                spec = spec.and(MemberFeeSpecifications.active(false));
                break;
            default:
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
                activeFilter = false;
            } else {
                activeFilter = fees.iterator()
                    .next()
                    .getActive();
            }
            feeYear = toFeeYear(member, year, activeFilter, fees);

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
