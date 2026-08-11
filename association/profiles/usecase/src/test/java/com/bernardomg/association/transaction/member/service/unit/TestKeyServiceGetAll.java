/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
 */

package com.bernardomg.association.transaction.member.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
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
@DisplayName("Key service - get all")
class TestKeyServiceGetAll {

    @Mock
    private KeyRepository     repository;

    @InjectMocks
    private DefaultKeyService service;

    @Test
    @DisplayName("When there is data, it is returned")
    void testGetAll() {
        final Iterable<Key> keys;

        // GIVEN
        given(repository.findAll()).willReturn(List.of(new Key(100L, false, "Main entrance key")));

        // WHEN
        keys = service.getAll();

        // THEN
        Assertions.assertThat(keys)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(new Key(100L, false, "Main entrance key"));
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testGetAll_NoData() {
        final Iterable<Key> keys;

        // GIVEN
        given(repository.findAll()).willReturn(List.of());

        // WHEN
        keys = service.getAll();

        // THEN
        Assertions.assertThat(keys)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
