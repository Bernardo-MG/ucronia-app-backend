
package com.bernardomg.association.library.author.test.usecase.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.association.library.author.domain.repository.AuthorRepository;
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.author.usecase.service.DefaultAuthorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultAuthorService - delete")
class TestAuthorServiceDelete {

    @Mock
    private AuthorRepository     authorRepository;

    @InjectMocks
    private DefaultAuthorService service;

    @Test
    @DisplayName("When deleting an author, then the repository is called")
    void testDelete_CallsRepository() {
        // GIVEN
        given(authorRepository.findOne(AuthorConstants.NUMBER)).willReturn(Optional.of(Authors.valid()));

        // WHEN
        service.delete(AuthorConstants.NUMBER);

        // THEN
        verify(authorRepository).delete(AuthorConstants.NUMBER);
    }

    @Test
    @DisplayName("When the author doesn't exist, then an exception is thrown")
    void testDelete_NotExisting_Exception() {
        final ThrowingCallable action;

        // GIVEN
        given(authorRepository.findOne(AuthorConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(AuthorConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the author doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingAuthorException.class);
    }

}
