
package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.book.adapter.inbound.jpa.repository.FictionBookSpringRepository;
import com.bernardomg.association.library.book.domain.model.FictionBook;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.data.annotation.MinimalFictionBook;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBookEntities;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.publisher.test.configuration.data.annotation.ValidPublisher;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FictionBookRepository - save")
class ITFictionBookRepositorySave {

    @Autowired
    private FictionBookRepository       repository;

    @Autowired
    private FictionBookSpringRepository springRepository;

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, then it is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @MinimalFictionBook
    void testSave_Existing_AddRelationships_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id",
                    "audit", "authors.audit", "publishers.audit", "lendings.audit")
                .build())
            .contains(FictionBookEntities.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are added, then it is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @MinimalFictionBook
    void testSave_Existing_AddRelationships_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
            .isEqualTo(FictionBooks.full());
    }

    @Test
    @DisplayName("When the book title is changed, then it is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @MinimalFictionBook
    void testSave_Existing_ChangeTitle_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.titleChange();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id",
                    "audit")
                .build())
            .contains(FictionBookEntities.titleChange());
    }

    @Test
    @DisplayName("When the book title is changed, then it is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    @MinimalFictionBook
    void testSave_Existing_ChangeTitle_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.titleChange();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(FictionBooks.titleChange());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, then it is persisted")
    @ValidProfile
    @FullFictionBook
    void testSave_Existing_ExistingRelationships_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id",
                    "audit", "authors.audit", "publishers.audit", "lendings.audit")
                .build())
            .contains(FictionBookEntities.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships also already exist, then it is returned")
    @ValidProfile
    @FullFictionBook
    void testSave_Existing_ExistingRelationships_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
            .isEqualTo(FictionBooks.full());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, then it is persisted")
    @ValidProfile
    @FullFictionBook
    void testSave_Existing_RemoveRelationships_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .contains(FictionBookEntities.minimal());
    }

    @Test
    @DisplayName("When there is an existing game book, and relationships are removed, then it is returned")
    @ValidProfile
    @FullFictionBook
    void testSave_Existing_RemoveRelationships_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(FictionBooks.minimal());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, then these relationships are not is persisted")
    void testSave_Full_MissingData_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id",
                    "audit")
                .build())
            .containsExactly(FictionBookEntities.noRelationships());
    }

    @Test
    @DisplayName("When there are relationships in a game book, but they don't exist, then these relationships are not returned")
    void testSave_Full_MissingData_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(FictionBooks.noRelationships());
    }

    @Test
    @DisplayName("When a fiction book with relationships is saved, then its relationships are persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    void testSave_Full_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id",
                    "audit", "authors.audit", "publishers.audit", "lendings.audit")
                .build())
            .containsExactly(FictionBookEntities.full());
    }

    @Test
    @DisplayName("When a fiction book with relationships is saved, then the saved fiction book is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    void testSave_Full_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.full();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
            .isEqualTo(FictionBooks.full());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13, then it is persisted")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    void testSave_Isbn13_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.isbn13();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "authors.id", "bookType.id", "donors.id", "gameSystem.id", "publishers.id",
                    "audit", "authors.audit", "publishers.audit", "lendings.audit")
                .build())
            .containsExactly(FictionBookEntities.isbn13());
    }

    @Test
    @DisplayName("When the game book has a ISBN-13, then it is returned")
    @ValidProfile
    @ValidAuthor
    @ValidPublisher
    void testSave_Isbn13_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.isbn13();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit", "authors.audit", "publishers.audit", "lendings.audit")
            .isEqualTo(FictionBooks.isbn13());
    }

    @Test
    @DisplayName("When there is an existing minimal fiction book, then it is persisted")
    @MinimalFictionBook
    void testSave_Minimal_Existing_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .contains(FictionBookEntities.minimal());
    }

    @Test
    @DisplayName("When there is an existing minimal game book, then it is returned")
    @MinimalFictionBook
    void testSave_Minimal_Existing_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(FictionBooks.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved, then it is persisted")
    void testSave_Minimal_Persisted() {
        final FictionBook book;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        repository.save(book);

        // THEN
        Assertions.assertThat(springRepository.findAll())
            .as("books")
            .usingRecursiveFieldByFieldElementComparator(RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("id", "audit")
                .build())
            .containsExactly(FictionBookEntities.minimal());
    }

    @Test
    @DisplayName("When the minimal game book is saved, then it is returned")
    void testSave_Minimal_Returned() {
        final FictionBook book;
        final FictionBook created;

        // GIVEN
        book = FictionBooks.minimal();

        // WHEN
        created = repository.save(book);

        // THEN
        Assertions.assertThat(created)
            .as("book")
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(FictionBooks.minimal());
    }

}
