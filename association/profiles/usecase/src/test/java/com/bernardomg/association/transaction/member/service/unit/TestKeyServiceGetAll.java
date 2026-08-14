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

import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.key.usecase.service.DefaultKeyService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

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
        final Page<Key>  keys;
        final Key        key;
        final Pagination pagination;
        final Sorting    sorting;
        final Page<Key>  existing;

        // GIVEN
        pagination = Pagination.unpaged();
        sorting = Sorting.unsorted();

        key = Keys.available();
        existing = new Page<>(List.of(key), 0, 0, 0, 0, 0, false, false, sorting);
        given(repository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        keys = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(keys)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("keys")
            .containsExactly(key);
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testGetAll_NoData() {
        final Page<Key>  keys;
        final Pagination pagination;
        final Sorting    sorting;
        final Page<Key>  existing;

        // GIVEN
        pagination = Pagination.unpaged();
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(repository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        keys = service.getAll(pagination, sorting);

        // THEN
        Assertions.assertThat(keys)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
