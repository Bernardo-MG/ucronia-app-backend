
package com.bernardomg.association.transaction.test.configuration.factory;

import com.bernardomg.association.transaction.domain.model.TransactionSummary;

public final class TransactionCurrentBalances {

    public static final TransactionSummary amount(final float amount) {
        return new TransactionSummary(amount, amount);
    }

    public static final TransactionSummary amount(final float amount, final float total) {
        return new TransactionSummary(amount, total);
    }

    private TransactionCurrentBalances() {
        super();
    }

}
