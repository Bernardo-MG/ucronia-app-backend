
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

import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.author.usecase.service.DefaultAuthorService;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAuthorService - create")
class TestAuthorServiceCreate {

    @Mock
    private AuthorRepository     authorRepository;

    @InjectMocks
    private DefaultAuthorService service;

    @Test
    @DisplayName("When there is an author with an empty name, then an exception is thrown")
    void testCreate_EmptyName() {
        final ThrowingCallable action;
        final Author           author;

        // GIVEN
        author = Authors.emptyName();

        // WHEN
        action = () -> service.create(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("empty", "name", ""));
    }

    @Test
    @DisplayName("When there is an author with an existing name, then an exception is thrown")
    void testCreate_ExistingName() {
        final ThrowingCallable action;
        final Author           author;

        // GIVEN
        author = Authors.toCreate();

        given(authorRepository.existsByName(AuthorConstants.NAME)).willReturn(true);

        // WHEN
        action = () -> service.create(author);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("existing", "name", AuthorConstants.NAME));
    }

    @Test
    @DisplayName("When there is an author with padded name, then the author is persisted")
    void testCreate_Padded_PersistedData() {
        final Author author;

        // GIVEN
        author = Authors.padded();

        // WHEN
        service.create(author);

        // THEN
        verify(authorRepository).save(Authors.toCreate());
    }

    @Test
    @DisplayName("When there is a valid author, then the author is persisted")
    void testCreate_PersistedData() {
        final Author author;

        // GIVEN
        author = Authors.toCreate();

        // WHEN
        service.create(author);

        // THEN
        verify(authorRepository).save(Authors.toCreate());
    }

    @Test
    @DisplayName("When there is a valid author, then the created author is returned")
    void testCreate_ReturnedData() {
        final Author author;
        final Author created;

        // GIVEN
        author = Authors.toCreate();

        given(authorRepository.save(Authors.toCreate())).willReturn(Authors.valid());

        // WHEN
        created = service.create(author);

        // THEN
        Assertions.assertThat(created)
            .as("When there is a valid author, then the created author is returned")
            .isEqualTo(Authors.valid());
    }

}
