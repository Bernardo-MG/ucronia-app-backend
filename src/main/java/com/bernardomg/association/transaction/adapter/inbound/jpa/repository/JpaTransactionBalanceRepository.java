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
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.transaction.adapter.inbound.jpa.model.MonthlyBalanceEntity;
import com.bernardomg.association.transaction.adapter.inbound.jpa.specification.MonthlyBalanceSpecifications;
import com.bernardomg.association.transaction.domain.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.domain.repository.TransactionBalanceRepository;
import com.bernardomg.data.domain.Sorting;
import com.bernardomg.data.springframework.SpringSorting;

@Repository
@Transactional
public final class JpaTransactionBalanceRepository implements TransactionBalanceRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                  log = LoggerFactory.getLogger(JpaTransactionBalanceRepository.class);

    private final MonthlyBalanceSpringRepository monthlyBalanceRepository;

    public JpaTransactionBalanceRepository(final MonthlyBalanceSpringRepository monthlyBalanceRepo) {
        super();

        monthlyBalanceRepository = Objects.requireNonNull(monthlyBalanceRepo);
    }

    @Override
    public final Optional<TransactionCurrentBalance> findCurrent() {
        final Optional<MonthlyBalanceEntity>      readBalance;
        final Instant                             month;
        final Optional<TransactionCurrentBalance> currentBalance;
        final Instant                             balanceDate;
        final LocalDate                           balanceDateParsed;
        final LocalDate                           monthParsed;
        final float                               results;

        log.debug("Finding current balance");

        // Find latest monthly balance
        // Ignore future balances
        month = YearMonth.now()
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        readBalance = monthlyBalanceRepository.findLatestInOrBefore(month);

        if (readBalance.isEmpty()) {
            currentBalance = Optional.empty();
        } else {
            balanceDate = readBalance.get()
                .getMonth();

            // Take the results only if it's the current year and month
            balanceDateParsed = LocalDate.ofInstant(balanceDate, ZoneOffset.UTC);
            monthParsed = LocalDate.ofInstant(month, ZoneOffset.UTC);
            if ((balanceDateParsed.getYear() == monthParsed.getYear())
                    && (balanceDateParsed.getMonth() == monthParsed.getMonth())) {
                results = readBalance.get()
                    .getResults();
            } else {
                results = 0;
            }

            currentBalance = Optional.of(new TransactionCurrentBalance(results, readBalance.get()
                .getTotal()));
        }

        log.debug("Found current balance: {}", currentBalance);

        return currentBalance;
    }

    @Override
    public final Collection<TransactionMonthlyBalance> findMonthlyBalance(final TransactionBalanceQuery query,
            final Sorting sorting) {
        final Optional<Specification<MonthlyBalanceEntity>> requestSpec;
        final Specification<MonthlyBalanceEntity>           limitSpec;
        final Specification<MonthlyBalanceEntity>           spec;
        final Collection<MonthlyBalanceEntity>              balance;
        final Collection<TransactionMonthlyBalance>         monthlyBalance;
        final Sort                                          sort;
        final Instant                                       limit;

        log.debug("Finding monthly balance");

        // Specification from the request
        requestSpec = MonthlyBalanceSpecifications.fromQuery(query);
        // Up to this month
        limit = YearMonth.now()
            .plusMonths(1)
            .atDay(1)
            .atStartOfDay(ZoneOffset.UTC)
            .toInstant();
        limitSpec = MonthlyBalanceSpecifications.before(limit);

        // Combine specifications
        if (requestSpec.isPresent()) {
            spec = requestSpec.get()
                .and(limitSpec);
        } else {
            spec = limitSpec;
        }

        sort = SpringSorting.toSort(sorting);
        balance = monthlyBalanceRepository.findAll(spec, sort);

        monthlyBalance = balance.stream()
            .map(this::toDomain)
            .toList();

        log.debug("Found monthly balance {}", monthlyBalance);

        return monthlyBalance;
    }

    private final TransactionMonthlyBalance toDomain(final MonthlyBalanceEntity entity) {
        final YearMonth month;
        final LocalDate monthParsed;

        monthParsed = LocalDate.ofInstant(entity.getMonth(), ZoneOffset.UTC);
        month = YearMonth.of(monthParsed.getYear(), monthParsed.getMonth());
        return new TransactionMonthlyBalance(month, entity.getResults(), entity.getTotal());
    }

}
