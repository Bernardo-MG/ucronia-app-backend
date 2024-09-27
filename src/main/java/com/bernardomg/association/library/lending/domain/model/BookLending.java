
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.person.domain.model.Person;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class BookLending {

    private LocalDate lendingDate;

    private long      number;

    private Person    person;

    private LocalDate returnDate;

}
