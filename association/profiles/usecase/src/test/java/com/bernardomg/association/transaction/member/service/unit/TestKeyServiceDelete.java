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
import com.bernardomg.association.member.usecase.service.DefaultKeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Key service - delete")
class TestKeyServiceDelete {

    private static final Long NUMBER = 100L;

    private static final Key  KEY    = new Key(NUMBER, false, "Main entrance key");

    @Mock
    private KeyRepository     repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("When deleting a key, it is removed")
    void testDelete() {
        // GIVEN
        given(repository.findOne(NUMBER)).willReturn(Optional.of(KEY));

        // WHEN
        service.delete(NUMBER);

        // THEN
        verify(repository).delete(NUMBER);
    }

    @Test
    @DisplayName("With a not existing key, an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(repository.findOne(NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.delete(NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingKeyException.class);
    }

}
