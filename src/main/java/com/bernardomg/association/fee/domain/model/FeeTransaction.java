
package com.bernardomg.association.fee.domain.model;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class FeeTransaction {

    private final LocalDate date;

    private final Long      index;

}
