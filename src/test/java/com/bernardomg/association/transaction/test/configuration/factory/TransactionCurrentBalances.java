
package com.bernardomg.association.transaction.test.configuration.factory;

import com.bernardomg.association.transaction.domain.model.TransactionCurrentBalance;

public final class TransactionCurrentBalances {

    public static final TransactionCurrentBalance amount(final float amount) {
        return new TransactionCurrentBalance(amount, amount);
    }

    public static final TransactionCurrentBalance amount(final float amount, final float total) {
        return new TransactionCurrentBalance(amount, total);
    }

    private TransactionCurrentBalances() {
        super();
    }

}
