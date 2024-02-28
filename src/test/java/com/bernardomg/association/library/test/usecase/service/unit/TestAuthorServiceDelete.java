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

package com.bernardomg.association.library.test.usecase.service.unit;

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

import com.bernardomg.association.library.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.test.config.factory.AuthorConstants;
import com.bernardomg.association.library.usecase.service.DefaultAuthorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorService - delete")
class TestAuthorServiceDelete {

    @Mock
    private AuthorRepository     authorRepository;

    @InjectMocks
    private DefaultAuthorService service;

    public TestAuthorServiceDelete() {
        super();
    }

    @Test
    @DisplayName("When deleting an author, the repository is called")
    void testDeleteAuthor_CallsRepository() {
        // GIVEN
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(true);

        // WHEN
        service.delete(AuthorConstants.NAME);

        // THEN
        verify(authorRepository).delete(AuthorConstants.NAME);
    }

    @Test
    @DisplayName("When the author doesn't exist, an exception is thrown")
    void testDeleteAuthor_NotExisting_NotRemovesEntity() {
        final ThrowingCallable execution;

        // GIVEN
        given(authorRepository.exists(AuthorConstants.NAME)).willReturn(false);

        // WHEN
        execution = () -> service.delete(AuthorConstants.NAME);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

}
