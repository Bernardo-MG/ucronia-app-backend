
package com.bernardomg.association.library.lending.test.configuration.factory;

import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.domain.model.BookBookLending;
import com.bernardomg.association.person.test.configuration.factory.Persons;

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
