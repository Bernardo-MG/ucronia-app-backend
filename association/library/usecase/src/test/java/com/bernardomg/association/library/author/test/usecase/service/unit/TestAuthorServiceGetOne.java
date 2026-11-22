
package com.bernardomg.association.library.author.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.library.author.domain.exception.MissingAuthorException;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.author.usecase.service.DefaultAuthorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAuthorService - getOne")
class TestAuthorServiceGetOne {

    @Mock
    private AuthorRepository     authorRepository;

    @InjectMocks
    private DefaultAuthorService service;

    @Test
    @DisplayName("When there is an author, then it is returned")
    void testGetOne() {
        final Optional<Author> author;

        // GIVEN
        given(authorRepository.findOne(AuthorConstants.NUMBER)).willReturn(Optional.of(Authors.valid()));

        // WHEN
        author = service.getOne(AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThat(author)
            .as("When there is an author, then it is returned")
            .contains(Authors.valid());
    }

    @Test
    @DisplayName("When there are no authors, then an exception is thrown")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(authorRepository.findOne(AuthorConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there are no authors, then an exception is thrown")
            .isInstanceOf(MissingAuthorException.class);
    }

}
