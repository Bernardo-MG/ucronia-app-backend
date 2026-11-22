
package com.bernardomg.association.calendar.test.service.unit;

import static org.mockito.BDDMockito.given;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.calendar.domain.model.CalendarType;
import com.bernardomg.association.calendar.domain.repository.CalendarTypeRepository;
import com.bernardomg.association.calendar.test.configuration.factory.CalendarTypes;
import com.bernardomg.association.calendar.usecase.service.DefaultCalendarTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultCalendarTypeService - create")
class TestCalendarTypeServiceCreate {

    @Mock
    private CalendarTypeRepository     calendarTypeRepository;

    @InjectMocks
    private DefaultCalendarTypeService service;

    @Test
    @DisplayName("When creating a calendar type, then the persisted data is returned")
    void testCreate() {
        final CalendarType calendarType;
        final CalendarType toCreate;

        // GIVEN
        toCreate = CalendarTypes.activity();
        given(calendarTypeRepository.save(toCreate)).willReturn(CalendarTypes.activity());

        // WHEN
        calendarType = service.create(toCreate);

        // THEN
        Assertions.assertThat(calendarType)
            .as("calendar type")
            .isEqualTo(CalendarTypes.activity());
    }

}
