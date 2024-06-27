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

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.domain.model.BookLending;
import com.bernardomg.association.library.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.test.config.data.annotation.FullBook;
import com.bernardomg.association.library.test.config.data.annotation.LentBookLending;
import com.bernardomg.association.library.test.config.data.annotation.MinimalBook;
import com.bernardomg.association.library.test.config.factory.BookConstants;
import com.bernardomg.association.library.test.config.factory.BookLendings;
import com.bernardomg.association.person.test.config.data.annotation.ValidPerson;
import com.bernardomg.association.person.test.config.factory.PersonConstants;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookLendingRepository - find one")
class ITBookLendingRepositoryFindOne {

    @Autowired
    private BookLendingRepository repository;

    @Test
    @DisplayName("With a lending, it is returned")
    @ValidPerson
    @FullBook
    @LentBookLending
    void testFindOne() {
        final Optional<BookLending> lendings;

        // WHEN
        lendings = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lendings)
            .as("lendings")
            .contains(BookLendings.lent());
    }

    @Test
    @DisplayName("With no person, nothing is returned")
    @ValidPerson
    void testFindOne_NoBook() {
        final Optional<BookLending> lendings;

        // WHEN
        lendings = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lendings)
            .as("lendings")
            .isEmpty();
    }

    @Test
    @DisplayName("With no person, nothing is returned")
    @MinimalBook
    void testFindOne_NoPerson() {
        final Optional<BookLending> lendings;

        // WHEN
        lendings = repository.findOne(BookConstants.NUMBER, PersonConstants.NUMBER);

        // THEN
        Assertions.assertThat(lendings)
            .as("lendings")
            .isEmpty();
    }

}
