
package com.bernardomg.association.fee.test.config.factory;

import java.util.List;

import com.bernardomg.association.fee.domain.model.FeeCalendarYearsRange;

public final class FeeCalendarYearsRanges {

    public static final FeeCalendarYearsRange current() {
        return FeeCalendarYearsRange.builder()
            .withYears(List.of(FeeConstants.YEAR))
            .build();
    }

    private FeeCalendarYearsRanges() {
        super();
    }

}
