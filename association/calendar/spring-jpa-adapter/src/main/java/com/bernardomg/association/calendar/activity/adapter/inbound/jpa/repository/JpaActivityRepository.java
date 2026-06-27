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

package com.bernardomg.association.calendar.activity.adapter.inbound.jpa.repository;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.calendar.activity.adapter.inbound.jpa.model.ActivityEntityConstants;
import com.bernardomg.association.calendar.activity.adapter.inbound.jpa.model.ActivityEntityMapper;
import com.bernardomg.association.calendar.activity.adapter.inbound.jpa.model.CalendarDateEntity;
import com.bernardomg.association.calendar.activity.adapter.inbound.jpa.model.CalendarInfoEntity;
import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.pagination.springframework.SpringPagination;

import jakarta.transaction.Transactional;

@Transactional
public final class JpaActivityRepository implements ActivityRepository {

    /**
     * Logger for the class.
     */
    private static final Logger                log = LoggerFactory.getLogger(JpaActivityRepository.class);

    private final CalendarDateSpringRepository calendarDateSpringRepository;

    private final CalendarInfoSpringRepository calendarInfoSpringRepository;

    public JpaActivityRepository(final CalendarInfoSpringRepository calendarInfoSpringRepo,
            final CalendarDateSpringRepository calendarDateSpringRepo) {
        super();

        calendarInfoSpringRepository = Objects.requireNonNull(calendarInfoSpringRepo);
        calendarDateSpringRepository = Objects.requireNonNull(calendarDateSpringRepo);
    }

    @Override
    public final void delete(final long number) {
        final Optional<CalendarInfoEntity> calendar;

        log.debug("Deleting activity {}", number);

        // TODO: check the date is deleted
        calendar = calendarInfoSpringRepository.findByNumber(number);
        if (calendar.isPresent()) {
            calendarInfoSpringRepository.deleteById(calendar.get()
                .getId());

            log.debug("Deleted activity {}", number);
        } else {
            // TODO: shouldn't throw an exception?
            log.debug("Couldn't delete activity {} as it doesn't exist", number);
        }
    }

    @Override
    public final boolean exists(final long number) {
        final boolean exists;

        log.debug("Checking if activity {} exists", number);

        exists = calendarInfoSpringRepository.existsByNumber(number);

        log.debug("Activity {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Activity> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<CalendarInfoEntity> page;
        final org.springframework.data.domain.Page<Activity>           read;
        final Pageable                                                 pageable;

        log.debug("Finding activities with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        if (sorting.properties()
            .isEmpty()) {
            page = calendarInfoSpringRepository.findAllOrderByFirstDate(pageable);
        } else {
            page = calendarInfoSpringRepository.findAll(pageable);
        }

        read = page.map(ActivityEntityMapper::toDomain);

        log.debug("Found activities {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final Optional<Activity> findOne(final Long number) {
        final Optional<Activity> activity;

        log.debug("Finding activity with number {}", number);

        activity = calendarInfoSpringRepository.findByNumber(number)
            .map(ActivityEntityMapper::toDomain);

        log.debug("Found activity with number {}: {}", number, activity);

        return activity;
    }

    @Override
    public final Activity save(final Activity activity) {
        final Optional<CalendarInfoEntity> existing;
        final CalendarInfoEntity           entity;
        final List<CalendarDateEntity>     createdDates;
        final CalendarInfoEntity           created;
        final Activity                     saved;
        final Long                         number;

        log.debug("Saving activity {}", activity);

        entity = ActivityEntityMapper.toEntity(activity);

        existing = calendarInfoSpringRepository.findByNumber(activity.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        } else {
            number = calendarInfoSpringRepository.findNextNumber();
            entity.setNumber(number);
        }

        // TODO: shouldn't be needed
        createdDates = calendarDateSpringRepository.saveAll(entity.getCalendarDates());

        entity.setCalendarDates(Set.copyOf(createdDates));

        setType(entity);

        created = calendarInfoSpringRepository.save(entity);
        saved = ActivityEntityMapper.toDomain(created);

        log.debug("Saved activity {}", saved);

        return saved;
    }

    private final void setType(final CalendarInfoEntity entity) {
        if (entity.getTypes() == null) {
            entity.setTypes(new HashSet<>(List.of(ActivityEntityConstants.PROFILE_TYPE)));
        } else {
            entity.getTypes()
                .add(ActivityEntityConstants.PROFILE_TYPE);
        }
    }

}
