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

package com.bernardomg.activity.test.service.unit;

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

import com.bernardomg.association.activity.domain.exception.MissingActivityException;
import com.bernardomg.association.activity.domain.model.Activity;
import com.bernardomg.association.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.activity.test.configuration.factory.Activities;
import com.bernardomg.association.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.activity.usecase.service.DefaultActivityService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Activity service - update")
class TestActivityServiceUpdate {

    @Mock
    private ActivityRepository     activityRepository;

    @InjectMocks
    private DefaultActivityService service;

    @Test
    @DisplayName("With a member with padded name, the member is persisted")
    void testCreate_Padded_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.valid();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(activity);

        // THEN
        verify(activityRepository).save(Activities.valid());
    }

    @Test
    @DisplayName("With a valid activity, it is persisted")
    void testCreate_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.valid();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(activity);

        // THEN
        verify(activityRepository).save(Activities.valid());
    }

    @Test
    @DisplayName("With a valid activity, it is returned")
    void testCreate_ReturnedData() {
        final Activity activity;
        final Activity updated;

        // GIVEN
        activity = Activities.valid();

        given(activityRepository.save(activity)).willReturn(activity);
        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        updated = service.update(activity);

        // THEN
        Assertions.assertThat(updated)
            .as("activity")
            .isEqualTo(Activities.valid());
    }

    @Test
    @DisplayName("With the activity with a future date, it is persisted")
    void testUpdate_Future_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.future();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(activity);

        // THEN
        verify(activityRepository).save(Activities.future());
    }

    @Test
    @DisplayName("With a not existing entity, an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Activity         activity;
        final ThrowingCallable execution;

        // GIVEN
        activity = Activities.valid();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(false);

        // WHEN
        execution = () -> service.update(activity);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingActivityException.class);
    }

}
