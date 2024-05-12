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

import com.bernardomg.association.library.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.test.config.data.annotation.FullBook;
import com.bernardomg.association.library.test.config.data.annotation.ValidBookType;
import com.bernardomg.association.library.test.config.factory.BookTypeConstants;
import com.bernardomg.association.member.test.config.data.annotation.ValidPerson;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookTypeRepository - delete")
class ITBookTypeRepositoryDelete {

    @Autowired
    private BookTypeRepository       repository;

    @Autowired
    private BookTypeSpringRepository springRepository;

    @Test
    @DisplayName("With a book type, it is deleted")
    @ValidBookType
    void testDelete() {
        // WHEN
        repository.delete(BookTypeConstants.NAME);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("book types")
            .isZero();
    }

    @Test
    @DisplayName("When the book type is assigned to a book, an exception is thrown")
    @ValidPerson
    @FullBook
    void testDelete_InBook() {
        final ThrowingCallable execution;

        // WHEN
        execution = () -> {
            repository.delete(BookTypeConstants.NAME);
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
        repository.delete(BookTypeConstants.NAME);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("book types")
            .isZero();
    }

}
