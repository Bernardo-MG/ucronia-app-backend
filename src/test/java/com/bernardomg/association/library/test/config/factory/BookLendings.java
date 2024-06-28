
package com.bernardomg.association.library.test.config.factory;

import java.time.LocalDate;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.person.test.config.factory.Persons;

public final class BookLendings {

    public static final BookLending lent() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(Persons.valid())
            .withLendingDate(BookConstants.LENT_DATE)
            .build();
    }

    public static final BookLending lentAlternativePerson() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(Persons.valid())
            .withLendingDate(BookConstants.LENT_DATE)
            .build();
    }

    public static final BookLending lentAtReturn() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(Persons.valid())
            .withLendingDate(BookConstants.RETURNED_DATE)
            .build();
    }

    public static final BookLending lentLast() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(Persons.valid())
            .withLendingDate(BookConstants.LENT_DATE_LAST)
            .build();
    }

    public static final BookLending lentToday() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(Persons.valid())
            .withLendingDate(LocalDate.now())
            .build();
    }

    public static final BookLending returned() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(Persons.valid())
            .withLendingDate(BookConstants.LENT_DATE)
            .withReturnDate(BookConstants.RETURNED_DATE)
            .build();
    }

    public static final BookLending returnedAlternative() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(Persons.alternative())
            .withLendingDate(BookConstants.LENT_DATE)
            .withReturnDate(BookConstants.RETURNED_DATE)
            .build();
    }

    public static final BookLending returnedLast() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(Persons.valid())
            .withLendingDate(BookConstants.LENT_DATE_LAST)
            .withReturnDate(BookConstants.RETURNED_DATE_LAST)
            .build();
    }

}
