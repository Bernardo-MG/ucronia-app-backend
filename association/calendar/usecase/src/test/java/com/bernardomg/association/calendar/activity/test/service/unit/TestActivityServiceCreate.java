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

package com.bernardomg.association.calendar.activity.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.activity.usecase.service.DefaultActivityService;
import com.bernardomg.association.calendar.domain.event.CalendarInfoPublishedEvent;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("Activity service - create")
class TestActivityServiceCreate {

    @Mock
    private ActivityRepository     activityRepository;

    @Mock
    private EventEmitter           eventEmitter;

    @InjectMocks
    private DefaultActivityService service;

    @Test
    @DisplayName("With a valid activity, an event is emitted")
    void testCreate_EmitsEvent() {
        final Activity                   activity;
        final CalendarInfoPublishedEvent event;

        // GIVEN
        activity = Activities.singleDay();
        event = new CalendarInfoPublishedEvent(null, activity.number());

        given(activityRepository.save(activity)).willReturn(activity);

        // WHEN
        service.create(activity);

        // THEN
        verify(eventEmitter).emit(event);
    }

    @Test
    @DisplayName("With a future activity, it is persisted")
    void testCreate_Future_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.future();

        given(activityRepository.save(activity)).willReturn(activity);

        // WHEN
        service.create(activity);

        // THEN
        verify(activityRepository).save(activity);
    }

    @Test
    @DisplayName("With a valid activity, it is persisted")
    void testCreate_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.singleDay();

        given(activityRepository.save(activity)).willReturn(activity);

        // WHEN
        service.create(activity);

        // THEN
        verify(activityRepository).save(activity);
    }

    @Test
    @DisplayName("With a valid activity, it is returned")
    void testCreate_ReturnedData() {
        final Activity activity;
        final Activity created;

        // GIVEN
        activity = Activities.singleDay();

        given(activityRepository.save(activity)).willReturn(activity);

        // WHEN
        created = service.create(activity);

        // THEN
        Assertions.assertThat(created)
            .as("activity")
            .isEqualTo(Activities.singleDay());
    }

    @Test
    @DisplayName("With an activity which starts and ends in the same date, it is persisted")
    void testCreate_SameDate_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.sameDate();

        given(activityRepository.save(activity)).willReturn(activity);

        // WHEN
        service.create(activity);

        // THEN
        verify(activityRepository).save(activity);
    }

    @Test
    @DisplayName("With an activity which starts after the end, an exception is thrown")
    void testCreate_StartsAfterEnd() {
        final ThrowingCallable execution;
        final Activity         activity;

        // GIVEN
        activity = Activities.startsAfterEnd();

        // WHEN
        execution = () -> service.create(activity);

        // THEN
        ValidationAssertions.assertThatFieldFails(execution, new FieldFailure("invalid", "dates", activity.dates()));
    }

}
