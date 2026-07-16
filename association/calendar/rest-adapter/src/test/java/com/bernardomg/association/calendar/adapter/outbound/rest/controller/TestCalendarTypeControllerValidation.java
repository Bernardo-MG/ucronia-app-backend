
package com.bernardomg.association.calendar.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.calendar.usecase.service.CalendarTypeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("CalendarTypeController - Validation")
class TestCalendarTypeControllerValidation {

    private MockMvc             mockMvc;

    @Mock
    private CalendarTypeService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new CalendarTypeController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating an empty calendar type, it is rejected")
    void testCreateCalendarType_Empty() throws Exception {
        mockMvc.perform(post("/calendar-type").contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a calendar type without color, it is rejected")
    void testCreateCalendarType_MissingColor() throws Exception {
        mockMvc.perform(post("/calendar-type").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "name":"Evento"
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a calendar type without name, it is rejected")
    void testUpdateCalendarType_MissingName() throws Exception {
        mockMvc.perform(put("/calendar-type/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "color":"#FFA500"
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

}
