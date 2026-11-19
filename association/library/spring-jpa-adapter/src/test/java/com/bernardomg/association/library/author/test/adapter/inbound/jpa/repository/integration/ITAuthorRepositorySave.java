
package com.bernardomg.association.library.author.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorEntities;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AuthorRepository - save")
class ITAuthorRepositorySave {

    @Autowired
    private AuthorRepository       repository;

    @Autowired
    private AuthorSpringRepository springRepository;

    @Test
    @DisplayName("When an entity is created or updated, then audit timestamps are set and the creation time is preserved")
    void testSave_Audit() {
        final Author created;
        final Author updated;

        // GIVEN
        created = repository.findOne(repository.save(new Author(1L, "Audit original"))
            .number())
            .orElseThrow();

        // WHEN
        updated = repository.save(new Author(created.number(), "Audit changed"));

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
    @DisplayName("When the author name is changed, then it is updated")
    @ValidAuthor
    void testSave_Existing_ChangeName_Persisted() {
        final Author author;

        // GIVEN
        author = Authors.nameChange();

        // WHEN
        repository.save(author);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("authors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(AuthorEntities.nameChange());
    }

    @Test
    @DisplayName("When the author name is changed, then it is returned")
    @ValidAuthor
    void testSave_Existing_ChangeName_Returned() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.nameChange();

        // WHEN
        created = repository.save(author);

        // THEN
        Assertions.assertThat(created)
            .as("author")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Authors.nameChange());
    }

    @Test
    @DisplayName("When saving, then an author is persisted")
    void testSave_Persisted() {
        final Author author;

        // GIVEN
        author = Authors.valid();

        // WHEN
        repository.save(author);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("authors")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "audit")
            .containsExactly(AuthorEntities.valid());
    }

    @Test
    @DisplayName("When saving, then the persisted author is returned")
    void testSave_Returned() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.valid();

        // WHEN
        created = repository.save(author);

        // THEN
        Assertions.assertThat(created)
            .as("author")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Authors.valid());
    }

}
