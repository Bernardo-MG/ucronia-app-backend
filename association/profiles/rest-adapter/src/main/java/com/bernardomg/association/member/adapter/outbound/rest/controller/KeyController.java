/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.member.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.member.adapter.outbound.rest.dto.KeyCreationDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.KeyPageResponseDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.KeyResponseDto;
import com.bernardomg.association.member.adapter.outbound.rest.dto.KeyUpdateDto;
import com.bernardomg.association.member.adapter.outbound.rest.model.KeyDtoMapper;
import com.bernardomg.association.member.domain.model.Key;
import com.bernardomg.association.member.usecase.service.KeyService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class KeyController implements KeyApi {

    private final KeyService service;

    public KeyController(final KeyService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.CREATE)
    public KeyResponseDto createKey(final KeyCreationDto keyCreationDto) {
        final Key key;
        final Key created;

        key = KeyDtoMapper.toDomain(keyCreationDto);
        created = service.create(key);

        return KeyDtoMapper.toResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.DELETE)
    public KeyResponseDto deleteKey(final Long number) {
        final Key key;

        key = service.delete(number);

        return KeyDtoMapper.toResponseDto(key);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.READ)
    public KeyPageResponseDto getAllKeys(final Integer page, final Integer size, final List<String> sort) {
        final Page<Key>  keys;
        final Pagination pagination;
        final Sorting    sorting;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);

        keys = service.getAll(pagination, sorting);

        return KeyDtoMapper.toResponseDto(keys);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.READ)
    public KeyResponseDto getKeyByNumber(final Long number) {
        final Optional<Key> key;

        key = service.getOne(number);

        return KeyDtoMapper.toResponseDto(key);
    }

    @Override
    @RequireResourceAuthorization(resource = "MEMBER_PROFILE", action = Actions.UPDATE)
    public KeyResponseDto updateKey(final Long number, final KeyUpdateDto keyUpdateDto) {
        final Key key;
        final Key updated;

        key = KeyDtoMapper.toDomain(number, keyUpdateDto);
        updated = service.update(key);

        return KeyDtoMapper.toResponseDto(updated);
    }

}
