
package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDateGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDonorsGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameBookRepository - findOne")
class ITGameBookRepositoryFindOne {

    @Autowired
    private GameBookRepository repository;

    @Test
    @DisplayName("When there is a game book and it has a donation without date, then it is returned")
    @ValidProfile
    @DonationNoDateGameBook
    void testFindOne_DonationNoDate() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a game book and it has a donation without date, then it is returned")
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(AuditDetails.class)
                .isEqualTo(GameBooks.donationNoDate()));
    }

    @Test
    @DisplayName("When there is a game book and it has a donation without donors, then it is returned")
    @DonationNoDonorsGameBook
    void testFindOne_DonationNoDonors() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a game book and it has a donation without donors, then it is returned")
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(AuditDetails.class)
                .isEqualTo(GameBooks.donationNoDonors()));
    }

    @Test
    @DisplayName("When there is a full game book, then it is returned")
    @ValidProfile
    @FullGameBook
    void testFindOne_Full() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a full game book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(AuditDetails.class)
                .isEqualTo(GameBooks.full()));
    }

    @Test
    @DisplayName("When there is a lent game book, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @FullGameBook
    @LentBookLending
    void testFindOne_FullLent() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a lent game book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(AuditDetails.class)
                .isEqualTo(GameBooks.lent()));
    }

    @Test
    @DisplayName("When there is a lent game book and it has history, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @FullGameBook
    @LentBookLendingHistory
    void testFindOne_Lent_History() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a lent game book and it has history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(AuditDetails.class)
                .isEqualTo(GameBooks.lentHistory()));
    }

    @Test
    @DisplayName("When there is a minimal game book, then it is returned")
    @MinimalGameBook
    void testFindOne_Minimal() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a minimal game book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(GameBooks.minimal()));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned game book, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @FullGameBook
    @ReturnedBookLending
    void testFindOne_Returned() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned game book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(AuditDetails.class)
                .isEqualTo(GameBooks.returned()));
    }

    @Test
    @DisplayName("When there is a returned game book with history, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @FullGameBook
    @ReturnedBookLendingHistory
    void testFindOne_Returned_History() {
        final Optional<GameBook> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned game book with history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFieldsOfTypes(AuditDetails.class)
                .isEqualTo(GameBooks.returnedHistory()));
    }

}
