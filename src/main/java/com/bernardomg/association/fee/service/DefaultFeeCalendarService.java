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

package com.bernardomg.association.fee.service;

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

import com.bernardomg.association.fee.model.FeeCalendar;
import com.bernardomg.association.fee.model.FeeCalendarMember;
import com.bernardomg.association.fee.model.FeeCalendarMonth;
import com.bernardomg.association.fee.model.FeeCalendarMonthFee;
import com.bernardomg.association.fee.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.persistence.model.MemberFeeEntity;
import com.bernardomg.association.fee.persistence.repository.ActiveMemberSpringRepository;
import com.bernardomg.association.fee.persistence.repository.MemberFeeRepository;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.infra.jpa.model.MemberEntity;

public final class DefaultFeeCalendarService implements FeeCalendarService {

    private final ActiveMemberSpringRepository activeMemberRepository;

    private final MemberFeeRepository          memberFeeRepository;

    public DefaultFeeCalendarService(final MemberFeeRepository memberFeeRepo,
            final ActiveMemberSpringRepository activeMemberRepo) {
        super();

        memberFeeRepository = Objects.requireNonNull(memberFeeRepo);
        activeMemberRepository = Objects.requireNonNull(activeMemberRepo);
    }

    @Override
    public final FeeCalendarYearsRange getRange() {
        final Collection<Integer> years;

        years = memberFeeRepository.findYears();
        return FeeCalendarYearsRange.builder()
            .years(years)
            .build();
    }

    @Override
    public final Iterable<FeeCalendar> getYear(final int year, final MemberStatus status, final Sort sort) {
        final Collection<MemberFeeEntity>      readFees;
        final Map<Long, List<MemberFeeEntity>> memberFees;
        final Collection<FeeCalendar>          years;
        final Collection<Long>                 memberIds;
        final YearMonth                        start;
        final YearMonth                        end;
        final YearMonth                        validStart;
        final YearMonth                        validEnd;
        final Collection<Long>                 foundIds;
        List<MemberFeeEntity>                  fees;
        FeeCalendar                            feeYear;

        start = YearMonth.of(year, Month.JANUARY);
        end = YearMonth.of(year, Month.DECEMBER);
        validStart = YearMonth.now();
        validEnd = YearMonth.now();

        // Select query based on status
        switch (status) {
            case ACTIVE:
                foundIds = activeMemberRepository.findAllActiveIdsInRange(validStart, validEnd);

                readFees = memberFeeRepository.findAllInRangeForMembersIn(start, end, foundIds, sort);
                break;
            case INACTIVE:
                foundIds = activeMemberRepository.findAllInactiveIds(validStart, validEnd);

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

    private final FeeCalendarMonth toFeeMonth(final MemberFeeEntity entity) {
        final Integer             month;
        final FeeCalendarMonthFee fee;
        final FeeCalendarMember   member;

        // Calendar months start at index 0, this has to be corrected
        month = entity.getDate()
            .getMonth()
            .getValue();

        fee = FeeCalendarMonthFee.builder()
            .date(entity.getDate())
            .paid(entity.getPaid())
            .build();
        member = FeeCalendarMember.builder()
            .number(entity.getMemberNumber())
            .build();
        return FeeCalendarMonth.builder()
            .fee(fee)
            .member(member)
            .month(month)
            .build();
    }

    private final FeeCalendar toFeeYear(final Long memberId, final Integer year,
            final Collection<MemberFeeEntity> fees) {
        final Collection<FeeCalendarMonth> months;
        final MemberFeeEntity              row;
        final String                       name;
        final boolean                      active;
        final YearMonth                    validStart;
        final YearMonth                    validEnd;
        final long                         memberNumber;
        final Optional<MemberEntity>       read;
        final FeeCalendarMember            member;

        months = fees.stream()
            .map(this::toFeeMonth)
            // Sort by month
            .sorted(Comparator.comparing(FeeCalendarMonth::getMonth))
            .toList();

        row = fees.iterator()
            .next();
        name = row.getFullName();

        // TODO: should be done in the repository
        validStart = YearMonth.now();
        validEnd = YearMonth.now();
        active = activeMemberRepository.isActive(memberId, validStart, validEnd);

        read = activeMemberRepository.findById(memberId);
        if (read.isPresent()) {
            memberNumber = read.get()
                .getNumber();
        } else {
            memberNumber = -1;
        }

        member = FeeCalendarMember.builder()
            .number(memberNumber)
            .fullName(name)
            .active(active)
            .build();
        return FeeCalendar.builder()
            .member(member)
            .months(months)
            .year(year)
            .build();
    }

}
