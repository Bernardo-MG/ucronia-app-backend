
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.domain.model.FeeType;

public final class FeeTypes {

    public static final FeeType nameChange() {
        return new FeeType(FeeTypeConstants.NUMBER, FeeTypeConstants.ALTERNATIVE_NAME, FeeTypeConstants.AMOUNT);
    }

    public static final FeeType positive() {
        return new FeeType(FeeTypeConstants.NUMBER, FeeTypeConstants.NAME, FeeTypeConstants.AMOUNT);
    }

    private FeeTypes() {
        super();
    }

}
