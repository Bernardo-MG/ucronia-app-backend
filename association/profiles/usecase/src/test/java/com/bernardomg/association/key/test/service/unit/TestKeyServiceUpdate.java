
package com.bernardomg.association.key.test.service.unit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.key.usecase.service.DefaultKeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultKeyService - update")
class TestKeyServiceUpdate {

    @Mock
    private KeyRepository     repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("When updating a not existing key, then an exception is thrown")
    void testUpdate_NotExisting() {
        final ThrowingCallable action;
        final Key              key;

        // GIVEN
        key = Keys.descriptionChange();
        given(repository.exists(KeyConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(key);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When updating a not existing key, then an exception is thrown")
            .isInstanceOf(MissingKeyException.class);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("When updating a key, then the change is persisted")
    void testUpdate_PersistedData() {
        final Key key;

        // GIVEN
        key = Keys.descriptionChange();
        given(repository.exists(KeyConstants.NUMBER)).willReturn(true);
        given(repository.save(key)).willReturn(key);

        // WHEN
        service.update(key);

        // THEN
        verify(repository).save(key);
    }

    @Test
    @DisplayName("When updating a key, then the change is returned")
    void testUpdate_ReturnedData() {
        final Key updated;
        final Key key;

        // GIVEN
        key = Keys.descriptionChange();
        given(repository.exists(KeyConstants.NUMBER)).willReturn(true);
        given(repository.save(key)).willReturn(key);

        // WHEN
        updated = service.update(key);

        // THEN
        Assertions.assertThat(updated)
            .as("When updating a key, then the change is returned")
            .isEqualTo(key);
    }

}
