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

package com.bernardomg.association.activity.usecase.service;

import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.activity.domain.exception.MissingActivityException;
import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.association.activity.domain.repository.ActivityRepository;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.validation.validator.FieldRuleValidator;
import com.bernardomg.validation.validator.Validator;

import jakarta.transaction.Transactional;

/**
 * Default implementation of the activity service.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Transactional
public final class DefaultActivityService implements ActivityService {

    /**
     * Logger for the class.
     */
    private static final Logger       log = LoggerFactory.getLogger(DefaultActivityService.class);

    private final ActivityRepository  activityRepository;

    private final Validator<Activity> validatorCreate;

    private final Validator<Activity> validatorUpdate;

    public DefaultActivityService(final ActivityRepository activityRepo) {
        super();

        activityRepository = Objects.requireNonNull(activityRepo);

        validatorCreate = new FieldRuleValidator<>();
        validatorUpdate = new FieldRuleValidator<>();
    }

    @Override
    public final Activity create(final Activity activity) {
        final Long     number;
        final Activity toCreate;
        final Activity saved;

        log.debug("Creating activity {}", activity);

        validatorCreate.validate(activity);

        // TODO: set inside the repository
        number = activityRepository.findNextNumber();

        toCreate = new Activity(number, activity.date(), activity.title(), activity.description());

        saved = activityRepository.save(toCreate);

        log.debug("Created activity {}", saved);

        return saved;
    }

    @Override
    public final Activity delete(final long number) {
        final Activity activity;

        log.debug("Deleting activity {}", number);

        activity = activityRepository.findOne(number)
            .orElseThrow(() -> {
                log.error("Missing activity {}", number);
                throw new MissingActivityException(number);
            });

        // TODO: Check this deletes on cascade
        activityRepository.delete(number);

        log.debug("Deleted activity {}", number);

        return activity;
    }

    @Override
    public final Page<Activity> getAll(final Pagination pagination, final Sorting sorting) {
        final Page<Activity> activitys;

        log.info("Getting all activitys with pagination {} and sorting {}", pagination, sorting);

        activitys = activityRepository.findAll(pagination, sorting);

        log.debug("Got all activitys with pagination {} and sorting {}: {}", pagination, sorting, activitys);

        return activitys;
    }

    @Override
    public final Optional<Activity> getOne(final long number) {
        final Optional<Activity> activity;

        log.debug("Reading activity with number {}", number);

        activity = activityRepository.findOne(number);
        if (activity.isEmpty()) {
            log.error("Missing activity {}", number);
            throw new MissingActivityException(number);
        }

        log.debug("Read activity with number {}: {}", number, activity);

        return activity;
    }

    @Override
    public final Activity update(final Activity activity) {
        final boolean  exists;
        final Activity toUpdate;
        final Activity updated;

        log.debug("Updating activity with number {} using data {}", activity.number(), activity);

        exists = activityRepository.exists(activity.number());
        if (!exists) {
            log.error("Missing activity {}", activity.number());
            throw new MissingActivityException(activity.number());
        }

        toUpdate = new Activity(activity.number(), activity.date(), activity.title(), activity.description());

        validatorUpdate.validate(toUpdate);

        updated = activityRepository.save(toUpdate);

        log.debug("Updated activity with number {}: {}", activity.number(), updated);

        return updated;
    }

}
