
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
@DisplayName("BookTypeRepository - existsByNameForAnother")
class ITBookTypeRepositoryExistsByNameForAnother {

    @Autowired
    private BookTypeRepository repository;

    @Test
    @DisplayName("When another book type with the same name is provided, then it exists")
    @ValidBookType
    void testExistsByNameForAnother_Another() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(BookTypeConstants.NAME, -1L);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When only the edited book type is provided, then nothing exists")
    @ValidBookType
    void testExistsByNameForAnother_Itself() {
        final boolean exists;

        // WHEN
        exists = repository.existsByNameForAnother(BookTypeConstants.NAME, BookTypeConstants.NUMBER);

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
        exists = repository.existsByNameForAnother(BookTypeConstants.NAME, BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
