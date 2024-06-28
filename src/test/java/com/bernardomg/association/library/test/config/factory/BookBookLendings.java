
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.domain.model.BookBookLending;
import com.bernardomg.association.person.test.config.factory.Persons;

public final class BookBookLendings {

    public static final BookBookLending lent() {
        return BookBookLending.builder()
            .withPerson(Persons.valid())
            .withLendingDate(BookConstants.LENT_DATE)
            .build();
    }

    public static final BookBookLending returned() {
        return BookBookLending.builder()
            .withPerson(Persons.valid())
            .withLendingDate(BookConstants.LENT_DATE)
            .withReturnDate(BookConstants.RETURNED_DATE)
            .build();
    }

}
