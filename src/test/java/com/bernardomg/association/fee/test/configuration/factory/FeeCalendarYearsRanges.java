
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;

public final class FeeCalendarYearsRanges {

    public static final FeeCalendarYearsRange current() {
        return new FeeCalendarYearsRange(List.of(FeeConstants.YEAR_VALUE));
    }

    private FeeCalendarYearsRanges() {
        super();
    }

}
