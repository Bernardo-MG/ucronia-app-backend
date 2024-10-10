/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
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

package com.bernardomg.association.library.booktype.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.booktype.domain.exception.MissingBookTypeException;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.association.library.booktype.usecase.service.DefaultBookTypeService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookTypeRepository - update")
class TestBookTypeServiceUpdate {

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @InjectMocks
    private DefaultBookTypeService service;

    public TestBookTypeServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With a book type with an empty name, an exception is thrown")
    void testUpdate_EmptyName() {
        final ThrowingCallable execution;
        final BookType         bookType;

        // GIVEN
        bookType = BookTypes.emptyName();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.update(bookType);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, FieldFailure.of("name", "empty", " "));
    }

    @Test
    @DisplayName("With a not existing book type, an exception is thrown")
    void testUpdate_NotExisting() {
        final BookType         bookType;
        final ThrowingCallable execution;

        // GIVEN
        bookType = BookTypes.valid();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(bookType);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookTypeException.class);
    }

    @Test
    @DisplayName("With a valid book type, the book type is persisted")
    void testUpdate_PersistedData() {
        final BookType bookType;

        // GIVEN
        bookType = BookTypes.valid();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(bookType);

        // THEN
        verify(bookTypeRepository).save(BookTypes.valid());
    }

    @Test
    @DisplayName("With a valid book type, the created book type is returned")
    void testUpdate_ReturnedData() {
        final BookType bookType;
        final BookType created;

        // GIVEN
        bookType = BookTypes.valid();

        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);

        given(bookTypeRepository.save(BookTypes.valid())).willReturn(BookTypes.valid());

        // WHEN
        created = service.update(bookType);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(BookTypes.valid());
    }

}
