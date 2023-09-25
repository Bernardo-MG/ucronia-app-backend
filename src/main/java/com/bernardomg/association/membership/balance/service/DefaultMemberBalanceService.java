
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
import com.bernardomg.association.membership.balance.persistence.model.PersistentMonthlyMemberBalance;
import com.bernardomg.association.membership.balance.persistence.repository.MonthlyMemberBalanceRepository;
import com.bernardomg.association.membership.balance.persistence.repository.MonthlyMemberBalanceSpecifications;

/**
 * Default implementation of the member service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
public final class DefaultMemberBalanceService implements MemberBalanceService {

    private final MonthlyMemberBalanceRepository monthlyMemberBalanceRepository;

    public DefaultMemberBalanceService(final MonthlyMemberBalanceRepository monthlyMemberBalanceRepo) {
        super();

        monthlyMemberBalanceRepository = Objects.requireNonNull(monthlyMemberBalanceRepo);
    }

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
            .total(entity.getTotal())
            .build();
    }

}
