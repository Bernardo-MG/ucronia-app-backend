
package com.bernardomg.association.library.adapter.outbound.rest.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class BookReturned {

    private long      book;

    private long      person;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
}
