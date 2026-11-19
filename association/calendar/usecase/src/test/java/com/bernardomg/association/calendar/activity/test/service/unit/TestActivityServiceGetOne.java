
package com.bernardomg.association.calendar.activity.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

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

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultActivityService - getOne")
class TestActivityServiceGetOne {

    @Mock
    private ActivityRepository     activityRepository;

    @Mock
    private EventEmitter           eventEmitter;

    @InjectMocks
    private DefaultActivityService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testFindOne() {
        final Optional<Activity> activity;

        // GIVEN
        given(activityRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(Activities.singleDay()));

        // WHEN
        activity = service.getOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThat(activity)
            .as("activity")
            .contains(Activities.singleDay());
    }

    @Test
    @DisplayName("When there is no data, then an exception is thrown")
    void testFindOne_NoData() {
        final ThrowingCallable action;

        // GIVEN
        given(activityRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no data, then an exception is thrown")
            .isInstanceOf(MissingActivityException.class);
    }

}
