
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.domain.model.FeeSummary;

public final class FeeSummaries {

    public static final FeeSummary both() {
        return new FeeSummary(1L, 1L);
    }

    private FeeSummaries() {
        super();
    }

}
