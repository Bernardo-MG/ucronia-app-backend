
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.person.domain.model.Person;

public record BookLending(Long number, Person person, LocalDate lendingDate, LocalDate returnDate) {

    public BookLending(final Long number, final Person person, final LocalDate lendingDate) {
        this(number, person, lendingDate, null);
    }
    
    public BookLending returned(final LocalDate date) {
        return new BookLending(number, person, lendingDate, date);
    }

}
