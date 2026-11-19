
package com.bernardomg.association.library.lending.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendingEntities;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookLendingRepository - save")
class ITBookLendingRepositorySave {

    @Autowired
    private BookLendingRepository       repository;

    @Autowired
    private BookLendingSpringRepository springRepository;

    @Test
    @DisplayName("When saving a lending for a fiction book and the profile doesnt exist, then nothing is persisted")
    @MinimalFictionBook
    void testSave_FictionBook_NoMember() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("lendings")
            .isZero();
    }

    @Test
    @DisplayName("When saving a lending for a fiction book, then a lending is persisted")
    @ValidProfile
    @FullFictionBook
    void testSave_FictionBook_Persisted() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("borrower", "audit")
                .build())
            .contains(BookLendingEntities.lent());
    }

    @Test
    @DisplayName("When changing a fiction bool from lent to returned, then it is persisted")
    @ValidProfile
    @FullFictionBook
    @LentBookLending
    void testSave_FictionBook_Return() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.returned();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("borrower", "audit")
                .build())
            .contains(BookLendingEntities.returned());
    }

    @Test
    @DisplayName("When saving a lending for a fiction book, then the persisted lending is returned")
    @ValidProfile
    @FullFictionBook
    void testSave_FictionBook_Returned() {
        final BookLending lending;
        final BookLending created;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        created = repository.save(lending);

        // THEN
        Assertions.assertThat(created)
            .as("lending")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(BookLendings.lent());
    }

    @Test
    @DisplayName("When saving a lending for a game book and the profile doesnt exist, then nothing is persisted")
    @MinimalGameBook
    void testSave_GameBook_NoMember() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("lendings")
            .isZero();
    }

    @Test
    @DisplayName("When saving a lending for a game book, then a lending is persisted")
    @ValidProfile
    @FullGameBook
    void testSave_GameBook_Persisted() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("borrower", "audit")
                .build())
            .contains(BookLendingEntities.lent());
    }

    @Test
    @DisplayName("When changing a fiction bool from lent to returned, then it is persisted")
    @ValidProfile
    @FullGameBook
    @LentBookLending
    void testSave_GameBook_Return() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.returned();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("borrower", "audit")
                .build())
            .contains(BookLendingEntities.returned());
    }

    @Test
    @DisplayName("When saving a lending for a game book, then the persisted lending is returned")
    @ValidProfile
    @FullGameBook
    void testSave_GameBook_Returned() {
        final BookLending lending;
        final BookLending created;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        created = repository.save(lending);

        // THEN
        Assertions.assertThat(created)
            .as("lending")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(BookLendings.lent());
    }

    @Test
    @DisplayName("When saving a lending for a book which doesn't exist, then nothing is persisted")
    @ValidProfile
    void testSave_NoBook() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("lendings")
            .isZero();
    }

}
