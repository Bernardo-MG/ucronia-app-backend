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
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeeBalance;
import com.bernardomg.association.fee.domain.repository.FeeRepository;

/**
 * Default implementation of the fee report service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Service
@Transactional
public final class DefaultFeeBalanceService implements FeeBalanceService {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(DefaultFeeBalanceService.class);

    private final FeeRepository feeRepository;

    public DefaultFeeBalanceService(final FeeRepository feeRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final FeeBalance getFeeBalance() {
        final Collection<Fee> fees;
        final long            paid;
        final long            unpaid;
        final FeeBalance      report;

        log.info("Getting payment report");

        // TODO: user a smaller query
        fees = feeRepository.findAllInMonth(YearMonth.now());
        paid = fees.stream()
            .filter(Fee::paid)
            .count();
        unpaid = fees.stream()
            .filter(Predicate.not(Fee::paid))
            .count();

        report = new FeeBalance(paid, unpaid);

        log.debug("Got payment report: {}", report);

        return report;
    }

}
