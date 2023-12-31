
package com.bernardomg.association.membership.test.fee.util.model;

import com.bernardomg.association.membership.fee.model.FeeUpdate;

public final class FeesUpdate {

    public static final FeeUpdate name() {
        return FeeUpdate.builder()
            .name("abc")
            .build();
    }

}
