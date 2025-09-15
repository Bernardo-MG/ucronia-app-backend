
package com.bernardomg.association.transaction.adapter.outbound.rest.model;

import java.util.Collection;

import com.bernardomg.association.transaction.domain.model.TransactionMonthlyBalance;
import com.bernardomg.ucronia.openapi.model.TransactionMonthlyBalanceDto;
import com.bernardomg.ucronia.openapi.model.TransactionMonthlyBalanceResponseDto;

public final class TransactionBalanceDtoMapper {

    public static final TransactionMonthlyBalanceResponseDto
            toResponseDto(final Collection<TransactionMonthlyBalance> balance) {
        return new TransactionMonthlyBalanceResponseDto().content(balance.stream()
            .map(TransactionBalanceDtoMapper::toDto)
            .toList());
    }

    private static final TransactionMonthlyBalanceDto toDto(final TransactionMonthlyBalance balance) {
        return new TransactionMonthlyBalanceDto().month(balance.month())
            .results(balance.results())
            .total(balance.total());
    }

    private TransactionBalanceDtoMapper() {
        super();
    }

}
