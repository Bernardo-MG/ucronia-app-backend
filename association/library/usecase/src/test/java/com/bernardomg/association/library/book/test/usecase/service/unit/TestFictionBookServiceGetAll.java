
package com.bernardomg.association.library.book.test.usecase.service.unit;

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

import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.book.domain.filter.BookFilter;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.DonorRepository;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookFilters;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultFictionBookService;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFictionBookService - getAll")
class TestFictionBookServiceGetAll {

    @Mock
    private AuthorRepository          authorRepository;

    @Mock
    private FictionBookRepository     bookRepository;

    @Mock
    private DonorRepository           donorRepository;

    @Mock
    private PublisherRepository       publisherRepository;

    @InjectMocks
    private DefaultFictionBookService service;

    @Test
    @DisplayName("When there are books, then they are returned")
    void testGetAll() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<FictionBook> books;
        final Page<FictionBook> existing;
        final BookFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.all();

        existing = new Page<>(List.of(FictionBooks.full()), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        books = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .containsExactly(FictionBooks.full());
    }

    @Test
    @DisplayName("When there are no books, then nothing is returned")
    void testGetAll_NoData() {
        final Pagination        pagination;
        final Sorting           sorting;
        final Page<FictionBook> books;
        final Page<FictionBook> existing;
        final BookFilter        filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.all();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        books = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .isEmpty();
    }

}
