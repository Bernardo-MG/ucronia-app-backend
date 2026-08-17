
package com.bernardomg.association.library.gamesystem.test.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
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
import java.util.Map;
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

import com.bernardomg.association.library.gamesystem.adapter.outbound.rest.controller.GameSystemController;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystemConstants;
import com.bernardomg.association.library.gamesystem.test.configuration.factory.GameSystems;
import com.bernardomg.association.library.gamesystem.usecase.service.GameSystemService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameSystemController")
class TestGameSystemController {

    private MockMvc           mockMvc;

    @Mock
    private GameSystemService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setValidationPropertyMap(Map.of("hibernate.validator.allow_parameter_constraint_override", "true"));
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new GameSystemController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a game system with valid data, it is accepted")
    void testCreateGameSystem() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(GameSystems.valid());

        requestBody = """
                {
                    "name": "Game system"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/gameSystem").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo(GameSystemConstants.NAME)))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When the game system is deleted, it is accepted")
    void testDeleteGameSystem() throws Exception {
        // GIVEN
        given(service.delete(1L)).willReturn(GameSystems.valid());

        // WHEN + THEN
        mockMvc.perform(delete("/library/gameSystem/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When there are game systems, they are returned")
    void testGetAllGameSystems() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(GameSystems.valid()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/library/gameSystem").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the game system exists, it is returned")
    void testGetGameSystemById() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(GameSystems.valid()));

        // WHEN + THEN
        mockMvc.perform(get("/library/gameSystem/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When updating a valid game system, it is accepted")
    void testUpdateGameSystem() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(GameSystems.nameChange());

        requestBody = """
                {
                    "name": "Game system 123"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/gameSystem/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo(GameSystemConstants.CHANGED_NAME)));
    }

}
