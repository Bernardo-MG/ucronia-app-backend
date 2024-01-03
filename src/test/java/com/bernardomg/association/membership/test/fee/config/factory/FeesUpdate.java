
package com.bernardomg.association.membership.test.fee.config.factory;

import com.bernardomg.association.membership.fee.model.FeeChange;

public final class FeesUpdate {

    public static final FeeChange nextMonth() {
        return FeeChange.builder()
            .date(FeeConstants.NEXT_MONTH)
            .build();
    }

}
