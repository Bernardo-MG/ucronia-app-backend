
package com.bernardomg.association.library.author.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.author.adapter.inbound.jpa.repository.AuthorSpringRepository;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.data.annotation.ValidAuthor;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("AuthorRepository - delete")
class ITAuthorRepositoryDelete {

    @Autowired
    private AuthorRepository       repository;

    @Autowired
    private AuthorSpringRepository springRepository;

    @Test
    @DisplayName("When there is an author, then it is deleted")
    @ValidAuthor
    void testDelete() {
        // WHEN
        repository.delete(AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("authors")
            .isZero();
    }

    @Test
    @DisplayName("When the author is assigned to a book, then it is deleted")
    @ValidProfile
    @FullGameBook
    void testDelete_InBook() {
        // WHEN
        repository.delete(AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("authors")
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("authors")
            .isZero();
    }

}
