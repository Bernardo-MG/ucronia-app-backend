
package com.bernardomg.association.funds.test.balance.util.model;

import com.bernardomg.association.funds.balance.model.CurrentBalance;

public final class CurrentBalances {

    public static final CurrentBalance amount(final float amount) {
        return CurrentBalance.builder()
            .results(amount)
            .total(amount)
            .build();
    }

    public static final CurrentBalance amount(final float amount, final float total) {
        return CurrentBalance.builder()
            .results(amount)
            .total(total)
            .build();
    }

    private CurrentBalances() {
        super();
    }

}
