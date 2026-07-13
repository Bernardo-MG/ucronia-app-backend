
package com.bernardomg.association.library.author.test.test.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @DisplayName("When creating an author with null name, it is rejected")
    void testCreateAuthorWithNullName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": null
                }
                """;

        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating an author without name, it is rejected")
    void testCreateAuthorWithoutName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                }
                """;

        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating an author with an oversized name, it is rejected")
    void testCreateAuthorWithOversizedName() throws Exception {
        final String longName;
        final String requestBody;

        longName = "x".repeat(101);
        requestBody = String.format("""
                {
                    "name": "%s"
                }
                """, longName);

        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying authors with size zero, it is rejected")
    void testGetAllAuthorsWithInvalidSizeZero() throws Exception {
        mockMvc.perform(get("/library/author").param("page", "0")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying authors with negative page, it is rejected")
    void testGetAllAuthorsWithNegativePage() throws Exception {
        mockMvc.perform(get("/library/author").param("page", "-1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an author without name, it is rejected")
    void testUpdateAuthorWithoutName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                }
                """;

        mockMvc.perform(put("/library/author/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an author with an oversized name, it is rejected")
    void testUpdateAuthorWithOversizedName() throws Exception {
        final String longName;
        final String requestBody;

        longName = "x".repeat(101);
        requestBody = String.format("""
                {
                    "name": "%s"
                }
                """, longName);

        mockMvc.perform(put("/library/author/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
