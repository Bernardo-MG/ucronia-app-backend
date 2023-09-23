
package com.bernardomg.association.membership.balance.service;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bernardomg.association.membership.balance.model.ImmutableMonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.MonthlyMemberBalance;
import com.bernardomg.association.membership.balance.model.request.MemberBalanceQuery;
import com.bernardomg.association.membership.balance.persistence.model.PersistentMonthlyMemberBalance;
import com.bernardomg.association.membership.balance.persistence.repository.MonthlyMemberBalanceRepository;
import com.bernardomg.association.membership.balance.persistence.repository.MonthlyMemberBalanceSpecifications;

import lombok.AllArgsConstructor;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Service
@AllArgsConstructor
public final class DefaultMemberBalanceService implements MemberBalanceService {

    @Autowired
    private final MonthlyMemberBalanceRepository monthlyMemberBalanceRepository;

    @Override
    public final Iterable<? extends MonthlyMemberBalance> getBalance(final MemberBalanceQuery query, final Sort sort) {
        final Optional<Specification<PersistentMonthlyMemberBalance>> requestSpec;
        final Specification<PersistentMonthlyMemberBalance>           limitSpec;
        final Specification<PersistentMonthlyMemberBalance>           spec;
        final Collection<PersistentMonthlyMemberBalance>              balance;

        requestSpec = MonthlyMemberBalanceSpecifications.fromRequest(query);
        limitSpec = MonthlyMemberBalanceSpecifications.before(YearMonth.now()
            .plusMonths(1));

        if (requestSpec.isPresent()) {
            spec = requestSpec.get()
                .and(limitSpec);
        } else {
            spec = limitSpec;
        }

        balance = monthlyMemberBalanceRepository.findAll(spec, sort);

        return balance.stream()
            .map(this::toMonthlyBalance)
            .toList();
    }

    private final MonthlyMemberBalance toMonthlyBalance(final PersistentMonthlyMemberBalance entity) {
        final YearMonth month;

        month = YearMonth.of(entity.getMonth()
            .getYear(),
            entity.getMonth()
                .getMonth());
        return ImmutableMonthlyMemberBalance.builder()
            .month(month)
            .total(entity.getMonthlyTotal())
            .build();
    }

}
