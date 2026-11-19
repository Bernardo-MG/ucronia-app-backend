
package com.bernardomg.association.library.booktype.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookTypeRepository - existsByName")
class ITBookTypeRepositoryExistsByName {

    @Autowired
    private BookTypeRepository repository;

    @Test
    @DisplayName("When there is a book type, then it exists")
    @ValidBookType
    void testExistsByName() {
        final boolean exists;

        // WHEN
        exists = repository.existsByName(BookTypeConstants.NAME);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no data, then nothing exists")
    void testExistsByName_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsByName(BookTypeConstants.NAME);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
