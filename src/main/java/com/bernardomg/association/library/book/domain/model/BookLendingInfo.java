
package com.bernardomg.association.library.book.domain.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.bernardomg.association.library.lending.domain.model.BookLending.Borrower;

public record BookLendingInfo(Borrower borrower, Instant lendingDate, Instant returnDate) {

    public Long getDays() {
        final Long days;

        // TODO: don't generate, set on creation
        if (returnDate == null) {
            days = ChronoUnit.DAYS.between(lendingDate, Instant.now()) + 1;
        } else {
            days = ChronoUnit.DAYS.between(lendingDate, returnDate) + 1;
        }

        return days;
    }

}
