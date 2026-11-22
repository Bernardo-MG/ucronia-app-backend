
package com.bernardomg.association.library.publisher.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublisherRepository - findAll")
class ITPublisherRepositoryFindAll {

    @Autowired
    private PublisherRepository repository;

    @Test
    @DisplayName("When there are publishers, then they are returned")
    @ValidPublisher
    void testFindAll() {
        final Page<Publisher> publishers;
        final Pagination      pagination;
        final Sorting         sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        publishers = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(publishers)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("publishers")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("audit")
            .containsExactly(Publishers.valid());
    }

    @Test
    @DisplayName("When there are no publishers, then nothing is returned")
    void testFindAll_NoData() {
        final Page<Publisher> publishers;
        final Pagination      pagination;
        final Sorting         sorting;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        // WHEN
        publishers = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(publishers)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("publishers")
            .isEmpty();
    }

}
