
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
@DisplayName("DefaultCalendarTypeService - delete")
class TestCalendarTypeServiceDelete {

    @Mock
    private CalendarTypeRepository     calendarTypeRepository;

    @InjectMocks
    private DefaultCalendarTypeService service;

    @Test
    @DisplayName("When deleting an existing calendar type, then the deleted data is returned")
    void testDelete() {
        final CalendarType calendarType;

        // GIVEN
        given(calendarTypeRepository.findOne(CalendarTypeConstants.NUMBER))
            .willReturn(Optional.of(CalendarTypes.activity()));

        // WHEN
        calendarType = service.delete(CalendarTypeConstants.NUMBER);

        // THEN
        Assertions.assertThat(calendarType)
            .as("calendar type")
            .isEqualTo(CalendarTypes.activity());
    }

    @Test
    @DisplayName("When deleting a not existing calendar type, then an exception is thrown")
    void testDelete_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(calendarTypeRepository.findOne(CalendarTypeConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(CalendarTypeConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When deleting a not existing calendar type, then an exception is thrown")
            .isInstanceOf(MissingCalendarTypeException.class);
    }

}
