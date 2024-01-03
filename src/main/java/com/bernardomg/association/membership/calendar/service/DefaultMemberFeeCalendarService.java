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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;

import com.bernardomg.association.membership.calendar.model.FeeMonth;
import com.bernardomg.association.membership.calendar.model.MemberFeeCalendar;
import com.bernardomg.association.membership.calendar.model.YearsRange;
import com.bernardomg.association.membership.fee.persistence.model.MemberFeeEntity;
import com.bernardomg.association.membership.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.membership.member.model.MemberStatus;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;
import com.bernardomg.association.membership.member.persistence.repository.MemberRepository;

public final class DefaultMemberFeeCalendarService implements MemberFeeCalendarService {

    private final MemberFeeRepository memberFeeRepository;

    private final MemberRepository    memberRepository;

    public DefaultMemberFeeCalendarService(final MemberFeeRepository memberFeeRepo, final MemberRepository memberRepo) {
        super();

        memberFeeRepository = Objects.requireNonNull(memberFeeRepo);
        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final YearsRange getRange() {
        final Collection<Integer> years;

        years = memberFeeRepository.findYears();
        return YearsRange.builder()
            .years(years)
            .build();
    }

    @Override
    public final Iterable<MemberFeeCalendar> getYear(final int year, final MemberStatus status, final Sort sort) {
        final Collection<MemberFeeEntity>      readFees;
        final Map<Long, List<MemberFeeEntity>> memberFees;
        final Collection<MemberFeeCalendar>    years;
        final Collection<Long>                 memberIds;
        final YearMonth                        start;
        final YearMonth                        end;
        final YearMonth                        validStart;
        final YearMonth                        validEnd;
        final Collection<Long>                 foundIds;
        List<MemberFeeEntity>                  fees;
        MemberFeeCalendar                      feeYear;

        start = YearMonth.of(year, Month.JANUARY);
        end = YearMonth.of(year, Month.DECEMBER);
        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        // Select query based on status
        switch (status) {
            case ACTIVE:
                foundIds = memberRepository.findAllActiveIdsInRange(validStart, validEnd);

                readFees = memberFeeRepository.findAllInRangeForMembersIn(start, end, foundIds, sort);
                break;
            case INACTIVE:
                foundIds = memberRepository.findAllInactiveIds(validStart, validEnd);

                readFees = memberFeeRepository.findAllInRangeForMembersIn(start, end, foundIds, sort);
                break;
            default:
                readFees = memberFeeRepository.findAllInRange(start, end, sort);
        }

        // Member fees grouped by id
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(MemberFeeEntity::getMemberId));
        // Sorted ids
        memberIds = readFees.stream()
            .map(MemberFeeEntity::getMemberId)
            .distinct()
            .toList();

        years = new ArrayList<>();
        for (final Long memberId : memberIds) {
            fees = memberFees.get(memberId);
            feeYear = toFeeYear(memberId, year, fees);
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

        return FeeMonth.builder()
            .date(fee.getDate())
            .memberNumber(fee.getMemberNumber())
            .month(month)
            .paid(fee.getPaid())
            .build();
    }

    private final MemberFeeCalendar toFeeYear(final Long memberId, final Integer year,
            final Collection<MemberFeeEntity> fees) {
        final Collection<FeeMonth>   months;
        final MemberFeeEntity        row;
        final String                 name;
        final boolean                active;
        final YearMonth              validStart;
        final YearMonth              validEnd;
        final long                   memberNumber;
        final Optional<MemberEntity> read;

        months = fees.stream()
            .map(this::toFeeMonth)
            // Sort by month
            .sorted(Comparator.comparing(FeeMonth::getMonth))
            .toList();

        row = fees.iterator()
            .next();
        name = row.getMemberName();

        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        active = memberRepository.isActive(memberId, validStart, validEnd);

        read = memberRepository.findById(memberId);
        if (read.isPresent()) {
            memberNumber = read.get()
                .getNumber();
        } else {
            memberNumber = -1;
        }
        return MemberFeeCalendar.builder()
            .memberNumber(memberNumber)
            .memberName(name)
            .months(months)
            .year(year)
            .active(active)
            .build();
    }

}
