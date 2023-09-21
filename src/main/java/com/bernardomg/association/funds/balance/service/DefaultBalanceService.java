
package com.bernardomg.association.funds.balance.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

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
    public final Collection<? extends MonthlyBalance> getMonthlyBalance(final BalanceQuery query, final Sort sort) {
        final Optional<Specification<PersistentMonthlyBalance>> spec;
        final Collection<PersistentMonthlyBalance>              balance;

        spec = MonthlyBalanceSpecifications.fromRequest(query);

        if (spec.isPresent()) {
            balance = monthlyBalanceRepository.findAll(spec.get(), sort);
        } else {
            balance = monthlyBalanceRepository.findAll(sort);
        }

        return balance.stream()
            .map(this::toMonthlyBalance)
            .toList();
    }

    @Override
    public final MonthlyBalance getTotalBalance() {
        final Pageable                       page;
        final Sort                           sort;
        final Page<PersistentMonthlyBalance> balances;
        final PersistentMonthlyBalance       balance;
        final MonthlyBalance                 result;

        sort = Sort.by(Direction.DESC, "month");
        page = PageRequest.of(0, 1, sort);
        balances = monthlyBalanceRepository.findAll(page);
        if (balances.isEmpty()) {
            result = ImmutableMonthlyBalance.builder()
                .month(LocalDate.now())
                .cumulative(0F)
                .monthlyTotal(0F)
                .build();
        } else {
            balance = balances.iterator()
                .next();
            result = toMonthlyBalance(balance);
        }

        return result;
    }

    private final MonthlyBalance toMonthlyBalance(final PersistentMonthlyBalance entity) {
        return ImmutableMonthlyBalance.builder()
            .month(entity.getMonth())
            .cumulative(entity.getCumulative())
            .monthlyTotal(entity.getMonthlyTotal())
            .build();
    }

}
