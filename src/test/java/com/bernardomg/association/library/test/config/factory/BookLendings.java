
package com.bernardomg.association.library.test.config.factory;

import java.time.LocalDate;
import java.time.Month;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.person.test.config.factory.PersonConstants;

public final class BookLendings {

    public static final BookLending inPast() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(PersonConstants.NUMBER)
            .withLendingDate(LocalDate.of(2020, Month.JANUARY, 1))
            .build();
    }

    public static final BookLending lent() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(PersonConstants.NUMBER)
            .withLendingDate(BookConstants.LENT_DATE)
            .build();
    }

    public static final BookLending returned() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(PersonConstants.NUMBER)
            .withLendingDate(BookConstants.LENT_DATE)
            .withReturnDate(BookConstants.RETURNED_DATE)
            .build();
    }

}
