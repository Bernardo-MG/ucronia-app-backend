
package com.bernardomg.association.calendar.game.adapter.outbound.rest.controller;

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

import com.bernardomg.association.calendar.game.usecase.service.ScheduledGameService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduledGameController - Validation")
class TestScheduledGameControllerValidation {

    private MockMvc              mockMvc;

    @Mock
    private ScheduledGameService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new ScheduledGameController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating an empty scheduled game, it is rejected")
    void testCreateScheduledGame_Empty() throws Exception {
        mockMvc.perform(post("/game").contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a scheduled game without gameType, it is rejected")
    void testCreateScheduledGame_MissingType() throws Exception {
        mockMvc.perform(post("/game").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "title":"Game",
                      "master":10,
                      "maxPlayers":5,
                      "start":"2025-08-01T00:00:00Z"
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a scheduled game with zero maxPlayers, it is rejected")
    void testUpdateScheduledGame_InvalidPlayers() throws Exception {
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
            .andExpect(status().isBadRequest());
    }

}
