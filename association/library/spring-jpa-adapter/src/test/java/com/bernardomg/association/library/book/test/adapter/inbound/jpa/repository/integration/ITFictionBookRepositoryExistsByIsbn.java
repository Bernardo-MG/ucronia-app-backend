
package com.bernardomg.association.library.book.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.domain.repository.FictionBookRepository;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullFictionBook;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FictionBookRepository - existsByIsbn")
class ITFictionBookRepositoryExistsByIsbn {

    @Autowired
    private FictionBookRepository repository;

    @Test
    @DisplayName("When the fiction book exists, then it exists")
    @ValidProfile
    @FullFictionBook
    void testExistsByIsbn() {
        final boolean exists;

        // WHEN
        exists = repository.existsByIsbn(BookConstants.ISBN_10);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no data, then nothing exists")
    void testExistsByIsbn_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByIsbn(BookConstants.ISBN_10);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
