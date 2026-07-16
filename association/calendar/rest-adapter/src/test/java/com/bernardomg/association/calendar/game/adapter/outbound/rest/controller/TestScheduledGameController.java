
package com.bernardomg.association.calendar.game.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

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

import com.bernardomg.association.calendar.game.test.configuration.factory.ScheduledGames;
import com.bernardomg.association.calendar.game.usecase.service.ScheduledGameService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScheduledGameController")
class TestScheduledGameController {

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
    @DisplayName("When creating a scheduled game with valid data, it is accepted")
    void testCreateScheduledGame() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(ScheduledGames.zeroRecurrence());

        requestBody = """
                {
                    "title": "Game",
                    "master": 10,
                    "maxPlayers": 5,
                    "start": "2025-08-01T00:00:00Z",
                    "gameType": "oneshot"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/game").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.title").exists());
    }

    @Test
    @DisplayName("When deleting a scheduled game, it is accepted")
    void testDeleteScheduledGame() throws Exception {
        // GIVEN
        given(service.delete(1L)).willReturn(ScheduledGames.zeroRecurrence());

        // WHEN + THEN
        mockMvc.perform(delete("/game/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When there are scheduled games, they are returned")
    void testGetAllScheduledGames() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any())).willReturn(
            new Page<>(List.of(ScheduledGames.zeroRecurrence()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/game").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the scheduled game exists, it is returned")
    void testGetOneScheduledGame() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(ScheduledGames.zeroRecurrence()));

        // WHEN + THEN
        mockMvc.perform(get("/game/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When updating a scheduled game with valid data, it is accepted")
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

        // WHEN + THEN
        mockMvc.perform(put("/game/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.title").exists());
    }

}
