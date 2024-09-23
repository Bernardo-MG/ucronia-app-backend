
package com.bernardomg.association.fee.test.configuration.factory;

import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;

public final class FeeCalendarYearsRanges {

    public static final FeeCalendarYearsRange current() {
        return FeeCalendarYearsRange.builder()
            .withYears(List.of(FeeConstants.YEAR_VALUE))
            .build();
    }

    private FeeCalendarYearsRanges() {
        super();
    }

}
