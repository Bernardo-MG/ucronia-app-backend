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
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.library.booktype.usecase.service.DefaultBookTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookTypeService - delete")
class TestBookTypeServiceDelete {

    @Mock
    private BookTypeRepository     bookTypeRepository;

    @InjectMocks
    private DefaultBookTypeService service;

    public TestBookTypeServiceDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting a book type, the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(true);

        // WHEN
        service.delete(BookTypeConstants.NUMBER);

        // THEN
        verify(bookTypeRepository).delete(BookTypeConstants.NUMBER);
    }

    @Test
    @DisplayName("When the book type doesn't exist, an exception is thrown")
    void testDelete_NotExisting_Exception() {
        final ThrowingCallable execution;

        // GIVEN
        given(bookTypeRepository.exists(BookTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.delete(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingBookTypeException.class);
    }

}
