
package com.bernardomg.association.fee.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class FeeTransaction {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate date;

    private final Long      index;

}
