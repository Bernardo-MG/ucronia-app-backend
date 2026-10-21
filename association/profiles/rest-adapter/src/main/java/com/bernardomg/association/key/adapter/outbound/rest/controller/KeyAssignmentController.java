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

package com.bernardomg.association.key.adapter.outbound.rest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyAssignmentCreationDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyAssignmentPageResponseDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyAssignmentResponseDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyAssignmentUpdateDto;
import com.bernardomg.association.key.adapter.outbound.rest.model.KeyDtoMapper;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.usecase.service.KeyAssignmentService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@RestController
public class KeyAssignmentController implements KeyAssignmentApi {

    private final KeyAssignmentService service;

    public KeyAssignmentController(final KeyAssignmentService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "KEY_ASSIGNMENT", action = Actions.CREATE)
    public KeyAssignmentResponseDto createKeyAssignment(final KeyAssignmentCreationDto creation) {
        final KeyAssignment created;

        created = service.create(creation.getNumber(), creation.getMember());

        return KeyDtoMapper.toAssignmentResponseDto(created);
    }

    @Override
    @RequireResourceAuthorization(resource = "KEY_ASSIGNMENT", action = Actions.DELETE)
    public KeyAssignmentResponseDto deleteKeyAssignment(final Long number) {
        final KeyAssignment deleted;

        deleted = service.delete(number);

        return KeyDtoMapper.toAssignmentResponseDto(deleted);
    }

    @Override
    @RequireResourceAuthorization(resource = "KEY_ASSIGNMENT", action = Actions.READ)
    public KeyAssignmentPageResponseDto getKeyAssignments(final Integer page, final Integer size,
            final List<String> sort) {
        final Page<KeyAssignment> assignments;
        final Pagination          pagination;
        final Sorting             sorting;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);

        assignments = service.getAssignments(pagination, sorting);

        return KeyDtoMapper.toAssignmentsResponseDto(assignments);
    }

    @Override
    @RequireResourceAuthorization(resource = "KEY_ASSIGNMENT", action = Actions.UPDATE)
    public KeyAssignmentResponseDto updateKeyAssignment(final Long number, final KeyAssignmentUpdateDto update) {
        final KeyAssignment updated;

        updated = service.update(number, update.getMember());

        return KeyDtoMapper.toAssignmentResponseDto(updated);
    }

}
