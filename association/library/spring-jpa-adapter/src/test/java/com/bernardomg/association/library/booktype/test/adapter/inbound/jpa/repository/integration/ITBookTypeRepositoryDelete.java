
package com.bernardomg.association.library.booktype.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.library.TestApplication;
import com.bernardomg.association.library.book.test.configuration.data.annotation.FullGameBook;
import com.bernardomg.association.library.booktype.adapter.inbound.jpa.repository.BookTypeSpringRepository;
import com.bernardomg.association.library.booktype.domain.repository.BookTypeRepository;
import com.bernardomg.association.library.booktype.test.configuration.data.annotation.ValidBookType;
import com.bernardomg.association.library.booktype.test.configuration.factory.BookTypeConstants;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("BookTypeRepository - delete")
class ITBookTypeRepositoryDelete {

    @Autowired
    private BookTypeRepository       repository;

    @Autowired
    private BookTypeSpringRepository springRepository;

    @Test
    @DisplayName("When there is a book type, then it is deleted")
    @ValidBookType
    void testDelete() {
        // WHEN
        repository.delete(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("book types")
            .isZero();
    }

    @Test
    @DisplayName("When the book type is assigned to a book, then it is deleted")
    @ValidProfile
    @FullGameBook
    void testDelete_InBook() {
        // WHEN
        repository.delete(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("book types")
            .isZero();
    }

    @Test
    @DisplayName("When there is no data, then nothing is deleted")
    void testDelete_NoData() {
        // WHEN
        repository.delete(BookTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .as("book types")
            .isZero();
    }

}
