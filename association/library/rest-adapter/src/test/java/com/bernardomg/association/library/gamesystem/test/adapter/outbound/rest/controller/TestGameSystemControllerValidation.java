
package com.bernardomg.association.library.gamesystem.test.adapter.outbound.rest.controller;

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

import com.bernardomg.association.library.gamesystem.adapter.outbound.rest.controller.GameSystemController;
import com.bernardomg.association.library.gamesystem.usecase.service.GameSystemService;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameSystemController - Validation")
class TestGameSystemControllerValidation {

    private MockMvc           mockMvc;

    @Mock
    private GameSystemService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new GameSystemController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating an empty game system, it is rejected")
    void testCreateGameSystem_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/gameSystem").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a game system with an empty name, it is rejected")
    void testCreateGameSystem_EmptyName() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "name": ""
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/gameSystem").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a game system with an oversized name, it is rejected")
    void testCreateGameSystem_OversizedName() throws Exception {
        final String longName;
        final String requestBody;

        // GIVEN
        longName = "x".repeat(101);
        requestBody = String.format("""
                {
                    "name": "%s"
                }
                """, longName);

        // WHEN + THEN
        mockMvc.perform(post("/library/gameSystem").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an empty game system, it is rejected")
    void testUpdateGameSystem_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/gameSystem/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a game system with an empty name, it is rejected")
    void testUpdateGameSystem_EmptyName() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "name": ""
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/gameSystem/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a game system with an oversized name, it is rejected")
    void testUpdateGameSystem_OversizedName() throws Exception {
        final String longName;
        final String requestBody;

        // GIVEN
        longName = "x".repeat(101);
        requestBody = String.format("""
                {
                    "name": "%s"
                }
                """, longName);

        // WHEN + THEN
        mockMvc.perform(put("/library/gameSystem/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
