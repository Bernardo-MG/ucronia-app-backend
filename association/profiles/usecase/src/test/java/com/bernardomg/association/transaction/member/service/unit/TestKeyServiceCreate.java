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
import com.bernardomg.association.member.usecase.service.DefaultKeyService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Key service - create")
class TestKeyServiceCreate {

    private static final Key TO_CREATE = new Key(100L, false, "Main entrance key");

    @Mock
    private KeyRepository repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("With a valid key, it is persisted")
    void testCreate_PersistedData() {
        // WHEN
        service.create(TO_CREATE);

        // THEN
        verify(repository).save(TO_CREATE);
    }

    @Test
    @DisplayName("With a valid key, it is returned")
    void testCreate_ReturnedData() {
        final Key created;

        // GIVEN
        given(repository.save(TO_CREATE)).willReturn(TO_CREATE);

        // WHEN
        created = service.create(TO_CREATE);

        // THEN
        Assertions.assertThat(created)
            .as("key")
            .isEqualTo(TO_CREATE);
    }

}
