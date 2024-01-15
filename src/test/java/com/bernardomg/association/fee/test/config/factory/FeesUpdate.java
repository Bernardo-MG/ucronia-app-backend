
package com.bernardomg.association.fee.test.config.factory;

import com.bernardomg.association.fee.model.FeeChange;

public final class FeesUpdate {

    public static final FeeChange nextMonth() {
        return FeeChange.builder()
            .date(FeeConstants.NEXT_MONTH)
            .build();
    }

}
