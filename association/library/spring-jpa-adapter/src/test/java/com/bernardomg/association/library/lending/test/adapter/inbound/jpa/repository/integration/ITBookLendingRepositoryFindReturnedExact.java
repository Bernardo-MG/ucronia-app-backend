
package com.bernardomg.association.library.lending.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookLendingRepository - findReturned")
class ITBookLendingRepositoryFindReturnedExact {

    @Autowired
    private BookLendingRepository repository;

    @Test
    @DisplayName("When there is a lent fiction book, then nothing is returned")
    @ValidProfile
    @FullFictionBook
    @LentBookLending
    void testFindReturned_FictionBook_Lent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a fiction book and it has no history, then nothing is returned")
    @ValidProfile
    @FullFictionBook
    void testFindReturned_FictionBook_NoHistory() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a fiction book and it is not lent, then nothing is returned")
    @MinimalFictionBook
    void testFindReturned_FictionBook_NotLent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned fiction book, then it is returned")
    @ValidProfile
    @FullFictionBook
    @ReturnedBookLending
    void testFindReturned_FictionBook_Returned() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned fiction book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.returned()));
    }

    @Test
    @DisplayName("When there is a returned game book and it has history, then it is returned")
    @ValidProfile
    @AlternativeProfile
    @FullFictionBook
    @ReturnedBookLendingHistory
    void testFindReturned_FictionBook_Returned_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned game book and it has history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.returned()));
    }

    @Test
    @DisplayName("When there is a lent game book, then nothing is returned")
    @ValidProfile
    @FullGameBook
    @LentBookLending
    void testFindReturned_GameBook_Lent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a game book and it has no history, then nothing is returned")
    @ValidProfile
    @FullGameBook
    void testFindReturned_GameBook_NoHistory() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a game book and it is not lent, then nothing is returned")
    @MinimalGameBook
    void testFindReturned_GameBook_NotLent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned game book, then it is returned")
    @ValidProfile
    @FullGameBook
    @ReturnedBookLending
    void testFindReturned_GameBook_Returned() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned game book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.returned()));
    }

    @Test
    @DisplayName("When there is a returned game book and it has history, then it is returned")
    @ValidProfile
    @AlternativeProfile
    @FullGameBook
    @ReturnedBookLendingHistory
    void testFindReturned_GameBook_Returned_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned game book and it has history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.returned()));
    }

    @Test
    @DisplayName("When there is no book, then nothing is returned")
    @ValidProfile
    void testFindReturned_NoBook() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findReturned(BookConstants.NUMBER, BookConstants.BORROWER, BookConstants.LENT_DATE);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

}
