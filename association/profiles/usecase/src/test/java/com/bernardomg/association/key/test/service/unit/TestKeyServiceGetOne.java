/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
 */

package com.bernardomg.association.key.test.service.unit;

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

import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.key.usecase.service.DefaultKeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Key service - get one")
class TestKeyServiceGetOne {

    @Mock
    private KeyRepository     repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("When there is data, it is returned")
    void testGetOne() {
        final Optional<Key> read;
        final Key           key;

        // GIVEN
        key = Keys.available();
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(key));

        // WHEN
        read = service.getOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(read)
            .contains(key);
    }

    @Test
    @DisplayName("With a not existing key, an exception is thrown")
    void testGetOne_NotExistingKey() {
        final ThrowingCallable execution;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingKeyException.class);
    }

}
