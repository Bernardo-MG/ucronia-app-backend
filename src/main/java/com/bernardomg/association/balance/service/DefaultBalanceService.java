
package com.bernardomg.association.balance.service;

import org.springframework.stereotype.Service;

import com.bernardomg.association.balance.model.Balance;
import com.bernardomg.association.balance.model.DtoBalance;
import com.bernardomg.association.transaction.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultBalanceService implements BalanceService {

    private final TransactionRepository transactionRepository;

    @Override
    public final Balance getBalance() {
        final DtoBalance balance;
        final Long       sum;

        sum = transactionRepository.findSumAll();

        balance = new DtoBalance();
        balance.setQuantity(sum);

        return balance;
    }

}
