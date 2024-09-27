
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class Fee {

    /**
     * TODO: Rename to month
     */
    private final YearMonth      date;

    private final boolean        paid;

    private final FeePerson      person;

    private final FeeTransaction transaction;

}
