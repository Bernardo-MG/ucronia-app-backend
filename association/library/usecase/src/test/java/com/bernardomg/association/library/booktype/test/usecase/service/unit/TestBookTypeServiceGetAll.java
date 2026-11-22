
package com.bernardomg.association.library.booktype.test.usecase.service.unit;

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

import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.booktype.usecase.service.DefaultBookTypeService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultBookTypeService - getAll")
class TestBookTypeServiceGetAll {

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @InjectMocks
    private DefaultBookTypeService service;

    @Test
    @DisplayName("When there are book types, then they are returned")
    void testGetAll() {
        final Pagination     pagination;
        final Sorting        sorting;
        final Page<BookType> types;
        final Page<BookType> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(BookTypes.valid()), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookTypeRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        types = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(types)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("book types")
            .containsExactly(BookTypes.valid());
    }

    @Test
    @DisplayName("When there are no book types, then nothing is returned")
    void testGetAll_NoData() {
        final Pagination     pagination;
        final Sorting        sorting;
        final Page<BookType> types;
        final Page<BookType> existing;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookTypeRepository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        types = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(types)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("book types")
            .isEmpty();
    }

}
