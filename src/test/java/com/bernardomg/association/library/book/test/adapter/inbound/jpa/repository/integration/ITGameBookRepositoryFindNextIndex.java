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

package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookRepository - find next number")
class ITGameBookRepositoryFindNextIndex {

    @Autowired
    private GameBookRepository repository;

    @Test
    @DisplayName("When there is a fiction book, the next number is correct")
    @NoMembershipPerson
    @FullFictionBook
    void testFindNextNumber_FictionBook() {
        final long index;

        // WHEN
        index = repository.findNextNumber();

        // THEN
        Assertions.assertThat(index)
            .as("index")
            .isEqualTo(BookConstants.NEXT_NUMBER);
    }

    @Test
    @DisplayName("When there is a game book, the next number is correct")
    @NoMembershipPerson
    @FullGameBook
    void testFindNextNumber_GameBook() {
        final long index;

        // WHEN
        index = repository.findNextNumber();

        // THEN
        Assertions.assertThat(index)
            .as("index")
            .isEqualTo(BookConstants.NEXT_NUMBER);
    }

    @Test
    @DisplayName("When there is no data, the next number is correct")
    void testFindNextNumber_NoData() {
        final long index;

        // WHEN
        index = repository.findNextNumber();

        // THEN
        Assertions.assertThat(index)
            .as("index")
            .isEqualTo(1);
    }

}
