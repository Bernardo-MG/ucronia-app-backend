/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
 */

package com.bernardomg.association.transaction.member.service.unit;

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

import com.bernardomg.association.member.domain.exception.MissingKeyException;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;
import com.bernardomg.association.member.usecase.service.DefaultKeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Key service - get one")
class TestKeyServiceGetOne {

    private static final Long NUMBER = 100L;

    @Mock
    private KeyRepository     repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("When there is data, it is returned")
    void testGetOne() {
        final Optional<Key> key;

        // GIVEN
        given(repository.findOne(NUMBER)).willReturn(Optional.of(new Key(100L, false, "Main entrance key")));

        // WHEN
        key = service.getOne(NUMBER);

        // THEN
        Assertions.assertThat(key)
            .contains(new Key(100L, false, "Main entrance key"));
    }

    @Test
    @DisplayName("With a not existing key, an exception is thrown")
    void testGetOne_NotExistingKey() {
        final ThrowingCallable execution;

        // GIVEN
        given(repository.findOne(NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingKeyException.class);
    }

}
