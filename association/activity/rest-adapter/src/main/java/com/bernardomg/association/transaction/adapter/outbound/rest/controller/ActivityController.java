/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
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

package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.activity.adapter.outbound.rest.controller.ActivityApi;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityCreationDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityPageResponseDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityResponseDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityUpdateDto;
import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.association.activity.usecase.service.ActivityService;
import com.bernardomg.association.transaction.adapter.outbound.rest.model.ActivityDtoMapper;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

/**
 * Activity REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class ActivityController implements ActivityApi {

    /**
     * Activity service.
     */
    private final ActivityService service;

    public ActivityController(final ActivityService service) {
        super();
        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "ACTIVITY", action = Actions.CREATE)
    public ActivityResponseDto createActivity(@Valid final ActivityCreationDto activityCreationDto) {
        final Activity transaction;
        final Activity toCreate;

        toCreate = ActivityDtoMapper.toDomain(activityCreationDto);
        transaction = service.create(toCreate);

        return ActivityDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.DELETE)
    public ActivityResponseDto deleteActivity(final Long number) {
        final Activity transaction;

        transaction = service.delete(number);

        return ActivityDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.READ)
    public ActivityPageResponseDto getAllActivitys(@Min(1) @Valid final Integer page, @Min(1) @Valid final Integer size,
            @Valid final List<@Pattern(regexp = "^(description|title|date)\\|(asc|desc)$") String> sort,
            @Valid final Instant date, @Valid final Instant from, @Valid final Instant to) {
        final Pagination     pagination;
        final Sorting        sorting;
        final Page<Activity> transactions;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        transactions = service.getAll(pagination, sorting);

        return ActivityDtoMapper.toResponseDto(transactions);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.READ)
    public ActivityResponseDto getOneActivity(final Long number) {
        final Optional<Activity> transaction;

        transaction = service.getOne(number);

        return ActivityDtoMapper.toResponseDto(transaction);
    }

    @Override
    @RequireResourceAuthorization(resource = "TRANSACTION", action = Actions.UPDATE)
    public ActivityResponseDto updateActivity(final Long number, @Valid final ActivityUpdateDto activityUpdateDto) {
        final Activity transaction;
        final Activity updated;

        transaction = ActivityDtoMapper.toDomain(number, activityUpdateDto);
        updated = service.update(transaction);
        return ActivityDtoMapper.toResponseDto(updated);
    }

}
