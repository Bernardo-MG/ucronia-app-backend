
package com.bernardomg.association.calendar.game.adapter.outbound.rest.controller;

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

import com.bernardomg.association.calendar.game.usecase.service.ScheduledGameService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduledGameController - createScheduledGame")
class TestScheduledGameControllerValidationCreateScheduledGame {

    @InjectMocks
    private ScheduledGameController controller;

    private MockMvc                 mockMvc;

    @Mock
    private ScheduledGameService    service;

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
    @DisplayName("When creating an empty scheduled game, then it is rejected")
    void testCreateScheduledGame_Empty() throws Exception {
        // WHEN
        mockMvc.perform(post("/game").contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a scheduled game without gameType, then it is rejected")
    void testCreateScheduledGame_MissingType() throws Exception {
        // WHEN
        mockMvc.perform(post("/game").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "title":"Game",
                      "master":10,
                      "maxPlayers":5,
                      "start":"2025-08-01T00:00:00Z"
                    }
                    """))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
