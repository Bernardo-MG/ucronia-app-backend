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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.domain.model.Author;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.test.config.factory.Authors;
import com.bernardomg.association.library.usecase.service.DefaultAuthorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorService - create")
class TestAuthorServiceCreate {

    @Mock
    private AuthorRepository     authorRepository;

    @InjectMocks
    private DefaultAuthorService service;

    public TestAuthorServiceCreate() {
        super();
    }

    @Test
    @DisplayName("With a valid author, the author is persisted")
    void testCreateAuthor_PersistedData() {
        final Author author;

        // GIVEN
        author = Authors.valid();

        // WHEN
        service.create(author);

        // THEN
        verify(authorRepository).save(Authors.valid());
    }

    @Test
    @DisplayName("With a valid author, the created author is returned")
    void testCreateAuthor_ReturnedData() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.save(Authors.valid())).willReturn(Authors.valid());

        // WHEN
        created = service.create(author);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Authors.valid());
    }

}