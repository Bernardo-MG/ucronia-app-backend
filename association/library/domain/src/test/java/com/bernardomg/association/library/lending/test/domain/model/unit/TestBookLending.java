
package com.bernardomg.association.library.lending.test.domain.model.unit;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.association.library.book.domain.model.Title;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.model.BookLending.LentBook;
import com.bernardomg.association.library.lending.domain.model.Borrower;
import com.bernardomg.association.library.lending.domain.model.BorrowerName;

@DisplayName("BookLending")
public class TestBookLending {

    public static final long   NUMBER     = 1L;

    public static final String SUBTITLE   = "Subtitle";

    public static final String SUPERTITLE = "Supertitle";

    public static final String TITLE      = "Title";

    @Test
    @DisplayName("When there is no return date, the days are calculated")
    void testDays_NoReturnDate() {
        final Borrower    borrower;
        final BookLending lending;
        final LentBook    book;
        final Title       title;

        // GIVEN
        title = new Title(SUPERTITLE, TITLE, SUBTITLE);
        book = new LentBook(NUMBER, title);
        borrower = new Borrower(0, new BorrowerName("", ""));
        lending = new BookLending(book, borrower, Instant.now()
            .minus(1L, ChronoUnit.DAYS));

        // WHEN + THEN
        Assertions.assertThat(lending.getDays())
            .isEqualTo(2L);
    }

    @Test
    @DisplayName("When there is no return date and was lent today, the days are calculated")
    void testDays_NoReturnDate_SameDay() {
        final Borrower    borrower;
        final BookLending lending;
        final LentBook    book;
        final Title       title;

        // GIVEN
        title = new Title(SUPERTITLE, TITLE, SUBTITLE);
        book = new LentBook(NUMBER, title);
        borrower = new Borrower(0, new BorrowerName("", ""));
        lending = new BookLending(book, borrower, Instant.now());

        // WHEN + THEN
        Assertions.assertThat(lending.getDays())
            .isEqualTo(1L);
    }

    @Test
    @DisplayName("When the return date is the lending date, the days are calculated")
    void testDays_ReturnedSameDay() {
        final Borrower    borrower;
        final BookLending lending;
        final LentBook    book;
        final Title       title;

        // GIVEN
        title = new Title(SUPERTITLE, TITLE, SUBTITLE);
        book = new LentBook(NUMBER, title);
        borrower = new Borrower(0, new BorrowerName("", ""));
        lending = new BookLending(book, borrower, Instant.now(), Instant.now());

        // WHEN + THEN
        Assertions.assertThat(lending.getDays())
            .isEqualTo(1L);
    }

    @Test
    @DisplayName("When there is a return date, the days are calculated")
    void testDays_WithReturn() {
        final Borrower    borrower;
        final BookLending lending;
        final LentBook    book;
        final Title       title;

        // GIVEN
        title = new Title(SUPERTITLE, TITLE, SUBTITLE);
        book = new LentBook(NUMBER, title);
        borrower = new Borrower(0, new BorrowerName("", ""));
        lending = new BookLending(book, borrower, Instant.now()
            .minus(1L, ChronoUnit.DAYS),
            Instant.now()
                .plus(1L, ChronoUnit.DAYS));

        // WHEN + THEN
        Assertions.assertThat(lending.getDays())
            .isEqualTo(3L);
    }

}
