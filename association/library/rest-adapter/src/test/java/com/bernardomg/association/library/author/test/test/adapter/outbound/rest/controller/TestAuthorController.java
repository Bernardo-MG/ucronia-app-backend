
package com.bernardomg.association.library.author.test.test.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.bernardomg.association.library.author.adapter.outbound.rest.controller.AuthorController;
import com.bernardomg.association.library.author.domain.model.Author;
import com.bernardomg.association.library.author.usecase.service.AuthorService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorController")
class TestAuthorController {

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
    @DisplayName("When creating an author with an empty name, it is accepted")
    void testCreateAuthorWithEmptyName() throws Exception {
        final String requestBody;

        given(service.create(any())).willReturn(new Author(1L, ""));

        requestBody = """
                {
                    "name": ""
                }
                """;

        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("When creating an author with valid data, it is accepted")
    void testCreateAuthorWithValidData() throws Exception {
        final String requestBody;

        given(service.create(any())).willReturn(new Author(1L, "Gary Gigax"));

        requestBody = """
                {
                    "name": "Gary Gigax"
                }
                """;

        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo("Gary Gigax")))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("When the author is deleted, it is accepted")
    void testDeleteAuthor() throws Exception {
        given(service.delete(any())).willReturn(new Author(1L, "Gary Gigax"));

        mockMvc.perform(delete("/library/author/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists())
            .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("When the author exists, it is returned")
    void testGetAuthorById() throws Exception {
        given(service.getOne(1L)).willReturn(Optional.of(new Author(1L, "Gary Gigax")));

        mockMvc.perform(get("/library/author/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists())
            .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("When updating a valid author, it is accepted")
    void testUpdateAuthorWithValidData() throws Exception {
        final String requestBody;

        given(service.update(any())).willReturn(new Author(1L, "Updated Author Name"));

        requestBody = """
                {
                    "name": "Updated Author Name"
                }
                """;

        mockMvc.perform(put("/library/author/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo("Updated Author Name")));
    }

}
