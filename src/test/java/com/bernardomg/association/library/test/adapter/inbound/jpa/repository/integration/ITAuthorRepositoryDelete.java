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

package com.bernardomg.association.library.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.bernardomg.association.library.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.domain.repository.AuthorRepository;
import com.bernardomg.association.library.test.config.data.annotation.FullBook;
import com.bernardomg.association.library.test.config.data.annotation.ValidAuthor;
import com.bernardomg.association.library.test.config.factory.AuthorConstants;
import com.bernardomg.association.person.test.config.data.annotation.ValidPerson;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("AuthorRepository - delete")
class ITAuthorRepositoryDelete {

    @Autowired
    private AuthorRepository       repository;

    @Autowired
    private AuthorSpringRepository springRepository;

    @Test
    @DisplayName("With an author, it is deleted")
    @ValidAuthor
    void testDelete() {
        // WHEN
        repository.delete(AuthorConstants.NAME);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("authors")
            .isZero();
    }

    @Test
    @DisplayName("When the author is assigned to a book, an exception is thrown")
    @ValidPerson
    @FullBook
    void testDelete_InBook() {
        final ThrowingCallable execution;

        // WHEN
        execution = () -> {
            repository.delete(AuthorConstants.NAME);
            springRepository.flush();
        };

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("With no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(AuthorConstants.NAME);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("authors")
            .isZero();
    }

}
