
package com.bernardomg.association.balance.service;

import java.util.Collection;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.balance.model.Balance;
import com.bernardomg.association.balance.model.ImmutableBalance;
import com.bernardomg.association.balance.model.ImmutableMonthlyBalance;
import com.bernardomg.association.balance.model.MonthlyBalance;
import com.bernardomg.association.balance.persistence.model.PersistentMonthlyBalance;
import com.bernardomg.association.balance.persistence.repository.MonthlyBalanceRepository;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultBalanceService implements BalanceService {

    private final MonthlyBalanceRepository monthlyBalanceRepository;

    private final TransactionRepository    transactionRepository;

    @Override
    public final Collection<MonthlyBalance> getMonthlyBalance() {
        return monthlyBalanceRepository.findAll()
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Override
    @PreAuthorize("hasAuthority('BALANCE:READ')")
    public final Balance getTotalBalance() {
        final Float readSum;
        final Float sum;

        readSum = transactionRepository.sumAll();
        if (readSum == null) {
            sum = 0F;
        } else {
            sum = readSum;
        }

        return ImmutableBalance.builder()
            .amount(sum)
            .build();
    }

    private final MonthlyBalance toDto(final PersistentMonthlyBalance entity) {
        return ImmutableMonthlyBalance.builder()
            .date(entity.getDate())
            .total(entity.getTotal())
            .cumulative(entity.getCumulative())
            .build();
    }

}
