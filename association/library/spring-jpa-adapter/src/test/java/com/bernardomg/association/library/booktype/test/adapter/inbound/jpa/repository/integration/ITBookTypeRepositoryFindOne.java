
package com.bernardomg.association.library.booktype.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.booktype.domain.model.BookType;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypes;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookTypeRepository - findOne")
class ITBookTypeRepositoryFindOne {

    @Autowired
    private BookTypeRepository repository;

    @Test
    @DisplayName("When there is a book type, then it is returned")
    @ValidBookType
    void testFindOne() {
        final Optional<BookType> bookType;

        // WHEN
        bookType = repository.findOne(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(bookType)
            .as("book type")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a book type, then it is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(BookTypes.valid()));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<BookType> bookType;

        // WHEN
        bookType = repository.findOne(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(bookType)
            .as("book type")
            .isEmpty();
    }

}
