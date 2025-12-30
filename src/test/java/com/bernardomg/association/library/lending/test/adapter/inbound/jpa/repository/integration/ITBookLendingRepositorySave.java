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

package com.bernardomg.association.library.lending.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.lending.adapter.inbound.jpa.repository.BookLendingSpringRepository;
import com.bernardomg.association.library.lending.domain.model.BookLending;
import com.bernardomg.association.library.lending.domain.repository.BookLendingRepository;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendingEntities;
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("BookLendingRepository - save")
class ITBookLendingRepositorySave {

    @Autowired
    private BookLendingRepository       repository;

    @Autowired
    private BookLendingSpringRepository springRepository;

    @Test
    @DisplayName("When saving a lending for a fiction book and the profile doesnt exist, nothing is persisted")
    @MinimalFictionBook
    void testSave_FictionBook_NoMember() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("lendings")
            .isZero();
    }

    @Test
    @DisplayName("When saving a lending for a fiction book, a lending is persisted")
    @ValidProfile
    @FullFictionBook
    void testSave_FictionBook_Persisted() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .contains(BookLendingEntities.lent());
    }

    @Test
    @DisplayName("When saving a lending for a fiction book, the persisted lending is returned")
    @ValidProfile
    @FullFictionBook
    void testSave_FictionBook_Returned() {
        final BookLending lending;
        final BookLending created;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        created = repository.save(lending);

        // THEN
        Assertions.assertThat(created)
            .as("lending")
            .isEqualTo(BookLendings.lent());
    }

    @Test
    @DisplayName("When saving a lending for a game book and the profile doesnt exist, nothing is persisted")
    @MinimalGameBook
    void testSave_GameBook_NoMember() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("lendings")
            .isZero();
    }

    @Test
    @DisplayName("When saving a lending for a game book, a lending is persisted")
    @ValidProfile
    @FullGameBook
    void testSave_GameBook_Persisted() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("lendings")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .contains(BookLendingEntities.lent());
    }

    @Test
    @DisplayName("When saving a lending for a game book, the persisted lending is returned")
    @ValidProfile
    @FullGameBook
    void testSave_GameBook_Returned() {
        final BookLending lending;
        final BookLending created;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        created = repository.save(lending);

        // THEN
        Assertions.assertThat(created)
            .as("lending")
            .isEqualTo(BookLendings.lent());
    }

    @Test
    @DisplayName("When saving a lending for a book which doesn't exist, nothing is persisted")
    @ValidProfile
    void testSave_NoBook() {
        final BookLending lending;

        // GIVEN
        lending = BookLendings.lent();

        // WHEN
        repository.save(lending);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("lendings")
            .isZero();
    }

}
