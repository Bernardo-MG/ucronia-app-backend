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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.model.FeePaymentReport;
import com.bernardomg.association.fee.domain.repository.FeeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Default implementation of the fee report service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
@Slf4j
@Service
@Transactional
public final class DefaultFeeReportService implements FeeReportService {

    private final FeeRepository feeRepository;

    public DefaultFeeReportService(final FeeRepository feeRepo) {
        super();

        feeRepository = Objects.requireNonNull(feeRepo);
    }

    @Override
    public final FeePaymentReport getPaymentReport() {
        final Collection<Fee>  fees;
        final long             paid;
        final long             unpaid;
        final FeePaymentReport report;

        log.info("Getting payment report");

        // TODO: user a smaller query
        fees = feeRepository.findAllInMonth(YearMonth.now());
        paid = fees.stream()
            .filter(Fee::paid)
            .count();
        unpaid = fees.stream()
            .filter(Predicate.not(Fee::paid))
            .count();

        report = new FeePaymentReport(paid, unpaid);

        log.debug("Got payment report: {}", report);

        return report;
    }

}
