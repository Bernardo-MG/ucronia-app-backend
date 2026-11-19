
package com.bernardomg.association.calendar.test.service.unit;

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

import com.bernardomg.association.calendar.domain.exception.MissingCalendarTypeException;
import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypeConstants;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypes;
import com.bernardomg.association.calendar.usecase.service.DefaultCalendarTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultCalendarTypeService - getOne")
class TestCalendarTypeServiceGetOne {

    @Mock
    private CalendarTypeRepository     calendarTypeRepository;

    @InjectMocks
    private DefaultCalendarTypeService service;

    @Test
    @DisplayName("When there is data, then it is returned")
    void testGetOne() {
        final Optional<CalendarType> calendarType;

        // GIVEN
        given(calendarTypeRepository.findOne(CalendarTypeConstants.NUMBER))
            .willReturn(Optional.of(CalendarTypes.activity()));

        // WHEN
        calendarType = service.getOne(CalendarTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(calendarType)
            .as("calendar type")
            .contains(CalendarTypes.activity());
    }

    @Test
    @DisplayName("When there is no data, then an exception is thrown")
    void testGetOne_NoData() {
        final ThrowingCallable action;

        // GIVEN
        given(calendarTypeRepository.findOne(CalendarTypeConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(CalendarTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is no data, then an exception is thrown")
            .isInstanceOf(MissingCalendarTypeException.class);
    }

}
