
package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.GameBookSpringRepository;
import com.bernardomg.association.library.book.domain.model.GameBook;
import com.bernardomg.association.library.book.domain.repository.GameBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalGameBook;
import com.bernardomg.association.library.book.test.configuration.factory.GameBookEntities;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.gamesystem.test.configuration.data.annotation.ValidGameSystem;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.security.adapter.inbound.jpa.model.audit.AuditMetadata;
import com.bernardomg.security.domain.audit.model.AuditDetails;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("GameBookRepository - save")
class ITGameBookRepositorySave {

    @Autowired
    private GameBookRepository       repository;

    @Autowired
    private GameBookSpringRepository springRepository;

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, then it is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    @MinimalGameBook
    void testSave_Existing_AddRelationships_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditMetadata.class)
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id")
                .build())
            .contains(GameBookEntities.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, then it is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    @MinimalGameBook
    void testSave_Existing_AddRelationships_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(AuditDetails.class)
            .isEqualTo(GameBooks.full());
    }

    @Test
    @DisplayName("When changing the book title, then it is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    @MinimalGameBook
    void testSave_Existing_ChangeTitle_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.titleChange();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id",
                    "audit")
                .build())
            .contains(GameBookEntities.titleChange());
    }

    @Test
    @DisplayName("When changing the book title, then it is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    @MinimalGameBook
    void testSave_Existing_ChangeTitle_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.titleChange();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(GameBooks.titleChange());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, then it is persisted")
    @ValidProfile
    @FullGameBook
    void testSave_Existing_ExistingRelationships_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditMetadata.class)
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id")
                .build())
            .contains(GameBookEntities.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, then it is returned")
    @ValidProfile
    @FullGameBook
    void testSave_Existing_ExistingRelationships_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(AuditDetails.class)
            .isEqualTo(GameBooks.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, then it is persisted")
    @ValidProfile
    @FullGameBook
    void testSave_Existing_RemoveRelationships_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .contains(GameBookEntities.minimal());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, then it is returned")
    @ValidProfile
    @FullGameBook
    void testSave_Existing_RemoveRelationships_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(GameBooks.minimal());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, then these relationships are not is persisted")
    void testSave_Full_MissingData_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id",
                    "audit")
                .build())
            .containsExactly(GameBookEntities.noRelationships());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, then these relationships are not returned")
    void testSave_Full_MissingData_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(GameBooks.noRelationships());
    }

    @Test
    @DisplayName("When a game book with relationships is saved, then its relationships are persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_Full_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditMetadata.class)
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id")
                .build())
            .containsExactly(GameBookEntities.full());
    }

    @Test
    @DisplayName("When a game book with relationships is saved, then the saved game book is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_Full_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(AuditDetails.class)
            .isEqualTo(GameBooks.full());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13, then it is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_Isbn13_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.isbn13();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFieldsOfTypes(AuditMetadata.class)
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id")
                .build())
            .containsExactly(GameBookEntities.isbn13());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13, then it is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @ValidBookType
    @ValidGameSystem
    void testSave_Isbn13_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.isbn13();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFieldsOfTypes(AuditDetails.class)
            .isEqualTo(GameBooks.isbn13());
    }

    @Test
    @DisplayName("When there is an existing minimal game book, then it is persisted")
    @MinimalGameBook
    void testSave_Minimal_Existing_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .contains(GameBookEntities.minimal());
    }

    @Test
    @DisplayName("When there is an existing minimal game book, then it is returned")
    @MinimalGameBook
    void testSave_Minimal_Existing_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(GameBooks.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved, then it is persisted")
    void testSave_Minimal_Persisted() {
        final GameBook book;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .containsExactly(GameBookEntities.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved, then it is returned")
    void testSave_Minimal_Returned() {
        final GameBook book;
        final GameBook created;

        // GIVEN
        book = GameBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(GameBooks.minimal());
    }

}
