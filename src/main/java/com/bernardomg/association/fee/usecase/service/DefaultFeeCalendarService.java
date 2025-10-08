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
import com.bernardomg.association.fee.domain.model.MemberFees;
import com.bernardomg.association.fee.domain.model.YearsRange;
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
    public final YearsRange getRange() {
        final YearsRange range;

        log.info("Getting fee calendar range");

        range = feeRepository.findRange();

        log.debug("Got fee calendar range: {}", range);

        return range;
    }

    @Override
    public final Collection<MemberFees> getYear(final Year year, final MemberStatus status, final Sorting sorting) {
        final Collection<Fee>        readFees;
        final Map<Object, List<Fee>> memberFees;
        final Collection<MemberFees> calendarFees;
        final Collection<MemberFees> sortedCalendarFees;
        final Collection<Long>       memberNumbers;
        final Comparator<MemberFees> feeCalendarComparator;
        List<Fee>                    fees;
        MemberFees                   calendarFee;
        Collection<MemberFees.Fee>   membFees;
        PersonName                   name;

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
            membFees = fees.stream()
                .map(this::toMemberFee)
                .sorted(Comparator.comparing(MemberFees.Fee::month))
                .toList();
            name = fees.iterator()
                .next()
                .member()
                .name();
            calendarFee = toFeeYear(memberNumber, name, status, membFees);
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

    private final MemberFees toFeeYear(final Long personNumber, final PersonName name, final MemberStatus status,
            final Collection<MemberFees.Fee> fees) {
        final boolean           active;
        final MemberFees.Member person;

        active = switch (status) {
            case ACTIVE -> true;
            case INACTIVE -> false;
            // TODO: get all active in a single query
            default -> personRepository.isActive(personNumber);
        };

        person = new MemberFees.Member(personNumber, name, active);
        return new MemberFees(person, fees);
    }

    private final MemberFees.Fee toMemberFee(final Fee fee) {
        return new MemberFees.Fee(fee.month(), fee.paid());
    }

}
