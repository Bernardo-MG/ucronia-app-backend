
package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLendingHistory;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLendingHistory;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameBookRepository - findAll")
class ITGameBookRepositoryFindAll {

    @Autowired
    private GameBookRepository repository;

    @Test
    @DisplayName("When there is a game book, then it is returned")
    @ValidProfile
    @FullGameBook
    void testFindAll() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditDetails.class)
                .build())
            .containsExactly(GameBooks.full());
    }

    @Test
    @DisplayName("When there is a lent game book, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @FullGameBook
    @LentBookLending
    void testFindAll_Lent() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditDetails.class)
                .build())
            .containsExactly(GameBooks.lent());
    }

    @Test
    @DisplayName("When there is a lent game book and it has history, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @FullGameBook
    @LentBookLendingHistory
    void testFindAll_Lent_WithHistory() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditDetails.class)
                .build())
            .containsExactly(GameBooks.lentHistory());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindAll_NoData() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned game book, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @FullGameBook
    @ReturnedBookLending
    void testFindAll_Returned() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditDetails.class)
                .build())
            .containsExactly(GameBooks.returned());
    }

    @Test
    @DisplayName("When there is a returned game book and it has history, then it is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @FullGameBook
    @ReturnedBookLendingHistory
    void testFindAll_Returned_WithHistory() {
        final Iterable<GameBook> books;
        final Sorting            sorting;

        // GIVEN
        sorting = Sorting.unsorted();

        // WHEN
        books = repository.findAll(sorting);

        // THEN
        Assertions.assertThat(books)
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditDetails.class)
                .build())
            .containsExactly(GameBooks.returnedHistory());
    }

}
