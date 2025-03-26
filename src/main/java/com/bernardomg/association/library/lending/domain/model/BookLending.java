
package com.bernardomg.association.library.lending.domain.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.person.domain.model.PersonName;

public record BookLending(LentBook book, Borrower borrower, LocalDate lendingDate, LocalDate returnDate) {

    public BookLending(final LentBook book, final Borrower borrower, final LocalDate lendingDate) {
        this(book, borrower, lendingDate, null);
    }

    public BookLending returned(final LocalDate date) {
        return new BookLending(book, borrower, lendingDate, date);
    }

    public record LentBook(long number, Title title) {

    }

    public record Borrower(long number, PersonName name) {

    }

    public Long getDays() {
        final Long days;

        if (returnDate == null) {
            days = ChronoUnit.DAYS.between(lendingDate, LocalDate.now()) + 1;
        } else {
            days = ChronoUnit.DAYS.between(lendingDate, returnDate) + 1;
        }

        return days;
    }

}
