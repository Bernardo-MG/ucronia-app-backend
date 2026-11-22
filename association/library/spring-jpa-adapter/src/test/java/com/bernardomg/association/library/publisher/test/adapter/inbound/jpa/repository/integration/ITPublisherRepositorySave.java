
package com.bernardomg.association.library.publisher.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.publisher.adapter.inbound.jpa.repository.PublisherSpringRepository;
import com.bernardomg.association.library.publisher.domain.model.Publisher;
import com.bernardomg.association.library.publisher.domain.repository.PublisherRepository;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.library.publisher.test.configuration.factory.PublisherEntities;
import com.bernardomg.association.library.publisher.test.configuration.factory.Publishers;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublisherRepository - save")
class ITPublisherRepositorySave {

    @Autowired
    private PublisherRepository       repository;

    @Autowired
    private PublisherSpringRepository springRepository;

    @Test
    @DisplayName("When an entity is created or updated, then audit timestamps are set and the creation time is preserved")
    void testSave_Audit() {
        final Publisher created;
        final Publisher updated;

        // GIVEN
        created = repository.findOne(repository.save(new Publisher(1L, "Audit original"))
            .number())
            .orElseThrow();

        // WHEN
        updated = repository.save(new Publisher(created.number(), "Audit changed"));

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(created.audit()
                .createdAt())
                .as("creation timestamp is set")
                .isNotNull();
            softly.assertThat(created.audit()
                .updatedAt())
                .as("update timestamp is set")
                .isNotNull();
            softly.assertThat(updated.audit()
                .createdAt())
                .as("creation timestamp is preserved")
                .isEqualTo(created.audit()
                    .createdAt());
            softly.assertThat(updated.audit()
                .updatedAt())
                .as("update timestamp advances")
                .isAfterOrEqualTo(created.audit()
                    .updatedAt());
            softly.assertThat(repository.findOne(created.number())
                .orElseThrow()
                .audit()
                .createdAt())
                .as("persisted creation timestamp is preserved")
                .isEqualTo(created.audit()
                    .createdAt());
        });
    }

    @Test
    @DisplayName("When the publisher name is changed, then it is updated")
    @ValidPublisher
    void testSave_NameChange_Persisted() {
        final Publisher publisher;

        // GIVEN
        publisher = Publishers.nameChange();

        // WHEN
        repository.save(publisher);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("publishers")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .containsExactly(PublisherEntities.nameChange());
    }

    @Test
    @DisplayName("When the publisher name is changed, then it is updated")
    @ValidPublisher
    void testSave_NameChange_Returned() {
        final Publisher publisher;
        final Publisher created;

        // GIVEN
        publisher = Publishers.nameChange();

        // WHEN
        created = repository.save(publisher);

        // THEN
        Assertions.assertThat(created)
            .as("publisher")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Publishers.nameChange());
    }

    @Test
    @DisplayName("When saving, then an publisher is persisted")
    void testSave_Persisted() {
        final Publisher publisher;

        // GIVEN
        publisher = Publishers.valid();

        // WHEN
        repository.save(publisher);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("publishers")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .containsExactly(PublisherEntities.valid());
    }

    @Test
    @DisplayName("When saving, then the persisted publisher is returned")
    void testSave_Returned() {
        final Publisher publisher;
        final Publisher created;

        // GIVEN
        publisher = Publishers.valid();

        // WHEN
        created = repository.save(publisher);

        // THEN
        Assertions.assertThat(created)
            .as("publisher")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Publishers.valid());
    }

}
