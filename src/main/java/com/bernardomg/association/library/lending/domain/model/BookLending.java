
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.person.domain.model.Person;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class BookLending {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate lendingDate;

    private long      number;

    private Person    person;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

}
