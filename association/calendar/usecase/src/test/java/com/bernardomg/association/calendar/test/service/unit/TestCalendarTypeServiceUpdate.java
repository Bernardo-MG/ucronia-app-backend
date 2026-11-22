
package com.bernardomg.association.calendar.test.service.unit;

import static org.mockito.BDDMockito.given;

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
@DisplayName("DefaultCalendarTypeService - update")
class TestCalendarTypeServiceUpdate {

    @Mock
    private CalendarTypeRepository     calendarTypeRepository;

    @InjectMocks
    private DefaultCalendarTypeService service;

    @Test
    @DisplayName("When updating an existing calendar type, then the updated data is returned")
    void testUpdate() {
        final CalendarType calendarType;
        final CalendarType toUpdate;

        // GIVEN
        toUpdate = CalendarTypes.nameChange();
        given(calendarTypeRepository.exists(CalendarTypeConstants.NUMBER)).willReturn(true);
        given(calendarTypeRepository.save(toUpdate)).willReturn(CalendarTypes.nameChange());

        // WHEN
        calendarType = service.update(toUpdate);

        // THEN
        Assertions.assertThat(calendarType)
            .as("calendar type")
            .isEqualTo(CalendarTypes.nameChange());
    }

    @Test
    @DisplayName("When updating a not existing calendar type, then an exception is thrown")
    void testUpdate_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(calendarTypeRepository.exists(CalendarTypeConstants.NUMBER)).willReturn(false);

        // WHEN
        action = () -> service.update(CalendarTypes.activity());

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When updating a not existing calendar type, then an exception is thrown")
            .isInstanceOf(MissingCalendarTypeException.class);
    }

}
