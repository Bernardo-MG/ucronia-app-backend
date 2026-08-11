/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
 */

package com.bernardomg.association.transaction.member.service.unit;

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

import com.bernardomg.association.member.domain.exception.MissingKeyException;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;
import com.bernardomg.association.member.usecase.service.DefaultKeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Key service - update")
class TestKeyServiceUpdate {

    private static final Long NUMBER = 100L;

    private static final Key  KEY    = new Key(NUMBER, true, "Missing key");

    @Mock
    private KeyRepository     repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("When updating a key, the change is persisted")
    void testUpdate_PersistedData() {
        // GIVEN
        given(repository.exists(NUMBER)).willReturn(true);
        given(repository.save(KEY)).willReturn(KEY);

        // WHEN
        service.update(KEY);

        // THEN
        verify(repository).save(KEY);
    }

    @Test
    @DisplayName("When updating a key, the change is returned")
    void testUpdate_ReturnedData() {
        final Key updated;

        // GIVEN
        given(repository.exists(NUMBER)).willReturn(true);
        given(repository.save(KEY)).willReturn(KEY);

        // WHEN
        updated = service.update(KEY);

        // THEN
        Assertions.assertThat(updated)
            .isEqualTo(KEY);
    }

    @Test
    @DisplayName("When updating a not existing key, an exception is thrown")
    void testUpdate_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(repository.exists(NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(KEY);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingKeyException.class);
    }

}
