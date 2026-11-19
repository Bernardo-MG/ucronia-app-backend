
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.DonorRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultGameBookService;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameBookService - getOne")
class TestGameBookServiceGetOne {

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
    @DisplayName("When there is a book, then it is returned")
    void testGetOne() {
        final Optional<GameBook> book;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(GameBooks.full()));

        // WHEN
        book = service.getOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(book)
            .as("When there is a book, then it is returned")
            .contains(GameBooks.full());
    }

    @Test
    @DisplayName("When there are no books, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(BookConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there are no books, then an exception is thrown")
            .isInstanceOf(MissingBookException.class);
    }

}
