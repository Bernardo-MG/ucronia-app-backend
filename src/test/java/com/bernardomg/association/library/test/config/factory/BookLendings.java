
package com.bernardomg.association.library.test.config.factory;

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

    public static final BookLending lentNoPerson() {
        return BookLending.builder()
            .withNumber(BookConstants.NUMBER)
            .withPerson(0)
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

}
