
package com.bernardomg.association.library.publisher.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherConstants;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublisherRepository - findOne")
class ITPublisherRepositoryFindOne {

    @Autowired
    private PublisherRepository repository;

    @Test
    @DisplayName("When there is an publisher, then it is returned")
    @ValidPublisher
    void testFindOne() {
        final Optional<Publisher> publisher;

        // WHEN
        publisher = repository.findOne(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(publisher)
            .as("publisher")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is an publisher, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Publishers.valid()));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Publisher> publisher;

        // WHEN
        publisher = repository.findOne(PublisherConstants.NUMBER);

        // THEN
        Assertions.assertThat(publisher)
            .as("publisher")
            .isEmpty();
    }

}
