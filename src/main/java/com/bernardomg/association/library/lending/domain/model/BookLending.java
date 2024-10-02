
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.person.domain.model.Person;

public record BookLending(Long number, Person person, LocalDate lendingDate, LocalDate returnDate) {

}
