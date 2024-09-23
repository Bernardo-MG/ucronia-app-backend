
package com.bernardomg.association.transaction.test.configuration.factory;

import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;

public final class TransactionCurrentBalances {

    public static final TransactionCurrentBalance amount(final float amount) {
        return TransactionCurrentBalance.builder()
            .withResults(amount)
            .withTotal(amount)
            .build();
    }

    public static final TransactionCurrentBalance amount(final float amount, final float total) {
        return TransactionCurrentBalance.builder()
            .withResults(amount)
            .withTotal(total)
            .build();
    }

    private TransactionCurrentBalances() {
        super();
    }

}
