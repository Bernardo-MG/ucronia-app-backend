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

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeePerson;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.member.domain.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee maintenance service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Slf4j
@Transactional
public final class DefaultFeeMaintenanceService implements FeeMaintenanceService {

    private final FeeRepository    feeRepository;

    private final MemberRepository memberRepository;

    public DefaultFeeMaintenanceService(final FeeRepository feeRepo, final MemberRepository memberRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        memberRepository = Objects.requireNonNull(memberRepo);
    }

    @Override
    public final void registerMonthFees() {
        final Collection<Fee>  feesToExtend;
        final Collection<Fee>  feesToCreate;
        final Collection<Long> numbers;

        // Find fees to extend into the current month
        feesToExtend = feeRepository.findAllForPreviousMonth();

        feesToCreate = feesToExtend.stream()
            // Prepare for the current month
            .map(this::toCurrentMonth)
            // Make sure the user is active
            .filter(this::isActive)
            // Make sure it doesn't exist
            .filter(this::notExists)
            .toList();

        log.debug("Registering {} fees for this month", feesToCreate.size());
        feeRepository.save(feesToCreate);

        // Makes sure these members are active
        numbers = feesToCreate.stream()
            .map(Fee::getPerson)
            .map(FeePerson::getNumber)
            .toList();
        memberRepository.activate(numbers);
    }

    private final boolean isActive(final Fee fee) {
        return memberRepository.isActive(fee.getPerson()
            .getNumber());
    }

    private final boolean notExists(final Fee fee) {
        return !feeRepository.exists(fee.getPerson()
            .getNumber(), fee.getDate());
    }

    private final Fee toCurrentMonth(final Fee fee) {
        final FeePerson person;

        person = FeePerson.builder()
            .withNumber(fee.getPerson()
                .getNumber())
            .build();
        return Fee.builder()
            .withPerson(person)
            .withDate(YearMonth.now())
            .build();
    }

}
