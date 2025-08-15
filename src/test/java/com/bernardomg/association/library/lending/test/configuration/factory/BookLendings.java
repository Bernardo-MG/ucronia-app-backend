
package com.bernardomg.association.library.lending.test.configuration.factory;

import java.time.Instant;

import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.Borrowers;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;

public final class BookLendings {

    public static final BookLending lent() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), BookConstants.LENT_DATE, null);
    }

    public static final BookLending lent(final Instant lent) {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), lent, null);
    }

    public static final BookLending lentAlternativePerson() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.alternative(), BookConstants.LENT_DATE, null);
    }

    public static final BookLending lentAtReturn() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), BookConstants.RETURNED_DATE, null);
    }

    public static final BookLending lentLast() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), BookConstants.LENT_DATE_LAST, null);
    }

    public static final BookLending lentToday() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), BookConstants.LENT_DATE_TODAY, null);
    }

    public static final BookLending returned() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), BookConstants.LENT_DATE, BookConstants.RETURNED_DATE);
    }

    public static final BookLending returned(final Instant lent, final Instant returned) {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), lent, returned);
    }

    public static final BookLending returnedAlternative() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.alternative(), BookConstants.LENT_DATE, BookConstants.RETURNED_DATE);
    }

    public static final BookLending returnedAlternative(final Instant lent, final Instant returned) {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.alternative(), lent, returned);
    }

    public static final BookLending returnedLast() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), BookConstants.LENT_DATE_LAST, BookConstants.RETURNED_DATE_LAST);
    }

    public static final BookLending returnedWhenLent() {
        final LentBook book;
        final Title    title;

        title = new Title(BookConstants.SUPERTITLE, BookConstants.TITLE, BookConstants.SUBTITLE);
        book = new LentBook(BookConstants.NUMBER, title);
        return new BookLending(book, Borrowers.valid(), BookConstants.LENT_DATE, BookConstants.LENT_DATE);
    }

}
