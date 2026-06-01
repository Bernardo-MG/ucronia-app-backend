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
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.MonthlyEvolutionEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.model.TransactionEvolutionEntityMapper;
import com.bernardomg.association.transaction.adapter.inbound.jpa.specification.MonthlyEvolutionSpecifications;
import com.bernardomg.association.transaction.domain.model.TransactionEvolutionMonth;
import com.bernardomg.association.transaction.domain.repository.TransactionEvolutionRepository;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringSorting;

import jakarta.transaction.Transactional;

@Transactional
public final class JpaTransactionEvolutionRepository implements TransactionEvolutionRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                    log = LoggerFactory
        .getLogger(JpaTransactionEvolutionRepository.class);

    private final MonthlyEvolutionSpringRepository monthlyEvolutionRepository;

    public JpaTransactionEvolutionRepository(final MonthlyEvolutionSpringRepository monthlyEvolutionRepo) {
        super();

        monthlyEvolutionRepository = Objects.requireNonNull(monthlyEvolutionRepo);
    }

    @Override
    public final Collection<TransactionEvolutionMonth> findEvolution(final Instant from, final Instant to,
            final Sorting sorting) {
        final Optional<Specification<MonthlyEvolutionEntity>> requestSpec;
        final Specification<MonthlyEvolutionEntity>           limitSpec;
        final Specification<MonthlyEvolutionEntity>           spec;
        final Collection<MonthlyEvolutionEntity>              evolution;
        final Collection<TransactionEvolutionMonth>           monthlyEvolution;
        final Sort                                            sort;
        final Instant                                         limit;

        log.debug("Finding monthly evolution");

        // Specification from the request
        requestSpec = MonthlyEvolutionSpecifications.fromQuery(from, to);
        // Up to this month
        limit = YearMonth.now()
            .plusMonths(1)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        limitSpec = MonthlyEvolutionSpecifications.before(limit);

        // Combine specifications
        if (requestSpec.isPresent()) {
            spec = requestSpec.get()
                .and(limitSpec);
        } else {
            spec = limitSpec;
        }

        sort = SpringSorting.toSort(sorting);
        evolution = monthlyEvolutionRepository.findAll(spec, sort);

        monthlyEvolution = evolution.stream()
            .map(TransactionEvolutionEntityMapper::toDomain)
            .toList();

        log.debug("Found monthly evolution {}", monthlyEvolution);

        return monthlyEvolution;
    }

}
