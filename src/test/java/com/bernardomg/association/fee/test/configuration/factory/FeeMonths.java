
package com.bernardomg.association.fee.test.configuration.factory;

import com.bernardomg.association.fee.domain.model.FeeCalendar.FeeCalendarMonth;

public final class FeeMonths {

    public static final FeeCalendarMonth paid() {
        return new FeeCalendarMonth(FeeConstants.CURRENT_MONTH, true);
    }

    private FeeMonths() {
        super();
    }

}
