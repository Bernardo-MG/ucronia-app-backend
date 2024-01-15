
package com.bernardomg.association.transaction.test.config.factory;

import com.bernardomg.association.transaction.model.TransactionCurrentBalance;

public final class TransactionCurrentBalances {

    public static final TransactionCurrentBalance amount(final float amount) {
        return TransactionCurrentBalance.builder()
            .results(amount)
            .total(amount)
            .build();
    }

    public static final TransactionCurrentBalance amount(final float amount, final float total) {
        return TransactionCurrentBalance.builder()
            .results(amount)
            .total(total)
            .build();
    }

    private TransactionCurrentBalances() {
        super();
    }

}
