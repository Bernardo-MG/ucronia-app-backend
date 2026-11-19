
package com.bernardomg.association.calendar.game.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.ScheduledGameService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduledGameController - updateScheduledGame")
class TestScheduledGameControllerUpdateScheduledGame {

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
    @DisplayName("When updating a scheduled game with valid data, then it is accepted")
    void testUpdateScheduledGame() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(ScheduledGames.zeroRecurrence());

        requestBody = """
                {
                    "title": "Game Updated",
                    "master": 10,
                    "maxPlayers": 5,
                    "start": "2025-08-01T00:00:00Z",
                    "gameType": "oneshot"
                }
                """;

        // WHEN
        mockMvc.perform(put("/game/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.title").exists());
    }

}
