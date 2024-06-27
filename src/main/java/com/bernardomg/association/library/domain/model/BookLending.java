
package com.bernardomg.association.library.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class BookLending {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lendingDate;

    // TODO: person
    private long      member;

    private long      number;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
}
