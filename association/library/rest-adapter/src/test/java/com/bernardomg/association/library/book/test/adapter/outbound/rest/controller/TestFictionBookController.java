
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.FictionBookController;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.usecase.service.FictionBookService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("FictionBookController")
class TestFictionBookController {

    private MockMvc            mockMvc;

    @Mock
    private FictionBookService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new FictionBookController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a fiction book with valid data, it is accepted")
    void testCreateFictionBook() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(FictionBooks.minimal());

        requestBody = """
                {
                    "title": {
                        "title": "Fiction Book"
                    },
                    "isbn": "1-56619-909-3",
                    "language": "en"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/book/fiction").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.title.title").exists());
    }

    @Test
    @DisplayName("When the fiction book is deleted, it is accepted")
    void testDeleteFictionBook() throws Exception {
        // GIVEN
        given(service.delete(1L)).willReturn(FictionBooks.minimal());

        // WHEN + THEN
        mockMvc.perform(delete("/library/book/fiction/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When there are fiction books, they are returned")
    void testGetAllFictionBooks() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(FictionBooks.minimal()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/library/book/fiction").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the fiction book exists, it is returned")
    void testGetFictionBookById() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(FictionBooks.minimal()));

        // WHEN + THEN
        mockMvc.perform(get("/library/book/fiction/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.title.title").exists());
    }

    @Test
    @DisplayName("When updating a valid fiction book, it is accepted")
    void testUpdateFictionBook() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(FictionBooks.minimal());

        requestBody = """
                {
                    "title": {
                        "title": "Updated Fiction Book"
                    },
                    "isbn": "1-56619-909-3",
                    "language": "en"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/book/fiction/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.title.title").exists());
    }

}
