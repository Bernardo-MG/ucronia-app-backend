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

package com.bernardomg.association.funds.balance.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.funds.balance.model.CurrentBalance;
import com.bernardomg.association.funds.balance.model.ImmutableCurrentBalance;
import com.bernardomg.association.funds.balance.model.ImmutableMonthlyBalance;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.model.request.BalanceQuery;
import com.bernardomg.association.funds.balance.persistence.model.PersistentMonthlyBalance;
import com.bernardomg.association.funds.balance.persistence.repository.MonthlyBalanceRepository;
import com.bernardomg.association.funds.balance.persistence.specification.MonthlyBalanceSpecifications;

public final class DefaultBalanceService implements BalanceService {

    private final MonthlyBalanceRepository monthlyBalanceRepository;

    public DefaultBalanceService(final MonthlyBalanceRepository monthlyBalanceRepo) {
        super();

        monthlyBalanceRepository = Objects.requireNonNull(monthlyBalanceRepo);
    }

    @Override
    public final CurrentBalance getBalance() {
        final PersistentMonthlyBalance           balance;
        final Optional<PersistentMonthlyBalance> readBalance;
        final CurrentBalance                     currentBalance;
        final LocalDate                          month;
        final Float                              results;

        month = LocalDate.now()
            .withDayOfMonth(1);
        readBalance = monthlyBalanceRepository.findLatest(month);
        if (readBalance.isEmpty()) {
            currentBalance = ImmutableCurrentBalance.builder()
                .total(0F)
                .results(0F)
                .build();
        } else {
            balance = readBalance.get();

            // Take the results only if it's the current month
            if (balance.getMonth()
                .getMonth()
                .equals(month.getMonth())) {
                results = balance.getResults();
            } else {
                results = 0F;
            }

            currentBalance = ImmutableCurrentBalance.builder()
                .total(balance.getTotal())
                .results(results)
                .build();
        }

        return currentBalance;
    }

    @Override
    public final Collection<? extends MonthlyBalance> getMonthlyBalance(final BalanceQuery query, final Sort sort) {
        final Optional<Specification<PersistentMonthlyBalance>> requestSpec;
        final Specification<PersistentMonthlyBalance>           limitSpec;
        final Specification<PersistentMonthlyBalance>           spec;
        final Collection<PersistentMonthlyBalance>              balance;

        requestSpec = MonthlyBalanceSpecifications.fromRequest(query);
        limitSpec = MonthlyBalanceSpecifications.before(YearMonth.now()
            .plusMonths(1));

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

    private final MonthlyBalance toMonthlyBalance(final PersistentMonthlyBalance entity) {
        final YearMonth month;

        month = YearMonth.of(entity.getMonth()
            .getYear(),
            entity.getMonth()
                .getMonth());
        return ImmutableMonthlyBalance.builder()
            .month(month)
            .total(entity.getTotal())
            .results(entity.getResults())
            .build();
    }

}
