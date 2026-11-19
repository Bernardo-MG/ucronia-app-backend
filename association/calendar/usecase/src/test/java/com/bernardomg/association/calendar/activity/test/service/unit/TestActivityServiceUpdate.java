
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

import com.bernardomg.association.calendar.activity.domain.exception.MissingActivityException;
import com.bernardomg.association.calendar.activity.domain.model.Activity;
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.activity.usecase.service.DefaultActivityService;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultActivityService - update")
class TestActivityServiceUpdate {

    @Mock
    private ActivityRepository     activityRepository;

    @Mock
    private EventEmitter           eventEmitter;

    @InjectMocks
    private DefaultActivityService service;

    @Test
    @DisplayName("When the activity with a future date is provided, then it is persisted")
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
    @DisplayName("When there is a not existing entity, then an exception is thrown")
    void testUpdate_NotExisting_Exception() {
        final Activity         activity;
        final ThrowingCallable action;

        // GIVEN
        activity = Activities.singleDay();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(activity);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a not existing entity, then an exception is thrown")
            .isInstanceOf(MissingActivityException.class);
    }

    @Test
    @DisplayName("When there is a member with padded name, then the member is persisted")
    void testUpdate_Padded_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.singleDay();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(activity);

        // THEN
        verify(activityRepository).save(Activities.singleDay());
    }

    @Test
    @DisplayName("When there is a valid activity, then it is persisted")
    void testUpdate_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.singleDay();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(activity);

        // THEN
        verify(activityRepository).save(Activities.singleDay());
    }

    @Test
    @DisplayName("When there is a valid activity, then it is returned")
    void testUpdate_ReturnedData() {
        final Activity activity;
        final Activity updated;

        // GIVEN
        activity = Activities.singleDay();

        given(activityRepository.save(activity)).willReturn(activity);
        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        updated = service.update(activity);

        // THEN
        Assertions.assertThat(updated)
            .as("activity")
            .isEqualTo(Activities.singleDay());
    }

    @Test
    @DisplayName("When there is an activity which starts and ends in the same date, then it is persisted")
    void testUpdate_SameDate_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.sameDate();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        service.update(activity);

        // THEN
        verify(activityRepository).save(Activities.sameDate());
    }

    @Test
    @DisplayName("When there is an activity which starts after the end, then an exception is thrown")
    void testUpdate_StartsAfterEnd() {
        final ThrowingCallable action;
        final Activity         activity;

        // GIVEN
        activity = Activities.startsAfterEnd();

        given(activityRepository.exists(ActivityConstants.NUMBER)).willReturn(true);

        // WHEN
        action = () -> service.update(activity);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("invalid", "dates", activity.dates()));
    }

}
