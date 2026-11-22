
package com.bernardomg.association.calendar.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.calendar.usecase.service.CalendarTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("CalendarTypeController - updateCalendarType")
class TestCalendarTypeControllerValidationUpdateCalendarType {

    @InjectMocks
    private CalendarTypeController controller;

    private MockMvc                mockMvc;

    @Mock
    private CalendarTypeService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When updating a calendar type without name, then it is rejected")
    void testUpdateCalendarType_MissingName() throws Exception {
        // WHEN
        mockMvc.perform(put("/calendar-type/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "color":"#FFA500"
                    }
                    """))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
