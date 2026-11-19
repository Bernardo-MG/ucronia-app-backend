
package com.bernardomg.association.library.publisher.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublisherRepository - exists")
class ITPublisherRepositoryExists {

    @Autowired
    private PublisherRepository repository;

    @Test
    @DisplayName("When there is an publisher, then it exists")
    @ValidPublisher
    void testExists() {
        final boolean exists;

        // WHEN
        exists = repository.exists(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no data, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
