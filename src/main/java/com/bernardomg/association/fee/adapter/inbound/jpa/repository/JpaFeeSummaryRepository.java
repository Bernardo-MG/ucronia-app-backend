/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.fee.adapter.inbound.jpa.repository;

import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.fee.domain.model.FeeSummary;
import com.bernardomg.association.fee.domain.repository.FeeSummaryRepository;

@Repository
@Transactional
public final class JpaFeeSummaryRepository implements FeeSummaryRepository {

    /**
     * Logger for the class.
     */
    private static final Logger       log = LoggerFactory.getLogger(JpaFeeSummaryRepository.class);

    private final FeeSpringRepository feeSpringRepository;

    public JpaFeeSummaryRepository(final FeeSpringRepository feeSpringRepo) {
        super();

        feeSpringRepository = Objects.requireNonNull(feeSpringRepo);
    }

    @Override
    public final FeeSummary findForMonth(final YearMonth date) {
        final FeeSummary summary;
        final Instant    dateParsed;

        log.debug("Finding summary for month {}", date);

        dateParsed = date.atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        summary = feeSpringRepository.findBalanceForMonth(dateParsed);

        log.debug("Found summary for month {}: {}", date, summary);

        return summary;
    }

}
