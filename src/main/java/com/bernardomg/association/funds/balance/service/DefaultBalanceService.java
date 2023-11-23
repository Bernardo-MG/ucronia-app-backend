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

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.funds.balance.model.CurrentBalance;
import com.bernardomg.association.funds.balance.model.ImmutableCurrentBalance;
import com.bernardomg.association.funds.balance.model.ImmutableMonthlyBalance;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.model.request.BalanceQuery;
import com.bernardomg.association.funds.balance.persistence.model.PersistentMonthlyBalance;
import com.bernardomg.association.funds.balance.persistence.repository.MonthlyBalanceRepository;
import com.bernardomg.association.funds.balance.persistence.repository.MonthlyBalanceSpecifications;

public final class DefaultBalanceService implements BalanceService {

    private final MonthlyBalanceRepository monthlyBalanceRepository;

    public DefaultBalanceService(final MonthlyBalanceRepository monthlyBalanceRepo) {
        super();

        monthlyBalanceRepository = Objects.requireNonNull(monthlyBalanceRepo);
    }

    @Override
    public final CurrentBalance getBalance() {
        final Pageable                       page;
        final Sort                           sort;
        final Page<PersistentMonthlyBalance> balances;
        final PersistentMonthlyBalance       balance;
        final CurrentBalance                 result;

        sort = Sort.by(Direction.DESC, "month");
        page = PageRequest.of(0, 1, sort);
        balances = monthlyBalanceRepository.findAll(page);
        if (balances.isEmpty()) {
            result = ImmutableCurrentBalance.builder()
                .total(0F)
                .difference(0F)
                .build();
        } else {
            balance = balances.iterator()
                .next();
            result = toCurrentBalance(balance);
        }

        return result;
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

    private final CurrentBalance toCurrentBalance(final PersistentMonthlyBalance entity) {
        return ImmutableCurrentBalance.builder()
            .total(entity.getTotal())
            .difference(entity.getDifference())
            .build();
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
            .difference(entity.getDifference())
            .build();
    }

}
