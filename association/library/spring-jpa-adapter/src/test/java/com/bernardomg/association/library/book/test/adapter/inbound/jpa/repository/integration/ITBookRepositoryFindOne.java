
package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.domain.model.Book;
import com.bernardomg.association.library.book.domain.repository.BookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDateFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.DonationNoDonorsFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalFictionBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.Books;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookRepository - findOne")
class ITBookRepositoryFindOne {

    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("When there is a fiction book and it has a donation without date, then it is returned")
    @ValidProfile
    @DonationNoDateFictionBook
    void testFindOne_DonationNoDate() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a fiction book and it has a donation without date, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
                .isEqualTo(Books.donationNoDate()));
    }

    @Test
    @DisplayName("When there is a fiction book and it has a donation without donors, then it is returned")
    @DonationNoDonorsFictionBook
    void testFindOne_DonationNoDonors() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a fiction book and it has a donation without donors, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
                .isEqualTo(Books.donationNoDonors()));
    }

    @Test
    @DisplayName("When there is a full fiction book, then it is returned")
    @ValidProfile
    @FullFictionBook
    void testFindOne_Full() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a full fiction book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
                .isEqualTo(Books.full()));
    }

    @Test
    @DisplayName("When there is a lent fiction book, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @FullFictionBook
    @LentBookLending
    void testFindOne_FullLent() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a lent fiction book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
                .isEqualTo(Books.lent()));
    }

    @Test
    @DisplayName("When there is a lent fiction book and it has history, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @FullFictionBook
    @LentBookLendingHistory
    void testFindOne_Lent_History() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a lent fiction book and it has history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
                .isEqualTo(Books.lentHistory()));
    }

    @Test
    @DisplayName("When there is a minimal fiction book, then it is returned")
    @MinimalFictionBook
    void testFindOne_Minimal() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a minimal fiction book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
                .isEqualTo(Books.minimal()));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned fiction book, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @FullFictionBook
    @ReturnedBookLending
    void testFindOne_Returned() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned fiction book, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
                .isEqualTo(Books.returned()));
    }

    @Test
    @DisplayName("When there is a returned fiction book with history, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @FullFictionBook
    @ReturnedBookLendingHistory
    void testFindOne_Returned_History() {
        final Optional<Book> book;

        // WHEN
        book = repository.findOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("book")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a returned fiction book with history, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
                .isEqualTo(Books.returnedHistory()));
    }

}
