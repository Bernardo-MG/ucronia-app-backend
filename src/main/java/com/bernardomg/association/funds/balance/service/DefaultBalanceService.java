
package com.bernardomg.association.funds.balance.service;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.bernardomg.association.funds.balance.model.Balance;
import com.bernardomg.association.funds.balance.model.ImmutableBalance;
import com.bernardomg.association.funds.balance.model.MonthlyBalance;
import com.bernardomg.association.funds.balance.model.mapper.BalanceMapper;
import com.bernardomg.association.funds.balance.persistence.repository.MonthlyBalanceRepository;
import com.bernardomg.association.funds.transaction.persistence.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultBalanceService implements BalanceService {

    private final BalanceMapper            mapper;

    private final MonthlyBalanceRepository monthlyBalanceRepository;

    private final TransactionRepository    transactionRepository;

    @Override
    public final Collection<? extends MonthlyBalance> getMonthlyBalance() {
        return monthlyBalanceRepository.findAll()
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    @Override
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

}
