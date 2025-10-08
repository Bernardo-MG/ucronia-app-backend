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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.person.domain.model.Person;
import com.bernardomg.association.person.domain.repository.PersonRepository;

/**
 * Default implementation of the fee maintenance service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service
@Transactional
public final class DefaultFeeMaintenanceService implements FeeMaintenanceService {

    /**
     * Logger for the class.
     */
    private static final Logger    log = LoggerFactory.getLogger(DefaultFeeMaintenanceService.class);

    private final FeeRepository    feeRepository;

    private final PersonRepository personRepository;

    public DefaultFeeMaintenanceService(final FeeRepository feeRepo, final PersonRepository personRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
        personRepository = Objects.requireNonNull(personRepo);
    }

    @Override
    public final void registerMonthFees() {
        final Collection<Fee>    feesToCreate;
        final Collection<Person> toRenew;

        log.info("Registering fees for this month");

        // Find fees to extend into the current month
        toRenew = personRepository.findAllToRenew();
        feesToCreate = toRenew.stream()
            // Prepare for the current month
            .map(this::toUnpaidThisMonth)
            // Make sure it doesn't exist
            .filter(this::notExists)
            .toList();

        feeRepository.save(feesToCreate);

        log.debug("Registered {} fees for this month", feesToCreate.size());
    }

    private final boolean notExists(final Fee fee) {
        return !feeRepository.exists(fee.member()
            .number(), fee.month());
    }

    private final Fee toUnpaidThisMonth(final Person feePerson) {
        final Fee.Member person;

        person = new Fee.Member(feePerson.number(), feePerson.name());
        return Fee.unpaid(YearMonth.now(), person);
    }

}
