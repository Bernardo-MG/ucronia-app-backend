
package com.bernardomg.association.library.adapter.outbound.rest.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class BookLent {

    private long      book;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lendingDate;

    private long      person;
}
