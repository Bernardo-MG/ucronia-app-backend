
package com.bernardomg.association.transaction.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ImmutableTransaction implements Transaction {

    private final Float         amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime date;

    private final String        description;

    private final Long          id;

}
