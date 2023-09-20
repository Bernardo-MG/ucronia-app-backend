
package com.bernardomg.association.funds.balance.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.bernardomg.association.funds.balance.model.Balance;
import com.bernardomg.association.funds.balance.model.BalanceQuery;
import com.bernardomg.association.funds.balance.model.ImmutableBalance;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.model.mapper.BalanceMapper;
import com.bernardomg.association.funds.balance.persistence.model.PersistentMonthlyBalance;
import com.bernardomg.association.funds.balance.persistence.repository.MonthlyBalanceRepository;
import com.bernardomg.association.funds.balance.persistence.repository.MonthlyBalanceSpecifications;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultBalanceService implements BalanceService {

    private final BalanceMapper            mapper;

    private final MonthlyBalanceRepository monthlyBalanceRepository;

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
            .map(mapper::toDto)
            .toList();
    }

    @Override
    public final Balance getTotalBalance() {
        final Float                          sum;
        final Pageable                       page;
        final Sort                           sort;
        final Page<PersistentMonthlyBalance> balances;
        final PersistentMonthlyBalance       balance;

        sort = Sort.by(Direction.DESC, "month");
        page = PageRequest.of(0, 1, sort);
        balances = monthlyBalanceRepository.findAll(page);
        if (balances.isEmpty()) {
            sum = 0F;
        } else {
            balance = balances.iterator()
                .next();
            sum = balance.getCumulative();
        }

        return ImmutableBalance.builder()
            .amount(sum)
            .build();
    }

}
