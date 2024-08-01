
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.person.domain.model.Person;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder(setterPrefix = "with")
public class BookBookLending {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @EqualsAndHashCode.Include
    private LocalDate lendingDate;

    @EqualsAndHashCode.Include
    private Person    person;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

}
