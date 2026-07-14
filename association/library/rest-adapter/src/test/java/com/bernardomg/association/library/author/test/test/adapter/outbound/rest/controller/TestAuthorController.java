
package com.bernardomg.association.library.author.test.test.adapter.outbound.rest.controller;

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
import com.bernardomg.association.library.author.test.configuration.factory.AuthorConstants;
import com.bernardomg.association.library.author.test.configuration.factory.Authors;
import com.bernardomg.association.library.author.usecase.service.AuthorService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

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
    @DisplayName("When creating an author with valid data, it is accepted")
    void testCreateAuthorWithValidData() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(Authors.valid());

        requestBody = """
                {
                    "name": "Author"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/author").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo(AuthorConstants.NAME)))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When the author is deleted, it is accepted")
    void testDeleteAuthor() throws Exception {
        // GIVEN
        given(service.delete(any())).willReturn(Authors.valid());

        // WHEN + THEN
        mockMvc.perform(delete("/library/author/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When there are authors, they are returned")
    void testGetAllAuthors() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(Authors.valid()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/library/author").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the author exists, it is returned")
    void testGetAuthorById() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(Authors.valid()));

        // WHEN + THEN
        mockMvc.perform(get("/library/author/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.name").exists());
    }

    @Test
    @DisplayName("When updating a valid author, it is accepted")
    void testUpdateAuthorWithValidData() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(Authors.nameChange());

        requestBody = """
                {
                    "name": "Author 123"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/author/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name", equalTo(AuthorConstants.CHANGED_NAME)));
    }

}
