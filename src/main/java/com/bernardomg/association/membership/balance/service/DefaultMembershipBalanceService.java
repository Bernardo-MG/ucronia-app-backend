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

package com.bernardomg.association.membership.balance.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.membership.balance.model.ImmutableMonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQuery;
import com.bernardomg.association.membership.balance.persistence.model.MonthlyMemberBalanceEntity;
import com.bernardomg.association.membership.balance.persistence.repository.MonthlyMemberBalanceRepository;
import com.bernardomg.association.membership.balance.persistence.repository.MonthlyMemberBalanceSpecifications;

/**
 * Default implementation of the membership balance service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class DefaultMembershipBalanceService implements MembershipBalanceService {

    private final MonthlyMemberBalanceRepository monthlyMemberBalanceRepository;

    public DefaultMembershipBalanceService(final MonthlyMemberBalanceRepository monthlyMemberBalanceRepo) {
        super();

        monthlyMemberBalanceRepository = Objects.requireNonNull(monthlyMemberBalanceRepo);
    }

    @Override
    public final Iterable<MonthlyMemberBalance> getBalance(final MemberBalanceQuery balance,
            final Sort sort) {
        final Optional<Specification<MonthlyMemberBalanceEntity>> requestSpec;
        final Specification<MonthlyMemberBalanceEntity>           limitSpec;
        final Specification<MonthlyMemberBalanceEntity>           spec;
        final Collection<MonthlyMemberBalanceEntity>              balances;

        // Specification from the request
        requestSpec = MonthlyMemberBalanceSpecifications.fromQuery(balance);
        // Up to this month
        limitSpec = MonthlyMemberBalanceSpecifications.before(YearMonth.now()
            .plusMonths(1));

        // Combine specifications
        if (requestSpec.isPresent()) {
            spec = requestSpec.get()
                .and(limitSpec);
        } else {
            spec = limitSpec;
        }

        balances = monthlyMemberBalanceRepository.findAll(spec, sort);

        return balances.stream()
            .map(this::toMonthlyBalance)
            .toList();
    }

    private final MonthlyMemberBalance toMonthlyBalance(final MonthlyMemberBalanceEntity entity) {
        final YearMonth month;

        month = YearMonth.of(entity.getMonth()
            .getYear(),
            entity.getMonth()
                .getMonth());
        return ImmutableMonthlyMemberBalance.builder()
            .month(month)
            .total(entity.getTotal())
            .build();
    }

}
