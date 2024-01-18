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

package com.bernardomg.association.member.usecase;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.bernardomg.association.member.delivery.model.MemberBalanceQuery;
import com.bernardomg.association.member.domain.model.MonthlyMemberBalance;
import com.bernardomg.association.member.infra.jpa.model.MonthlyMemberBalanceEntity;
import com.bernardomg.association.member.infra.jpa.repository.MonthlyMemberBalanceSpringRepository;
import com.bernardomg.association.member.infra.jpa.specification.MonthlyMemberBalanceSpecifications;

/**
 * Default implementation of the membership balance service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class DefaultMemberBalanceService implements MemberBalanceService {

    private final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepository;

    public DefaultMemberBalanceService(final MonthlyMemberBalanceSpringRepository monthlyMemberBalanceRepo) {
        super();

        monthlyMemberBalanceRepository = Objects.requireNonNull(monthlyMemberBalanceRepo);
    }

    @Override
    public final Iterable<MonthlyMemberBalance> getMonthlyBalance(final MemberBalanceQuery balance, final Sort sort) {
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
        return MonthlyMemberBalance.builder()
            .date(month)
            .total(entity.getTotal())
            .build();
    }

}
