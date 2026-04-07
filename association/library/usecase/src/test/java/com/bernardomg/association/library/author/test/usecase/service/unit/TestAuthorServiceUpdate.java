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

package com.bernardomg.association.library.author.test.usecase.service.unit;

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

import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.author.usecase.service.DefaultAuthorService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorService - update")
class TestAuthorServiceUpdate {

    @Mock
    private AuthorRepository     authorRepository;

    @InjectMocks
    private DefaultAuthorService service;

    public TestAuthorServiceUpdate() {
        super();
    }

    @Test
    @DisplayName("With an author with an empty name, an exception is thrown")
    void testUpdate_EmptyName() {
        final ThrowingCallable execution;
        final Author           author;

        // GIVEN
        author = Authors.emptyName();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.update(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("With an author with a name existing for another user, an exception is thrown")
    void testUpdate_ExistsForAnother() {
        final Author           author;
        final ThrowingCallable execution;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(authorRepository.existsByNameForAnother(AuthorConstants.NAME, AuthorConstants.NUMBER)).willReturn(true);

        // WHEN
        execution = () -> service.update(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution,
            new FieldFailure("existing", "name", AuthorConstants.NAME));
    }

    @Test
    @DisplayName("With a not existing author, an exception is thrown")
    void testUpdate_NotExisting() {
        final Author           author;
        final ThrowingCallable execution;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(author);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("With a valid author, the author is persisted")
    void testUpdate_PersistedData() {
        final Author author;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(author);

        // THEN
        verify(authorRepository).save(Authors.valid());
    }

    @Test
    @DisplayName("With a valid author, the created author is returned")
    void testUpdate_ReturnedData() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);

        given(authorRepository.save(Authors.valid())).willReturn(Authors.valid());

        // WHEN
        created = service.update(author);

        // THEN
        Assertions.assertThat(created)
            .isEqualTo(Authors.valid());
    }

}
