
package com.bernardomg.association.library.lending.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
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
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.ReturnedBookLending;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

/**
 * TODO: test with fiction books.
 */
@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookLendingRepository - findAll")
class ITBookLendingRepositoryFindAll {

    @Autowired
    private BookLendingRepository repository;

    @Test
    @DisplayName("When there is a lent fiction book, then it is returned")
    @ValidProfile
    @FullFictionBook
    @LentBookLending
    void testFindAll_FictionBook_Lent() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("audit")
                .build())
            .containsExactly(BookLendings.lent());
    }

    @Test
    @DisplayName("When there is a fiction book and it is not lent, then nothing is returned")
    @MinimalFictionBook
    void testFindAll_FictionBook_NotLent() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned fiction book, then nothing is returned")
    @ValidProfile
    @FullFictionBook
    @ReturnedBookLending
    void testFindAll_FictionBook_Returned() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a lent game book, then it is returned")
    @ValidProfile
    @FullGameBook
    @LentBookLending
    void testFindAll_GameBook_Lent() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("audit")
                .build())
            .containsExactly(BookLendings.lent());
    }

    @Test
    @DisplayName("When there is a game book and it is not lent, then nothing is returned")
    @MinimalGameBook
    void testFindAll_GameBook_NotLent() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a returned game book, then nothing is returned")
    @ValidProfile
    @FullGameBook
    @ReturnedBookLending
    void testFindAll_GameBook_Returned() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindAll_NoData() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<BookLending> lendings;

        // WHEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        lendings = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(lendings)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("lendings")
            .isEmpty();
    }

}
