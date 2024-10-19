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

package com.bernardomg.association.library.lending.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.book.test.configuration.data.annotation.FullBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.data.annotation.LentBookLending;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendingEntities;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.association.person.test.configuration.factory.PersonConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookLendingRepository - save")
class ITBookLendingRepositoryReturnAt {

    @Autowired
    private BookLendingRepository       repository;

    @Autowired
    private BookLendingSpringRepository springRepository;

    @Test
    @DisplayName("When returning a book and the book doesnt exist, nothing is persisted")
    @NoMembershipPerson
    void testReturnAt_NoBook() {
        // WHEN
        repository.returnAt(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.RETURNED_DATE);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("lendings")
            .isZero();
    }

    @Test
    @DisplayName("When returning a book and the lending doesn't exist, nothing is persisted")
    @NoMembershipPerson
    @FullBook
    void testReturnAt_NoLending_NotPersisted() {
        // WHEN
        repository.returnAt(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.RETURNED_DATE);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("lendings")
            .isEmpty();
    }

    @Test
    @DisplayName("When returning a book and the lending doesn't exist, nothing is returned")
    @NoMembershipPerson
    @FullBook
    void testReturnAt_NoLending_Returned() {
        final Optional<BookLending> created;

        // WHEN
        created = repository.returnAt(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.RETURNED_DATE);

        // THEN
        Assertions.assertThat(created)
            .as("lending")
            .isEmpty();
    }

    @Test
    @DisplayName("When returning a book and the person doesnt exist, nothing is persisted")
    @MinimalBook
    void testReturnAt_NoMember() {
        // WHEN
        repository.returnAt(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.RETURNED_DATE);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("lendings")
            .isZero();
    }

    @Test
    @DisplayName("When returning a book and the book and person exist, a lending is persisted")
    @NoMembershipPerson
    @FullBook
    @LentBookLending
    void testReturnAt_Persisted() {
        // WHEN
        repository.returnAt(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.RETURNED_DATE);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .contains(BookLendingEntities.returned());
    }

    @Test
    @DisplayName("When returning a book and the book and person exist, the persisted lending is returned")
    @NoMembershipPerson
    @FullBook
    @LentBookLending
    void testReturnAt_Returned() {
        final Optional<BookLending> created;

        // WHEN
        created = repository.returnAt(BookConstants.NUMBER, PersonConstants.NUMBER, BookConstants.RETURNED_DATE);

        // THEN
        Assertions.assertThat(created)
            .as("lending")
            .contains(BookLendings.returned());
    }

}
