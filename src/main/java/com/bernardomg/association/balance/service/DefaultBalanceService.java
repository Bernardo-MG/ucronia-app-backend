
package com.bernardomg.association.balance.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bernardomg.association.balance.model.Balance;
import com.bernardomg.association.balance.model.ImmutableBalance;
import com.bernardomg.association.transaction.persistence.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public final class DefaultBalanceService implements BalanceService {

    private final TransactionRepository transactionRepository;

    @Override
    @PreAuthorize("hasAuthority('BALANCE:READ')")
    public final Balance getBalance() {
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
