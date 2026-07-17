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

package com.bernardomg.association.calendar.adapter.outbound.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.calendar.adapter.outbound.rest.dto.CalendarTypeCreationDto;
import com.bernardomg.association.calendar.adapter.outbound.rest.dto.CalendarTypePageResponseDto;
import com.bernardomg.association.calendar.adapter.outbound.rest.dto.CalendarTypeResponseDto;
import com.bernardomg.association.calendar.adapter.outbound.rest.dto.CalendarTypeUpdateDto;
import com.bernardomg.association.calendar.adapter.outbound.rest.model.CalendarTypeDtoMapper;
import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.usecase.service.CalendarTypeService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.web.WebSorting;
import com.bernardomg.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.permission.domain.constant.Actions;

/**
 * Calendar type REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
public class CalendarTypeController implements CalendarTypeApi {

    /**
     * Calendar type service.
     */
    private final CalendarTypeService service;

    public CalendarTypeController(final CalendarTypeService service) {
        super();

        this.service = service;
    }

    @Override
    @RequireResourceAuthorization(resource = "CALENDAR_TYPE", action = Actions.CREATE)
    public CalendarTypeResponseDto createCalendarType(final CalendarTypeCreationDto calendarTypeCreationDto) {
        final CalendarType calendarType;
        final CalendarType toCreate;

        toCreate = CalendarTypeDtoMapper.toDomain(calendarTypeCreationDto);
        calendarType = service.create(toCreate);

        return CalendarTypeDtoMapper.toResponseDto(calendarType);
    }

    @Override
    @RequireResourceAuthorization(resource = "CALENDAR_TYPE", action = Actions.DELETE)
    public CalendarTypeResponseDto deleteCalendarType(final Long number) {
        final CalendarType calendarType;

        calendarType = service.delete(number);

        return CalendarTypeDtoMapper.toResponseDto(calendarType);
    }

    @Override
    @RequireResourceAuthorization(resource = "CALENDAR_TYPE", action = Actions.READ)
    public CalendarTypePageResponseDto getAllCalendarTypes(final Integer page, final Integer size,
            final List<String> sort) {
        final Pagination         pagination;
        final Sorting            sorting;
        final Page<CalendarType> calendarTypes;

        pagination = new Pagination(page, size);
        sorting = WebSorting.toSorting(sort);
        calendarTypes = service.getAll(pagination, sorting);

        return CalendarTypeDtoMapper.toResponseDto(calendarTypes);
    }

    @Override
    @RequireResourceAuthorization(resource = "CALENDAR_TYPE", action = Actions.READ)
    public CalendarTypeResponseDto getOneCalendarType(final Long number) {
        final Optional<CalendarType> calendarType;

        calendarType = service.getOne(number);

        return CalendarTypeDtoMapper.toResponseDto(calendarType);
    }

    @Override
    @RequireResourceAuthorization(resource = "CALENDAR_TYPE", action = Actions.UPDATE)
    public CalendarTypeResponseDto updateCalendarType(final Long number,
            final CalendarTypeUpdateDto calendarTypeUpdateDto) {
        final CalendarType calendarType;
        final CalendarType updated;

        calendarType = CalendarTypeDtoMapper.toDomain(number, calendarTypeUpdateDto);
        updated = service.update(calendarType);

        return CalendarTypeDtoMapper.toResponseDto(updated);
    }

}
