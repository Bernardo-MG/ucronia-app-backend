
package com.bernardomg.association.library.author.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AuthorRepository - existsByNameForAnother")
class ITAuthorRepositoryExistsByNameForAnother {

    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("When another author with the same name is provided, then it exists")
    @ValidAuthor
    void testExistsByNameForAnother_Another() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(AuthorConstants.NAME, -1L);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When only the edited author is provided, then nothing exists")
    @ValidAuthor
    void testExistsByNameForAnother_Itself() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(AuthorConstants.NAME, AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is no data, then nothing exists")
    void testExistsByNameForAnother_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(AuthorConstants.NAME, AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
