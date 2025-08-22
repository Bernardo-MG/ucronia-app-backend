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

import java.text.Normalizer;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeCalendar;
import com.bernardomg.association.fee.domain.model.FeeCalendar.FeeCalendarMonth;
import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.domain.repository.PersonRepository;
import com.bernardomg.data.domain.Sorting;

/**
 * Default implementation of the fee calendar service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service
@Transactional
public final class DefaultFeeCalendarService implements FeeCalendarService {

    /**
     * Logger for the class.
     */
    private static final Logger    log = LoggerFactory.getLogger(DefaultFeeCalendarService.class);

    private final FeeRepository    feeRepository;

    private final PersonRepository personRepository;

    public DefaultFeeCalendarService(final FeeRepository feeRepo, final PersonRepository personRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        personRepository = Objects.requireNonNull(personRepo);
    }

    @Override
    public final FeeCalendarYearsRange getRange() {
        final FeeCalendarYearsRange range;

        log.info("Getting fee calendar range");

        range = feeRepository.findRange();

        log.debug("Got fee calendar range: {}", range);

        return range;
    }

    @Override
    public final Collection<FeeCalendar> getYear(final Year year, final MemberStatus status, final Sorting sorting) {
        final Collection<Fee>         readFees;
        final Map<Object, List<Fee>>  memberFees;
        final Collection<FeeCalendar> calendarFees;
        final Collection<FeeCalendar> sortedCalendarFees;
        final Collection<Long>        memberNumbers;
        final Comparator<FeeCalendar> feeCalendarComparator;
        List<Fee>                     fees;
        FeeCalendar                   calendarFee;
        Collection<FeeCalendarMonth>  months;
        PersonName                    name;

        log.info("Getting fee calendar for year {} and status {}", year, status);

        // Select query based on status
        readFees = switch (status) {
            case ACTIVE -> feeRepository.findAllInYearForActiveMembers(year, sorting);
            case INACTIVE -> feeRepository.findAllInYearForInactiveMembers(year, sorting);
            default -> feeRepository.findAllInYear(year, sorting);
        };

        log.debug("Read fees: {}", readFees);

        // Member fees grouped by id
        memberFees = readFees.stream()
            .collect(Collectors.groupingBy(f -> f.member()
                .number()));
        log.debug("Member fees: {}", memberFees);

        // Sorted ids
        memberNumbers = readFees.stream()
            .map(Fee::member)
            .map(Fee.Member::number)
            .distinct()
            .sorted()
            .toList();
        log.debug("Member numbers: {}", memberNumbers);

        calendarFees = new ArrayList<>();
        for (final Long memberNumber : memberNumbers) {
            fees = memberFees.get(memberNumber);
            months = fees.stream()
                .map(this::toFeeMonth)
                // Sort by month
                .sorted(Comparator.comparing(FeeCalendarMonth::monthNumber))
                .toList();
            name = fees.iterator()
                .next()
                .member()
                .name();
            calendarFee = toFeeYear(memberNumber, name, status, year, months);
            calendarFees.add(calendarFee);
        }
        feeCalendarComparator = Comparator.comparing(fc -> normalizeString(fc.member()
            .name()
            .fullName()));
        sortedCalendarFees = calendarFees.stream()
            .sorted(feeCalendarComparator)
            .collect(Collectors.toList());

        log.debug("Got fee calendar for year {} and status {}: {}", year, status, sortedCalendarFees);

        return sortedCalendarFees;
    }

    private final String normalizeString(final String input) {
        // TODO: test this
        return Normalizer.normalize(input, Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "");
    }

    private final FeeCalendarMonth toFeeMonth(final Fee fee) {
        return new FeeCalendarMonth(fee.month(), fee.paid());
    }

    private final FeeCalendar toFeeYear(final Long personNumber, final PersonName name, final MemberStatus status,
            final Year year, final Collection<FeeCalendarMonth> months) {
        final boolean            active;
        final FeeCalendar.Member person;

        active = switch (status) {
            case ACTIVE -> true;
            case INACTIVE -> false;
            // TODO: get all active in a single query
            default -> personRepository.isActive(personNumber);
        };

        person = new FeeCalendar.Member(personNumber, name, active);
        return new FeeCalendar(person, months, year.getValue());
    }

}
