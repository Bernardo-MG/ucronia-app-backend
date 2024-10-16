
package com.bernardomg.association.library.lending.test.configuration.factory;

import java.time.LocalDate;

import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.person.test.configuration.factory.Persons;

public final class BookLendings {

    public static final BookLending lent() {
        return new BookLending(BookConstants.NUMBER, Persons.noMembership(), BookConstants.LENT_DATE, null);
    }

    public static final BookLending lentAlternativePerson() {
        return new BookLending(BookConstants.NUMBER, Persons.alternative(), BookConstants.LENT_DATE, null);
    }

    public static final BookLending lentAtReturn() {
        return new BookLending(BookConstants.NUMBER, Persons.noMembership(), BookConstants.RETURNED_DATE, null);
    }

    public static final BookLending lentLast() {
        return new BookLending(BookConstants.NUMBER, Persons.noMembership(), BookConstants.LENT_DATE_LAST, null);
    }

    public static final BookLending lentToday() {
        return new BookLending(BookConstants.NUMBER, Persons.noMembership(), LocalDate.now(), null);
    }

    public static final BookLending returned() {
        return new BookLending(BookConstants.NUMBER, Persons.noMembership(), BookConstants.LENT_DATE,
            BookConstants.RETURNED_DATE);
    }

    public static final BookLending returnedAlternative() {
        return new BookLending(BookConstants.NUMBER, Persons.alternative(), BookConstants.LENT_DATE,
            BookConstants.RETURNED_DATE);
    }

    public static final BookLending returnedLast() {
        return new BookLending(BookConstants.NUMBER, Persons.noMembership(), BookConstants.LENT_DATE_LAST,
            BookConstants.RETURNED_DATE_LAST);
    }

}
