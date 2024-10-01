
package com.bernardomg.association.library.lending.test.configuration.factory;

import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.domain.model.BookBookLending;
import com.bernardomg.association.person.test.configuration.factory.Persons;

public final class BookBookLendings {

    public static final BookBookLending lent() {
        return new BookBookLending(Persons.valid(), BookConstants.LENT_DATE, null);
    }

    public static final BookBookLending returned() {
        return new BookBookLending(Persons.valid(), BookConstants.LENT_DATE, BookConstants.RETURNED_DATE);
    }

}
