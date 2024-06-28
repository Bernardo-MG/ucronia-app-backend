
package com.bernardomg.association.library.test.config.factory;

import java.time.LocalDate;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.person.test.config.factory.PersonConstants;

public final class BookLendings {

    public static final BookLending lent() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(PersonConstants.NUMBER)
            .withLendingDate(BookConstants.LENT_DATE)
            .build();
    }

    public static final BookLending lentAlternativePerson() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(PersonConstants.ALTERNATIVE_NUMBER)
            .withLendingDate(BookConstants.LENT_DATE)
            .build();
    }

    public static final BookLending lentAtReturn() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(PersonConstants.NUMBER)
            .withLendingDate(BookConstants.RETURNED_DATE)
            .build();
    }

    public static final BookLending lentLastNoPerson() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(0)
            .withLendingDate(BookConstants.LENT_DATE_LAST)
            .build();
    }

    public static final BookLending lentNoPerson() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(0)
            .withLendingDate(BookConstants.LENT_DATE)
            .build();
    }

    public static final BookLending lentToday() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(PersonConstants.NUMBER)
            .withLendingDate(LocalDate.now())
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

    public static final BookLending returnedAlternative() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(PersonConstants.ALTERNATIVE_NUMBER)
            .withLendingDate(BookConstants.LENT_DATE)
            .withReturnDate(BookConstants.RETURNED_DATE)
            .build();
    }

    public static final BookLending returnedLastNoPerson() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(0)
            .withLendingDate(BookConstants.LENT_DATE_LAST)
            .withReturnDate(BookConstants.RETURNED_DATE_LAST)
            .build();
    }

    public static final BookLending returnedNoPerson() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(0)
            .withLendingDate(BookConstants.LENT_DATE)
            .withReturnDate(BookConstants.RETURNED_DATE)
            .build();
    }

}
