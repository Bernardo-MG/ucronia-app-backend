
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
import com.bernardomg.association.calendar.domain.event.ActivityPublishedEvent;
import com.bernardomg.association.calendar.domain.event.CalendarEventFactory;
import com.bernardomg.event.emitter.EventEmitter;
import com.bernardomg.validation.domain.model.FieldFailure;
import com.bernardomg.validation.test.assertion.ValidationAssertions;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultActivityService - create")
class TestActivityServiceCreate {

    @Mock
    private ActivityRepository     activityRepository;

    @Mock
    private EventEmitter           eventEmitter;

    @InjectMocks
    private DefaultActivityService service;

    @Test
    @DisplayName("When there is a valid activity, then an event is emitted")
    void testCreate_EmitsEvent() {
        final Activity               activity;
        final ActivityPublishedEvent event;

        // GIVEN
        activity = Activities.singleDay();
        event = CalendarEventFactory.activityPublished(activity.number());

        given(activityRepository.save(activity)).willReturn(activity);

        // WHEN
        service.create(activity);

        // THEN
        verify(eventEmitter)
            .emit(org.mockito.ArgumentMatchers.argThat(actual -> actual instanceof final ActivityPublishedEvent emitted
                    && emitted.getActivityNumber()
                        .equals(event.getActivityNumber())
                    && emitted.getSource()
                        .equals(event.getSource())));
    }

    @Test
    @DisplayName("When there is a future activity, then it is persisted")
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
    @DisplayName("When there is an activity with multiple days, then it is persisted")
    void testCreate_MultipleDays_PersistedData() {
        final Activity activity;

        // GIVEN
        activity = Activities.multipleDay();

        given(activityRepository.save(activity)).willReturn(activity);

        // WHEN
        service.create(activity);

        // THEN
        verify(activityRepository).save(activity);
    }

    @Test
    @DisplayName("When there is an activity with multiple days, then it is returned")
    void testCreate_MultipleDays_ReturnedData() {
        final Activity activity;
        final Activity created;

        // GIVEN
        activity = Activities.multipleDay();

        given(activityRepository.save(activity)).willReturn(activity);

        // WHEN
        created = service.create(activity);

        // THEN
        Assertions.assertThat(created)
            .as("activity")
            .isEqualTo(activity);
    }

    @Test
    @DisplayName("When there is a valid activity, then it is persisted")
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
    @DisplayName("When there is a valid activity, then it is returned")
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
    @DisplayName("When there is an activity which starts and ends in the same date, then it is persisted")
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
    @DisplayName("When there is an activity which starts after the end, then an exception is thrown")
    void testCreate_StartsAfterEnd() {
        final ThrowingCallable action;
        final Activity         activity;

        // GIVEN
        activity = Activities.startsAfterEnd();

        // WHEN
        action = () -> service.create(activity);

        // THEN
        ValidationAssertions.assertThatFieldFails(action, new FieldFailure("invalid", "dates", activity.dates()));
    }

}
