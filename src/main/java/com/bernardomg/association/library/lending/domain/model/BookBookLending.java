
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.person.domain.model.Person;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record BookBookLending(Person person, LocalDate lendingDate, LocalDate returnDate) {

}
