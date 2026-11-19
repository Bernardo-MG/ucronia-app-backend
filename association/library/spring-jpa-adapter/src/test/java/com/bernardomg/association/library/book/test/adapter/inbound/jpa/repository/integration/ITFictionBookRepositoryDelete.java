
package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.FictionBookSpringRepository;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FictionBookRepository - delete")
class ITFictionBookRepositoryDelete {

    @Autowired
    private AuthorSpringRepository      authorSpringRepository;

    @Autowired
    private PublisherSpringRepository   publisherSpringRepository;

    @Autowired
    private FictionBookRepository       repository;

    @Autowired
    private FictionBookSpringRepository springRepository;

    @Test
    @DisplayName("When the fiction book exists, then it is deleted")
    @ValidProfile
    @FullFictionBook
    void testDelete() {
        // WHEN
        repository.delete(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("books")
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(BookConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("books")
            .isZero();
    }

    @Test
    @DisplayName("When the fiction book is deleted, then the related entities are kept")
    @ValidProfile
    @FullFictionBook
    void testDelete_Relationships() {
        // WHEN
        repository.delete(BookConstants.NUMBER);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(authorSpringRepository.count())
                .as("authors")
                .isNotZero();
            softly.assertThat(publisherSpringRepository.count())
                .as("publishers")
                .isNotZero();
        });
    }

}
