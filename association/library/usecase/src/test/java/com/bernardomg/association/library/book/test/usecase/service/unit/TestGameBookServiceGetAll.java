
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
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.DonorRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookFilters;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultGameBookService;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameBookService - getAll")
class TestGameBookServiceGetAll {

    @Mock
    private AuthorRepository       authorRepository;

    @Mock
    private GameBookRepository     bookRepository;

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @Mock
    private DonorRepository        donorRepository;

    @Mock
    private GameSystemRepository   gameSystemRepository;

    @Mock
    private PublisherRepository    publisherRepository;

    @InjectMocks
    private DefaultGameBookService service;

    @Test
    @DisplayName("When there are books, then they are returned")
    void testGetAll() {
        final Pagination     pagination;
        final Sorting        sorting;
        final Page<GameBook> books;
        final Page<GameBook> existing;
        final BookFilter     filter;

        // GIVEN
        pagination = new Pagination(1, 20);
        sorting = Sorting.unsorted();
        filter = BookFilters.all();

        existing = new Page<>(List.of(GameBooks.full()), 0, 0, 0, 0, 0, false, false, sorting);
        given(bookRepository.findAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        books = service.getAll(filter, pagination, sorting);

        // THEN
        Assertions.assertThat(books)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("books")
            .containsExactly(GameBooks.full());
    }

    @Test
    @DisplayName("When there are no books, then nothing is returned")
    void testGetAll_NoData() {
        final Pagination     pagination;
        final Sorting        sorting;
        final Page<GameBook> books;
        final Page<GameBook> existing;
        final BookFilter     filter;

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
