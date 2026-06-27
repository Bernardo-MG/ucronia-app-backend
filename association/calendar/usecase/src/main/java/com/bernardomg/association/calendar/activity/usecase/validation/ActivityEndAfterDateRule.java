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

package com.bernardomg.association.calendar.activity.usecase.validation;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.model.Activity.ActivityDate;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.validator.FieldRule;

/**
 * Checks the activity ends after the start.
 */
public final class ActivityEndAfterDateRule implements FieldRule<Activity> {

    /**
     * Logger for the class.
     */
    private static final Logger log = LoggerFactory.getLogger(ActivityEndAfterDateRule.class);

    public ActivityEndAfterDateRule() {
        super();
    }

    @Override
    public final Optional<FieldFailure> check(final Activity activity) {
        final Optional<FieldFailure> failure;
        final FieldFailure           fieldFailure;

        // TODO: return which date has failed
        if (activity.dates()
            .stream()
            .allMatch(this::isValid)) {
            failure = Optional.empty();
        } else {
            log.error("A date ends before starting: {}", activity.dates());
            fieldFailure = new FieldFailure("invalid", "dates", activity.dates());
            failure = Optional.of(fieldFailure);
        }

        return failure;
    }

    private final boolean isValid(final ActivityDate date) {
        return date.end()
            .isAfter(date.start())
                || date.start()
                    .equals(date.end());
    }

}
