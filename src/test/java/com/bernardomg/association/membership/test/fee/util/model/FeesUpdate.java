
package com.bernardomg.association.membership.test.fee.util.model;

import com.bernardomg.association.membership.fee.model.FeeUpdate;

public final class FeesUpdate {

    public static final FeeUpdate notPaid() {
        return FeeUpdate.builder()
            .paid(false)
            .build();
    }

    public static final FeeUpdate paid() {
        return FeeUpdate.builder()
            .paid(true)
            .build();
    }

}
