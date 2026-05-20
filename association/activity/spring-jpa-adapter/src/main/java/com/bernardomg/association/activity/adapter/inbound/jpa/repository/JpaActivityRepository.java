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

package com.bernardomg.association.activity.adapter.inbound.jpa.repository;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.bernardomg.association.activity.adapter.inbound.jpa.model.ActivityEntity;
import com.bernardomg.association.activity.adapter.inbound.jpa.model.ActivityEntityMapper;
import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.association.activity.domain.repository.ActivityRepository;
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
    private static final Logger            log = LoggerFactory.getLogger(JpaActivityRepository.class);

    private final ActivitySpringRepository activitySpringRepository;

    public JpaActivityRepository(final ActivitySpringRepository activityRepo) {
        super();

        activitySpringRepository = Objects.requireNonNull(activityRepo);
    }

    @Override
    public final void delete(final long number) {
        final Optional<ActivityEntity> activity;

        log.debug("Deleting activity {}", number);

        activity = activitySpringRepository.findByNumber(number);
        if (activity.isPresent()) {
            activitySpringRepository.deleteById(activity.get()
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

        exists = activitySpringRepository.existsByNumber(number);

        log.debug("Activity {} exists: {}", number, exists);

        return exists;
    }

    @Override
    public final Page<Activity> findAll(final Pagination pagination, final Sorting sorting) {
        final org.springframework.data.domain.Page<ActivityEntity> page;
        final org.springframework.data.domain.Page<Activity>       read;
        final Pageable                                             pageable;

        log.debug("Finding activities with pagination {} and sorting {}", pagination, sorting);

        pageable = SpringPagination.toPageable(pagination, sorting);
        page = activitySpringRepository.findAll(pageable);

        read = page.map(ActivityEntityMapper::toDomain);

        log.debug("Found activities {}", read);

        return SpringPagination.toPage(read);
    }

    @Override
    public final long findNextNumber() {
        final long number;

        log.debug("Finding next number for the activities");

        number = activitySpringRepository.findNextNumber();

        log.debug("Found number {}", number);

        return number;
    }

    @Override
    public final Optional<Activity> findOne(final Long number) {
        final Optional<Activity> activity;

        log.debug("Finding activity with number {}", number);

        activity = activitySpringRepository.findByNumber(number)
            .map(ActivityEntityMapper::toDomain);

        log.debug("Found activity with number {}: {}", number, activity);

        return activity;
    }

    @Override
    public final Activity save(final Activity activity) {
        final Optional<ActivityEntity> existing;
        final ActivityEntity           entity;
        final ActivityEntity           created;
        final Activity                 saved;

        log.debug("Saving activity {}", activity);

        entity = ActivityEntityMapper.toEntity(activity);

        existing = activitySpringRepository.findByNumber(activity.number());
        if (existing.isPresent()) {
            entity.setId(existing.get()
                .getId());
        }

        created = activitySpringRepository.save(entity);
        saved = ActivityEntityMapper.toDomain(created);

        log.debug("Saved activity {}", saved);

        return saved;
    }

}
