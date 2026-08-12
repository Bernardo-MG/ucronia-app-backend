/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
 */

package com.bernardomg.association.transaction.member.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.domain.repository.KeyRepository;
import com.bernardomg.association.member.test.configuration.factory.Keys;
import com.bernardomg.association.member.usecase.service.DefaultKeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Key service - create")
class TestKeyServiceCreate {

    @Mock
    private KeyRepository     repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("With a valid key, it is persisted")
    void testCreate_PersistedData() {
        final Key key;

        // GIVEN
        key = Keys.valid();

        // WHEN
        service.create(key);

        // THEN
        verify(repository).save(key);
    }

    @Test
    @DisplayName("With a valid key, it is returned")
    void testCreate_ReturnedData() {
        final Key created;
        final Key key;

        // GIVEN
        key = Keys.valid();
        given(repository.save(key)).willReturn(key);

        // WHEN
        created = service.create(key);

        // THEN
        Assertions.assertThat(created)
            .as("key")
            .isEqualTo(key);
    }

}
