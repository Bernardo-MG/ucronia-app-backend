
package com.bernardomg.association.balance.service;

import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('BALANCE:READ')")
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
