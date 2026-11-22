
package com.bernardomg.association.library.author.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AuthorRepository - findOne")
class ITAuthorRepositoryFindOne {

    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("When there is an author, then it is returned")
    @ValidAuthor
    void testFindOne() {
        final Optional<Author> author;

        // WHEN
        author = repository.findOne(AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThat(author)
            .as("author")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is an author, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Authors.valid()));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Author> author;

        // WHEN
        author = repository.findOne(AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThat(author)
            .as("author")
            .isEmpty();
    }

}
