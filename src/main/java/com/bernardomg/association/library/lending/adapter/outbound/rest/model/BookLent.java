
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
public class BookLent {

    private long      book;

    private LocalDate lendingDate;

    private long      person;

}
