
package com.bernardomg.association.library.author.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AuthorRepository - findAll")
class ITAuthorRepositoryFindAll {

    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("When there are authors, then they are returned")
    @ValidAuthor
    void testFindAll() {
        final Page<Author> authors;
        final Pagination   pagination;
        final Sorting      sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        authors = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(authors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("authors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Authors.valid());
    }

    @Test
    @DisplayName("When there are no authors, then nothing is returned")
    void testFindAll_NoData() {
        final Page<Author> authors;
        final Pagination   pagination;
        final Sorting      sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        authors = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(authors)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("authors")
            .isEmpty();
    }

}
