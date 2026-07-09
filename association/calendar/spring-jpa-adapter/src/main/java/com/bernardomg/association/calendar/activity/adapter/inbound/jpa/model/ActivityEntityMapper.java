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

package com.bernardomg.association.calendar.activity.adapter.inbound.jpa.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.model.Activity.ActivityDate;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarDateEntity;
import com.bernardomg.association.calendar.adapter.inbound.jpa.model.CalendarInfoEntity;

/**
 * Activity repository mapper.
 */
public final class ActivityEntityMapper {

    public static final Activity toDomain(final CalendarInfoEntity entity) {
        final List<ActivityDate> dates;

        dates = entity.getCalendarDates()
            .stream()
            .map(ActivityEntityMapper::toDomain)
            .toList();
        return new Activity(entity.getNumber(), entity.getTitle(), entity.getDescription(), entity.getLocation(),
            entity.getImage(), dates);
    }

    public static final CalendarInfoEntity toEntity(final Activity activity) {
        final CalendarInfoEntity      entity;
        final Set<CalendarDateEntity> dates;

        entity = new CalendarInfoEntity();
        entity.setNumber(activity.number());
        entity.setTitle(activity.title());
        entity.setDescription(activity.description());
        entity.setLocation(activity.location());
        entity.setImage(activity.image());

        dates = activity.dates()
            .stream()
            .map(ActivityEntityMapper::toEntity)
            .collect(Collectors.toSet());
        entity.setCalendarDates(dates);

        return entity;
    }

    private static final ActivityDate toDomain(final CalendarDateEntity entity) {
        return new ActivityDate(entity.getStart(), entity.getEnd());
    }

    private static final CalendarDateEntity toEntity(final ActivityDate date) {
        final CalendarDateEntity entity;

        entity = new CalendarDateEntity();
        entity.setStart(date.start());
        entity.setEnd(date.end());

        return entity;
    }

    private ActivityEntityMapper() {
        super();
    }

}
