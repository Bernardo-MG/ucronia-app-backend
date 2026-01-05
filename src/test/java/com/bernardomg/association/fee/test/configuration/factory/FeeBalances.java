
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.domain.model.FeeBalance;

public final class FeeBalances {

    public static final FeeBalance both() {
        return new FeeBalance(1L, 1L);
    }

    private FeeBalances() {
        super();
    }

}
