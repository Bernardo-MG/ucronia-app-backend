/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original publisher or publishers.
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

package com.bernardomg.association.library.publisher.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.person.test.configuration.data.annotation.NoMembershipPerson;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("PublisherRepository - delete")
class ITPublisherRepositoryDelete {

    @Autowired
    private PublisherRepository       repository;

    @Autowired
    private PublisherSpringRepository springRepository;

    @Test
    @DisplayName("With an publisher, it is deleted")
    @ValidPublisher
    void testDelete() {
        // WHEN
        repository.delete(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("publishers")
            .isZero();
    }

    @Test
    @DisplayName("When the publisher is assigned to a book, it is deleted")
    @NoMembershipPerson
    @FullGameBook
    void testDelete_InBook() {
        // WHEN
        repository.delete(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("publishers")
            .isZero();
    }

    @Test
    @DisplayName("With no data, nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("publishers")
            .isZero();
    }

}
