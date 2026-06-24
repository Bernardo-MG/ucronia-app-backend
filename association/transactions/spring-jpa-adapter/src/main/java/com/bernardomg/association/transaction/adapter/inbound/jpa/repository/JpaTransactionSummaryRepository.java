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

package com.bernardomg.association.transaction.adapter.inbound.jpa.repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.MonthlyEvolutionEntity;
import com.bernardomg.association.transaction.domain.model.TransactionSummary;
import com.bernardomg.association.transaction.domain.repository.TransactionSummaryRepository;

public final class JpaTransactionSummaryRepository implements TransactionSummaryRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                    log = LoggerFactory.getLogger(JpaTransactionSummaryRepository.class);

    private final MonthlyEvolutionSpringRepository monthlyEvolutionRepository;

    public JpaTransactionSummaryRepository(final MonthlyEvolutionSpringRepository monthlyEvolutionRepo) {
        super();

        monthlyEvolutionRepository = Objects.requireNonNull(monthlyEvolutionRepo);
    }

    @Override
    public final Optional<TransactionSummary> findSummary() {
        final Optional<MonthlyEvolutionEntity> readEvolution;
        final Instant                          month;
        final Optional<TransactionSummary>     currentEvolution;
        final Instant                          evolutionDate;
        final LocalDate                        evolutionDateParsed;
        final LocalDate                        monthParsed;
        final float                            results;

        log.debug("Finding current evolution");

        // Find latest monthly evolution
        // Ignore future evolutions
        month = YearMonth.now()
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        readEvolution = monthlyEvolutionRepository.findLatestInOrBefore(month);

        if (readEvolution.isEmpty()) {
            currentEvolution = Optional.empty();
        } else {
            evolutionDate = readEvolution.get()
                .getMonth();

            // Take the results only if it's the current year and month
            // TODO: why local date?
            evolutionDateParsed = LocalDate.ofInstant(evolutionDate, ZoneOffset.UTC);
            monthParsed = LocalDate.ofInstant(month, ZoneOffset.UTC);
            if ((evolutionDateParsed.getYear() == monthParsed.getYear())
                    && (evolutionDateParsed.getMonth() == monthParsed.getMonth())) {
                results = readEvolution.get()
                    .getResults();
            } else {
                results = 0;
            }

            currentEvolution = Optional.of(new TransactionSummary(results, readEvolution.get()
                .getTotal()));
        }

        log.debug("Found current evolution: {}", currentEvolution);

        return currentEvolution;
    }

}
