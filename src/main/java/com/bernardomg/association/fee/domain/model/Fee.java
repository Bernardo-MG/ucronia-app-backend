
package com.bernardomg.association.fee.domain.model;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class Fee {

    @JsonFormat(pattern = "yyyy-MM")
    private final YearMonth      date;

    private final boolean        paid;

    private final FeePerson      person;

    private final FeeTransaction transaction;

}
