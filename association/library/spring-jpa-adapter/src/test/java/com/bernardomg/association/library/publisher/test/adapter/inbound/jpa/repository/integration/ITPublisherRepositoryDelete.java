
package com.bernardomg.association.library.publisher.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublisherRepository - delete")
class ITPublisherRepositoryDelete {

    @Autowired
    private PublisherRepository       repository;

    @Autowired
    private PublisherSpringRepository springRepository;

    @Test
    @DisplayName("When there is an publisher, then it is deleted")
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
    @DisplayName("When the publisher is assigned to a book, then it is deleted")
    @ValidProfile
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
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("publishers")
            .isZero();
    }

}
