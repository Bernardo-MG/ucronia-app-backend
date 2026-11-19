
package com.bernardomg.association.library.publisher.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.association.library.publisher.usecase.service.DefaultPublisherService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultPublisherService - getAll")
class TestPublisherServiceGetAll {

    @Mock
    private PublisherRepository     publisherRepository;

    @InjectMocks
    private DefaultPublisherService service;

    @Test
    @DisplayName("When there are publishers, then they are returned")
    void testGetAll() {
        final Pagination      pagination;
        final Sorting         sorting;
        final Page<Publisher> publishers;
        final Page<Publisher> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(Publishers.valid()), 0, 0, 0, 0, 0, false, false, sorting);
        given(publisherRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        publishers = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(publishers)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("authors")
            .containsExactly(Publishers.valid());
    }

    @Test
    @DisplayName("When there are no publishers, then nothing is returned")
    void testGetAll_NoData() {
        final Pagination      pagination;
        final Sorting         sorting;
        final Page<Publisher> publishers;
        final Page<Publisher> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(publisherRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        publishers = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(publishers)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("authors")
            .isEmpty();
    }

}
