
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
import com.bernardomg.association.library.book.test.configuration.factory.DonorConstants;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookLendingRepository - findOne")
class ITBookLendingRepositoryFindOne {

    @Autowired
    private BookLendingRepository repository;

    @Test
    @DisplayName("When there is a lent fiction book, then it is lent")
    @ValidProfile
    @FullFictionBook
    @LentBookLending
    void testFindOne_FictionBook_Lent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a lent fiction book, then it is lent")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.lent()));
    }

    @Test
    @DisplayName("When there is a lent game book and it has history, then it is returned")
    @ValidProfile
    @AlternativeProfile
    @FullFictionBook
    @LentBookLendingHistory
    void testFindOne_FictionBook_Lent_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a lent game book and it has history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.lentLast()));
    }

    @Test
    @DisplayName("When there is a fiction book and it has no history, then nothing is returned")
    @ValidProfile
    @FullFictionBook
    void testFindOne_FictionBook_NoHistory() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a fiction book and it is not lent, then nothing is returned")
    @MinimalFictionBook
    void testFindOne_FictionBook_NotLent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

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
    void testFindOne_FictionBook_Returned() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

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
    @DisplayName("When there is a returned fiction book and it has history, then it is returned")
    @ValidProfile
    @AlternativeProfile
    @FullFictionBook
    @ReturnedBookLendingHistory
    void testFindOne_FictionBook_Returned_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned fiction book and it has history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.returnedLast()));
    }

    @Test
    @DisplayName("When there is a lent game book, then it is returned")
    @ValidProfile
    @FullGameBook
    @LentBookLending
    void testFindOne_GameBook_Lent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a lent game book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.lent()));
    }

    @Test
    @DisplayName("When there is a lent game book and it has history, then it is returned")
    @ValidProfile
    @AlternativeProfile
    @FullGameBook
    @LentBookLendingHistory
    void testFindOne_GameBook_Lent_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a lent game book and it has history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.lentLast()));
    }

    @Test
    @DisplayName("When there is a game book and it has no history, then nothing is returned")
    @ValidProfile
    @FullGameBook
    void testFindOne_GameBook_NoHistory() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a game book and it is not lent, then nothing is returned")
    @MinimalGameBook
    void testFindOne_GameBook_NotLent() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

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
    void testFindOne_GameBook_Returned() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

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
    void testFindOne_GameBook_Returned_History() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned game book and it has history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookLendings.returnedLast()));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    @ValidProfile
    void testFindOne_NoBook() {
        final Optional<BookLending> lending;

        // WHEN
        lending = repository.findOne(BookConstants.NUMBER, DonorConstants.NUMBER);

        // THEN
        Assertions.assertThat(lending)
            .as("lending")
            .isEmpty();
    }

}
