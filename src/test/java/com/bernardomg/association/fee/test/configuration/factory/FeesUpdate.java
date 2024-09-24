
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.domain.model.FeeChange;

public final class FeesUpdate {

    public static final FeeChange nextMonth() {
        return FeeChange.builder()
            .withDate(FeeConstants.NEXT_MONTH)
            .build();
    }

}
