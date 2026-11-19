
package com.bernardomg.association.calendar.activity.adapter.outbound.rest.controller;

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

import com.bernardomg.association.calendar.activity.usecase.service.ActivityService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityController - createActivity")
class TestActivityControllerValidationCreateActivity {

    @InjectMocks
    private ActivityController controller;

    private MockMvc            mockMvc;

    @Mock
    private ActivityService    service;

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
    @DisplayName("When creating an empty activity, then it is rejected")
    void testCreateActivity_Empty() throws Exception {
        // WHEN
        mockMvc.perform(post("/activity").contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating an activity without dates, then it is rejected")
    void testCreateActivity_MissingDates() throws Exception {
        // WHEN
        mockMvc.perform(post("/activity").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "title":"Activity"
                    }
                    """))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
