
package com.bernardomg.association.fee.test.configuration.factory;

import java.time.Year;
import java.util.List;

import com.bernardomg.association.member.domain.model.YearsRange;

public final class YearsRanges {

    public static final YearsRange current() {
        return new YearsRange(List.of(Year.of(FeeConstants.YEAR_VALUE)));
    }

    private YearsRanges() {
        super();
    }

}
