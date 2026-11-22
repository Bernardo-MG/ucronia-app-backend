
package com.bernardomg.association.library.author.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAuthorService - update")
class TestAuthorServiceUpdate {

    @Mock
    private AuthorRepository     authorRepository;

    @InjectMocks
    private DefaultAuthorService service;

    @Test
    @DisplayName("When there is an author with an empty name, then an exception is thrown")
    void testUpdate_EmptyName() {
        final ThrowingCallable action;
        final Author           author;

        // GIVEN
        author = Authors.emptyName();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("When there is an author with a name existing for another user, then an exception is thrown")
    void testUpdate_ExistsForAnother() {
        final Author           author;
        final ThrowingCallable action;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);
        given(authorRepository.existsByNameForAnother(AuthorConstants.NAME, AuthorConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("existing", "name", AuthorConstants.NAME));
    }

    @Test
    @DisplayName("When there is a not existing author, then an exception is thrown")
    void testUpdate_NotExisting() {
        final Author           author;
        final ThrowingCallable action;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(author);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing author, then an exception is thrown")
            .isInstanceOf(MissingAuthorException.class);
    }

    @Test
    @DisplayName("When there is a valid author, then the author is persisted")
    void testUpdate_PersistedData() {
        final Author author;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(author);

        // THEN
        verify(authorRepository).save(Authors.valid());
    }

    @Test
    @DisplayName("When there is a valid author, then the created author is returned")
    void testUpdate_ReturnedData() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.valid();

        given(authorRepository.exists(AuthorConstants.NUMBER)).willReturn(true);

        given(authorRepository.save(Authors.valid())).willReturn(Authors.valid());

        // WHEN
        created = service.update(author);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid author, then the created author is returned")
            .isEqualTo(Authors.valid());
    }

}
