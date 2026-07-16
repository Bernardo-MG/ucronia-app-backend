
package com.bernardomg.association.calendar.activity.adapter.outbound.rest.controller;

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

import com.bernardomg.association.calendar.activity.usecase.service.ActivityService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityController - Validation")
class TestActivityControllerValidation {

    private MockMvc         mockMvc;

    @Mock
    private ActivityService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new ActivityController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating an empty activity, it is rejected")
    void testCreateActivity_Empty() throws Exception {
        mockMvc.perform(post("/activity").contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating an activity without dates, it is rejected")
    void testCreateActivity_MissingDates() throws Exception {
        mockMvc.perform(post("/activity").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "title":"Activity"
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an activity without title, it is rejected")
    void testUpdateActivityMissingTitle() throws Exception {
        mockMvc.perform(put("/activity/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "dates":[
                        {
                          "start":"2025-08-01T00:00:00Z",
                          "end":"2025-08-01T02:00:00Z"
                        }
                      ]
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

}
