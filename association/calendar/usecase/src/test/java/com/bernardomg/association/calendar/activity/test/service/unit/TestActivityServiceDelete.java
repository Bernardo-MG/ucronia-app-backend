
package com.bernardomg.association.calendar.activity.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.association.calendar.activity.domain.repository.ActivityRepository;
import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.activity.test.configuration.factory.ActivityConstants;
import com.bernardomg.association.calendar.activity.usecase.service.DefaultActivityService;
import com.bernardomg.event.emitter.EventEmitter;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultActivityService - delete")
class TestActivityServiceDelete {

    @Mock
    private ActivityRepository     activityRepository;

    @Mock
    private EventEmitter           eventEmitter;

    @InjectMocks
    private DefaultActivityService service;

    @Test
    @DisplayName("When the activity doesn't exist, then an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(activityRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(ActivityConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the activity doesn't exist, then an exception is thrown")
            .isInstanceOf(MissingActivityException.class);
    }

    @Test
    @DisplayName("When deleting, then the repository is called")
    void testDelete_RemovesEntity() {
        // GIVEN
        given(activityRepository.findOne(ActivityConstants.NUMBER)).willReturn(Optional.of(Activities.singleDay()));

        // WHEN
        service.delete(ActivityConstants.NUMBER);

        // THEN
        verify(activityRepository).delete(ActivityConstants.NUMBER);
    }

}
