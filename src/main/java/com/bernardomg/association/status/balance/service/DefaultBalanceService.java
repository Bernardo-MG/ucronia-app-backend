
package com.bernardomg.association.status.balance.service;

import org.springframework.stereotype.Service;

import com.bernardomg.association.crud.transaction.repository.TransactionRepository;
import com.bernardomg.association.status.balance.model.Balance;
import com.bernardomg.association.status.balance.model.DtoBalance;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultBalanceService implements BalanceService {

    private final TransactionRepository transactionRepository;

    @Override
    public final Balance getBalance() {
        final DtoBalance balance;
        final Long       readSum;
        final Long       sum;

        readSum = transactionRepository.findSumAll();
        if (readSum == null) {
            sum = 0L;
        } else {
            sum = readSum;
        }

        balance = new DtoBalance();
        balance.setAmount(sum);

        return balance;
    }

}
