/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

import com.bernardomg.association.contact.domain.repository.ContactRepository;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.book.domain.exception.MissingBookException;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.DefaultGameBookService;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.gamesystem.domain.repository.GameSystemRepository;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService - delete")
class TestGameBookServiceDelete {

    @Mock
    private AuthorRepository       authorRepository;

    @Mock
    private GameBookRepository     bookRepository;

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @Mock
    private ContactRepository      contactRepository;

    @Mock
    private GameSystemRepository   gameSystemRepository;

    @Mock
    private PublisherRepository    publisherRepository;

    @InjectMocks
    private DefaultGameBookService service;

    public TestGameBookServiceDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting a book, the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.of(GameBooks.full()));

        // WHEN
        service.delete(BookConstants.NUMBER);

        // THEN
        verify(bookRepository).delete(BookConstants.NUMBER);
    }

    @Test
    @DisplayName("When the book doesn't exist, an exception is thrown")
    void testDelete_NotExisting_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        given(bookRepository.findOne(BookConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(BookConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookException.class);
    }

}
