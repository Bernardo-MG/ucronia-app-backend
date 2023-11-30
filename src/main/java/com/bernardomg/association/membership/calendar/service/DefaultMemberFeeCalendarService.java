/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.membership.calendar.service;

import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.calendar.model.ImmutableFeeMonth;
import com.bernardomg.association.membership.calendar.model.ImmutableMemberFeeCalendar;
import com.bernardomg.association.membership.calendar.model.ImmutableYearsRange;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;
import com.bernardomg.association.membership.calendar.model.YearsRange;
import com.bernardomg.association.membership.fee.persistence.model.MemberFeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeSpecifications;
import com.bernardomg.association.membership.member.model.MemberStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultMemberFeeCalendarService implements MemberFeeCalendarService {

    private final MemberFeeRepository memberFeeRepository;

    public DefaultMemberFeeCalendarService(final MemberFeeRepository memberFeeRepo) {
        super();

        memberFeeRepository = Objects.requireNonNull(memberFeeRepo);
    }

    @Override
    public final YearsRange getRange() {
        final Collection<Integer> years;

        years = memberFeeRepository.findYears();
        return ImmutableYearsRange.builder()
            .years(years)
            .build();
    }

    @Override
    public final Iterable<MemberFeeCalendar> getYear(final int year, final MemberStatus active, final Sort sort) {
        final Collection<MemberFeeEntity>      readFees;
        final Map<Long, List<MemberFeeEntity>> memberFees;
        final Collection<MemberFeeCalendar>    years;
        final Collection<Long>                 memberIds;
        Specification<MemberFeeEntity>         spec;
        List<MemberFeeEntity>                  fees;
        MemberFeeCalendar                      feeYear;
        Boolean                                activeFilter;

        spec = MemberFeeSpecifications.between(YearMonth.of(year, Month.JANUARY), YearMonth.of(year, Month.DECEMBER));
        // TODO: Read active member ids to filter
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
        // Member fees grouped by id
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(MemberFeeEntity::getMemberId));
        // Sorted ids
        memberIds = readFees.stream()
            .map(MemberFeeEntity::getMemberId)
            .distinct()
            .toList();

        years = new ArrayList<>();
        for (final Long member : memberIds) {
            fees = memberFees.get(member);
            if (fees.isEmpty()) {
                activeFilter = false;
            } else {
//                activeFilter = fees.iterator()
//                    .next()
//                    .getActive();
                activeFilter = false;
            }
            feeYear = toFeeYear(member, year, activeFilter, fees);

            years.add(feeYear);
        }

        return years;
    }

    private final FeeMonth toFeeMonth(final MemberFeeEntity fee) {
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

    private final MemberFeeCalendar toFeeYear(final Long member, final Integer year, final Boolean active,
            final Collection<MemberFeeEntity> fees) {
        final Collection<FeeMonth> months;
        final MemberFeeEntity      row;
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

        return ImmutableMemberFeeCalendar.builder()
            .memberId(member)
            .memberName(name)
            .active(active)
            .months(months)
            .year(year)
            .build();
    }

}
