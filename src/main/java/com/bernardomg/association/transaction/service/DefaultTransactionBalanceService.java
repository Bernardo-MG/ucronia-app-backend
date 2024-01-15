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

package com.bernardomg.association.transaction.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.transaction.model.TransactionBalanceQuery;
import com.bernardomg.association.transaction.model.TransactionCurrentBalance;
import com.bernardomg.association.transaction.model.TransactionMonthlyBalance;
import com.bernardomg.association.transaction.persistence.model.MonthlyBalanceEntity;
import com.bernardomg.association.transaction.persistence.repository.MonthlyBalanceRepository;
import com.bernardomg.association.transaction.persistence.specification.MonthlyBalanceSpecifications;

/**
 * Default implementation of the balance service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 */
public final class DefaultTransactionBalanceService implements TransactionBalanceService {

    private final MonthlyBalanceRepository monthlyBalanceRepository;

    public DefaultTransactionBalanceService(final MonthlyBalanceRepository monthlyBalanceRepo) {
        super();

        monthlyBalanceRepository = Objects.requireNonNull(monthlyBalanceRepo);
    }

    @Override
    public final TransactionCurrentBalance getBalance() {
        final MonthlyBalanceEntity           balance;
        final Optional<MonthlyBalanceEntity> readBalance;
        final TransactionCurrentBalance      currentBalance;
        final LocalDate                      month;
        final LocalDate                      balanceDate;
        final Float                          results;

        // Find latest monthly balance
        // Ignore future balances
        month = LocalDate.now()
            .withDayOfMonth(1);
        readBalance = monthlyBalanceRepository.findLatestInOrBefore(month);

        if (readBalance.isEmpty()) {
            currentBalance = TransactionCurrentBalance.builder()
                .total(0F)
                .results(0F)
                .build();
        } else {
            balance = readBalance.get();

            balanceDate = balance.getMonth();

            // Take the results only if it's the current year and month
            if ((balanceDate.getYear() == month.getYear()) && (balanceDate.getMonth()
                .equals(month.getMonth()))) {
                results = balance.getResults();
            } else {
                results = 0F;
            }

            currentBalance = TransactionCurrentBalance.builder()
                .total(balance.getTotal())
                .results(results)
                .build();
        }

        return currentBalance;
    }

    @Override
    public final Collection<TransactionMonthlyBalance> getMonthlyBalance(final TransactionBalanceQuery query,
            final Sort sort) {
        final Optional<Specification<MonthlyBalanceEntity>> requestSpec;
        final Specification<MonthlyBalanceEntity>           limitSpec;
        final Specification<MonthlyBalanceEntity>           spec;
        final Collection<MonthlyBalanceEntity>              balance;

        // Specification from the request
        requestSpec = MonthlyBalanceSpecifications.fromQuery(query);
        // Up to this month
        limitSpec = MonthlyBalanceSpecifications.before(YearMonth.now()
            .plusMonths(1));

        // Combine specifications
        if (requestSpec.isPresent()) {
            spec = requestSpec.get()
                .and(limitSpec);
        } else {
            spec = limitSpec;
        }

        balance = monthlyBalanceRepository.findAll(spec, sort);

        return balance.stream()
            .map(this::toMonthlyBalance)
            .toList();
    }

    private final TransactionMonthlyBalance toMonthlyBalance(final MonthlyBalanceEntity entity) {
        final YearMonth month;

        month = YearMonth.of(entity.getMonth()
            .getYear(),
            entity.getMonth()
                .getMonth());
        return TransactionMonthlyBalance.builder()
            .month(month)
            .total(entity.getTotal())
            .results(entity.getResults())
            .build();
    }

}
