
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;

import com.bernardomg.association.person.domain.model.PersonName;

public record BookLending(long number, Borrower borrower, LocalDate lendingDate, LocalDate returnDate) {

    public BookLending(final long number, final Borrower borrower, final LocalDate lendingDate) {
        this(number, borrower, lendingDate, null);
    }

    public BookLending returned(final LocalDate date) {
        return new BookLending(number, borrower, lendingDate, date);
    }

    public record Borrower(long number, PersonName name) {

    }

}
