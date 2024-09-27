
package com.bernardomg.association.library.lending.adapter.outbound.rest.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class BookReturned {

    private long      book;

    private long      person;

    private LocalDate returnDate;

}
