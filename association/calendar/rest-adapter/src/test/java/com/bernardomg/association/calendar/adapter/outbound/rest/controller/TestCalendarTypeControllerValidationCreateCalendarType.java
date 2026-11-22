
package com.bernardomg.association.calendar.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@DisplayName("CalendarTypeController - createCalendarType")
class TestCalendarTypeControllerValidationCreateCalendarType {

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
    @DisplayName("When creating an empty calendar type, then it is rejected")
    void testCreateCalendarType_Empty() throws Exception {
        // WHEN
        mockMvc.perform(post("/calendar-type").contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a calendar type without color, then it is rejected")
    void testCreateCalendarType_MissingColor() throws Exception {
        // WHEN
        mockMvc.perform(post("/calendar-type").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "name":"Evento"
                    }
                    """))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
