
package com.bernardomg.association.library.booktype.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookTypeRepository - findAll")
class ITBookTypeRepositoryFindAll {

    @Autowired
    private BookTypeRepository repository;

    @Test
    @DisplayName("When there are book types, then they are returned")
    @ValidBookType
    void testFindAll() {
        final Page<BookType> bookTypes;
        final Pagination     pagination;
        final Sorting        sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        bookTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(bookTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("book types")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(BookTypes.valid());
    }

    @Test
    @DisplayName("When there are no book types, then nothing is returned")
    void testFindAll_NoData() {
        final Page<BookType> bookTypes;
        final Pagination     pagination;
        final Sorting        sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        bookTypes = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(bookTypes)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("book types")
            .isEmpty();
    }

}
