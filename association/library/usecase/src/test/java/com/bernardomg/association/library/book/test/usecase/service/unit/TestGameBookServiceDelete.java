
package com.bernardomg.association.library.book.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.association.library.book.domain.repository.DonorRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultGameBookService;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultGameBookService - delete")
class TestGameBookServiceDelete {

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
    @DisplayName("When deleting a book, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(GameBooks.full()));

        // WHEN
        service.delete(BookConstants.NUMBER);

        // THEN
        verify(bookRepository).delete(BookConstants.NUMBER);
    }

    @Test
    @DisplayName("When the book doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(BookConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the book doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingBookException.class);
    }

}
