
package com.bernardomg.association.calendar.game.adapter.outbound.rest.controller;

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

import com.bernardomg.association.calendar.game.usecase.service.ScheduledGameService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduledGameController - updateScheduledGame")
class TestScheduledGameControllerValidationUpdateScheduledGame {

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
    @DisplayName("When updating a scheduled game with zero maxPlayers, then it is rejected")
    void testUpdateScheduledGame_InvalidPlayers() throws Exception {
        // WHEN
        mockMvc.perform(put("/game/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "title":"Game",
                      "master":10,
                      "maxPlayers":0,
                      "start":"2025-08-01T00:00:00Z",
                      "gameType":"oneshot"
                    }
                    """))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
