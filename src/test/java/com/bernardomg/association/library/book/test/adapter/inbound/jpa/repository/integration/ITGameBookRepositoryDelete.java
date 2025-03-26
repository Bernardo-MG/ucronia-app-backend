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
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.GameBookSpringRepository;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.gamesystem.adapter.inbound.jpa.repository.GameSystemSpringRepository;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("GameBookRepository - delete")
class ITGameBookRepositoryDelete {

    @Autowired
    private AuthorSpringRepository     authorSpringRepository;

    @Autowired
    private BookTypeSpringRepository   bookTypeSpringRepository;

    @Autowired
    private GameSystemSpringRepository gameSystemSpringRepository;

    @Autowired
    private PublisherSpringRepository  publisherSpringRepository;

    @Autowired
    private GameBookRepository         repository;

    @Autowired
    private GameBookSpringRepository   springRepository;

    @Test
    @DisplayName("Then the game book exists, it is deleted")
    @NoMembershipPerson
    @FullGameBook
    void testDelete() {
        // WHEN
        repository.delete(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("books")
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("books")
            .isZero();
    }

    @Test
    @DisplayName("When the game book is deleted, the related entities are kept")
    @NoMembershipPerson
    @FullGameBook
    void testDelete_Relationships() {
        // WHEN
        repository.delete(BookConstants.NUMBER);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(authorSpringRepository.count())
                .as("authors")
                .isNotZero();
            softly.assertThat(bookTypeSpringRepository.count())
                .as("book types")
                .isNotZero();
            softly.assertThat(gameSystemSpringRepository.count())
                .as("game systems")
                .isNotZero();
            softly.assertThat(publisherSpringRepository.count())
                .as("publishers")
                .isNotZero();
        });
    }

}
