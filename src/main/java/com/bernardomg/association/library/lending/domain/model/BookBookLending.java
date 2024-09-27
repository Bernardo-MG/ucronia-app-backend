
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.person.domain.model.Person;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "with")
public class BookBookLending {

    @EqualsAndHashCode.Include
    private LocalDate lendingDate;

    @EqualsAndHashCode.Include
    private Person    person;

    private LocalDate returnDate;

}
