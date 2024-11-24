
package com.bernardomg.association.library.lending.test.configuration.factory;

import java.time.LocalDate;

import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.Borrowers;
import com.bernardomg.association.library.lending.domain.model.BookLending;

public final class BookLendings {

    public static final BookLending lent() {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), BookConstants.LENT_DATE, null);
    }

    public static final BookLending lentAlternativePerson() {
        return new BookLending(BookConstants.NUMBER, Borrowers.alternative(), BookConstants.LENT_DATE, null);
    }

    public static final BookLending lentAtReturn() {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), BookConstants.RETURNED_DATE, null);
    }

    public static final BookLending lentLast() {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), BookConstants.LENT_DATE_LAST, null);
    }

    public static final BookLending lentToday() {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), LocalDate.now(), null);
    }

    public static final BookLending returned(final LocalDate lent, final LocalDate returned) {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), lent,
            returned);
    }

    public static final BookLending returnedAlternative(final LocalDate lent, final LocalDate returned) {
        return new BookLending(BookConstants.NUMBER, Borrowers.alternative(), lent,
            returned);
    }

    public static final BookLending lent(final LocalDate lent) {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), lent,
            null);
    }

    public static final BookLending returned() {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), BookConstants.LENT_DATE,
            BookConstants.RETURNED_DATE);
    }

    public static final BookLending returnedAlternative() {
        return new BookLending(BookConstants.NUMBER, Borrowers.alternative(), BookConstants.LENT_DATE,
            BookConstants.RETURNED_DATE);
    }

    public static final BookLending returnedLast() {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), BookConstants.LENT_DATE_LAST,
            BookConstants.RETURNED_DATE_LAST);
    }

    public static final BookLending returnedWhenLent() {
        return new BookLending(BookConstants.NUMBER, Borrowers.valid(), BookConstants.LENT_DATE,
            BookConstants.LENT_DATE);
    }

}
