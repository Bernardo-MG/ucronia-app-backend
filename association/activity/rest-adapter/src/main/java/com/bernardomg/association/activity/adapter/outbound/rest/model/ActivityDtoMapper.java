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

package com.bernardomg.association.activity.adapter.outbound.rest.model;

import java.util.List;
import java.util.Optional;

import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityCreationDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityDateDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityPageResponseDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityResponseDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.ActivityUpdateDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.PropertyDto;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.PropertyDto.DirectionEnum;
import com.bernardomg.association.activity.adapter.outbound.rest.dto.SortingDto;
import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.association.activity.domain.model.Activity.ActivityDate;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting.Direction;
import com.bernardomg.pagination.domain.Sorting.Property;

public final class ActivityDtoMapper {

    public static final Activity toDomain(final ActivityCreationDto creation) {
        final List<ActivityDate> dates;

        dates = creation.getDates()
            .stream()
            .map(ActivityDtoMapper::toDomain)
            .toList();
        return new Activity(-1, creation.getTitle(), creation.getDescription(), creation.getLocation(),
            creation.getImage(), dates);
    }

    public static final Activity toDomain(final Long number, final ActivityUpdateDto change) {
        final List<ActivityDate> dates;

        dates = change.getDates()
            .stream()
            .map(ActivityDtoMapper::toDomain)
            .toList();
        return new Activity(number, change.getTitle(), change.getDescription(), change.getLocation(), change.getImage(),
            dates);
    }

    public static final ActivityResponseDto toResponseDto(final Activity activity) {
        return new ActivityResponseDto().content(ActivityDtoMapper.toDto(activity));
    }

    public static final ActivityResponseDto toResponseDto(final Optional<Activity> activity) {
        return new ActivityResponseDto().content(activity.map(ActivityDtoMapper::toDto)
            .orElse(null));
    }

    public static final ActivityPageResponseDto toResponseDto(final Page<Activity> page) {
        final SortingDto sortingResponse;

        sortingResponse = new SortingDto().properties(page.sort()
            .properties()
            .stream()
            .map(ActivityDtoMapper::toDto)
            .toList());
        return new ActivityPageResponseDto().content(page.content()
            .stream()
            .map(ActivityDtoMapper::toDto)
            .toList())
            .size(page.size())
            .page(page.page())
            .totalElements(page.totalElements())
            .totalPages(page.totalPages())
            .elementsInPage(page.elementsInPage())
            .first(page.first())
            .last(page.last())
            .sort(sortingResponse);
    }

    private static final ActivityDate toDomain(final ActivityDateDto date) {
        return new ActivityDate(date.getStart(), date.getEnd());
    }

    private static final ActivityDto toDto(final Activity activity) {
        final List<ActivityDateDto> dates;

        dates = activity.dates()
            .stream()
            .map(ActivityDtoMapper::toDto)
            .toList();
        return new ActivityDto().number(activity.number())
            .title(activity.title())
            .description(activity.description())
            .image(activity.image())
            .dates(dates);
    }

    private static final ActivityDateDto toDto(final ActivityDate date) {
        return new ActivityDateDto().start(date.start())
            .end(date.end());
    }

    private static final PropertyDto toDto(final Property property) {
        final DirectionEnum direction;

        if (property.direction() == Direction.ASC) {
            direction = DirectionEnum.ASC;
        } else {
            direction = DirectionEnum.DESC;
        }
        return new PropertyDto().name(property.name())
            .direction(direction);
    }

    private ActivityDtoMapper() {
        super();
    }

}
