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

package com.bernardomg.association.fee.usecase.service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendarMember;
import com.bernardomg.association.fee.domain.model.FeeCalendarMonth;
import com.bernardomg.association.fee.domain.model.FeeCalendarMonthFee;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.model.FeePerson;
import com.bernardomg.association.fee.domain.repository.ActiveMemberRepository;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.domain.model.MemberStatus;

/**
 * Default implementation of the fee calendar service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Transactional
public final class DefaultFeeCalendarService implements FeeCalendarService {

    private final ActiveMemberRepository activeMemberRepository;

    private final FeeRepository          feeRepository;

    public DefaultFeeCalendarService(final FeeRepository feeRepo, final ActiveMemberRepository activeMemberRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        activeMemberRepository = Objects.requireNonNull(activeMemberRepo);
    }

    @Override
    public final FeeCalendarYearsRange getRange() {
        return feeRepository.findRange();
    }

    @Override
    public final Iterable<FeeCalendar> getYear(final Year year, final MemberStatus status, final Sort sort) {
        final Collection<Fee>         readFees;
        final Map<Object, List<Fee>>  memberFees;
        final Collection<FeeCalendar> years;
        final Collection<Long>        memberNumbers;
        List<Fee>                     fees;
        FeeCalendar                   feeYear;

        // Select query based on status
        switch (status) {
            case ACTIVE:
                readFees = feeRepository.findAllForActiveMembers(year, sort);
                break;
            case INACTIVE:
                readFees = feeRepository.findAllForInactiveMembers(year, sort);
                break;
            default:
                readFees = feeRepository.findAllInYear(year, sort);
        }

        // Member fees grouped by id
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(f -> f.getPerson()
                .getNumber()));
        // Sorted ids
        memberNumbers = readFees.stream()
            .map(Fee::getPerson)
            .map(FeePerson::getNumber)
            .distinct()
            .toList();

        years = new ArrayList<>();
        for (final Long memberNumber : memberNumbers) {
            fees = memberFees.get(memberNumber);
            feeYear = toFeeYear(memberNumber, year, fees);
            years.add(feeYear);
        }

        return years;
    }

    private final FeeCalendarMonth toFeeMonth(final Fee fee) {
        final Integer             month;
        final FeeCalendarMonthFee calendarFee;

        // Calendar months start at index 0, this has to be corrected
        month = fee.getDate()
            .getMonth()
            .getValue();

        calendarFee = FeeCalendarMonthFee.builder()
            .withDate(fee.getDate())
            .withPaid(fee.isPaid())
            .build();
        return FeeCalendarMonth.builder()
            .withFee(calendarFee)
            .withMonth(month)
            .build();
    }

    private final FeeCalendar toFeeYear(final Long memberNumber, final Year year, final Collection<Fee> fees) {
        final Collection<FeeCalendarMonth> months;
        final Fee                          row;
        final String                       name;
        final boolean                      active;
        final FeeCalendarMember            member;

        months = fees.stream()
            .map(this::toFeeMonth)
            // Sort by month
            .sorted(Comparator.comparing(FeeCalendarMonth::getMonth))
            .toList();

        row = fees.iterator()
            .next();
        name = row.getPerson()
            .getFullName();

        // FIXME: Shouldn't be needed when filtering by active or inactive
        active = activeMemberRepository.isActive(memberNumber);

        member = FeeCalendarMember.builder()
            .withNumber(memberNumber)
            .withFullName(name)
            .withActive(active)
            .build();
        return FeeCalendar.builder()
            .withMember(member)
            .withMonths(months)
            .withYear(year.getValue())
            .build();
    }

}
