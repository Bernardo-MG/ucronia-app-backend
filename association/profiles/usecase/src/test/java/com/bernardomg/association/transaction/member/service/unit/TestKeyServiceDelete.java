/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
 */

package com.bernardomg.association.transaction.member.service.unit;

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

import com.bernardomg.association.member.domain.exception.MissingKeyException;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;
import com.bernardomg.association.member.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.test.configuration.factory.Keys;
import com.bernardomg.association.member.usecase.service.DefaultKeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Key service - delete")
class TestKeyServiceDelete {

    @Mock
    private KeyRepository     repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("When deleting a key, it is removed")
    void testDelete() {
        final Key key;

        // GIVEN
        key = Keys.available();
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(key));

        // WHEN
        service.delete(KeyConstants.NUMBER);

        // THEN
        verify(repository).delete(KeyConstants.NUMBER);
    }

    @Test
    @DisplayName("With a not existing key, an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable execution;
        Keys.available();
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingKeyException.class);
    }

}
