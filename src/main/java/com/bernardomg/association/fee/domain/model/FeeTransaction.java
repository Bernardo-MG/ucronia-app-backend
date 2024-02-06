
package com.bernardomg.association.fee.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class FeeTransaction {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final Long      index;

}
