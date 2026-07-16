
package com.bernardomg.association.library.author.test.test.adapter.outbound.rest.controller;

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

import com.bernardomg.association.library.author.adapter.outbound.rest.controller.AuthorController;
import com.bernardomg.association.library.author.usecase.service.AuthorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorController - Validation")
class TestAuthorControllerValidation {

    private MockMvc       mockMvc;

    @Mock
    private AuthorService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new AuthorController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating an empty author, it is rejected")
    void testCreateAuthor_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating an author with an empty name, it is rejected")
    void testCreateAuthor_EmptyName() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "name": ""
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating an author with an oversized name, it is rejected")
    void testCreateAuthor_OversizedName() throws Exception {
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
        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an empty author, it is rejected")
    void testUpdateAuthor_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/author/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an author with an empty name, it is rejected")
    void testUpdateAuthor_EmptyName() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "name": ""
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/author/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an author with an oversized name, it is rejected")
    void testUpdateAuthor_OversizedName() throws Exception {
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
        mockMvc.perform(put("/library/author/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
