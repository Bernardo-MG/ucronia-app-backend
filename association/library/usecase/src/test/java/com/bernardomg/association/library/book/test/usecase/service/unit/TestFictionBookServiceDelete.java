
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
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultFictionBookService;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultFictionBookService - delete")
class TestFictionBookServiceDelete {

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
    @DisplayName("When deleting a book, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(FictionBooks.full()));

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
