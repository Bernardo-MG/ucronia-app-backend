
package com.bernardomg.association.library.booktype.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeEntities;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookTypeRepository - save")
class ITBookTypeRepositorySave {

    @Autowired
    private BookTypeRepository       repository;

    @Autowired
    private BookTypeSpringRepository springRepository;

    @Test
    @DisplayName("When an entity is created or updated, then audit timestamps are set and the creation time is preserved")
    void testSave_Audit() {
        final BookType created;
        final BookType updated;

        // GIVEN
        created = repository.findOne(repository.save(new BookType(1L, "Audit original"))
            .number())
            .orElseThrow();

        // WHEN
        updated = repository.save(new BookType(created.number(), "Audit changed"));

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
    @DisplayName("When the book title is changed, then it is persisted")
    @ValidBookType
    void testSave_NameChange_Persisted() {
        final BookType bookType;

        // GIVEN
        bookType = BookTypes.nameChange();

        // WHEN
        repository.save(bookType);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("book types")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .containsExactly(BookTypeEntities.nameChange());
    }

    @Test
    @DisplayName("When the book title is changed, then it is returned")
    @ValidBookType
    void testSave_NameChange_Returned() {
        final BookType bookType;
        final BookType created;

        // GIVEN
        bookType = BookTypes.nameChange();

        // WHEN
        created = repository.save(bookType);

        // THEN
        Assertions.assertThat(created)
            .as("book type")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(BookTypes.nameChange());
    }

    @Test
    @DisplayName("When saving, then a book type is persisted")
    void testSave_Persisted() {
        final BookType bookType;

        // GIVEN
        bookType = BookTypes.valid();

        // WHEN
        repository.save(bookType);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("book types")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .containsExactly(BookTypeEntities.valid());
    }

    @Test
    @DisplayName("When saving, then the persisted book type is returned")
    void testSave_Returned() {
        final BookType bookType;
        final BookType created;

        // GIVEN
        bookType = BookTypes.valid();

        // WHEN
        created = repository.save(bookType);

        // THEN
        Assertions.assertThat(created)
            .as("book type")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(BookTypes.valid());
    }

}
