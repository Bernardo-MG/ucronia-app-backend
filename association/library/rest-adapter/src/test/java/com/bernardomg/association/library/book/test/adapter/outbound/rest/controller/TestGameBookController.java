
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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.GameBookController;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.GameBookService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameBookController")
class TestGameBookController {

    private MockMvc         mockMvc;

    @Mock
    private GameBookService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new GameBookController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a game book with valid data, it is accepted")
    void testCreateGameBook() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(GameBooks.minimal());

        requestBody = """
                {
                    "title": {
                        "title": "Game Book"
                    },
                    "isbn": "1-56619-909-3",
                    "language": "en"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/book/game").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.title.title").exists());
    }

    @Test
    @DisplayName("When the game book is deleted, it is accepted")
    void testDeleteGameBook() throws Exception {
        // GIVEN
        given(service.delete(1L)).willReturn(GameBooks.minimal());

        // WHEN + THEN
        mockMvc.perform(delete("/library/book/game/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When there are game books, they are returned")
    void testGetAllGameBooks() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(GameBooks.minimal()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/library/book/game").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the game book exists, it is returned")
    void testGetGameBookById() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(GameBooks.minimal()));

        // WHEN + THEN
        mockMvc.perform(get("/library/book/game/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.title.title").exists());
    }

    @Test
    @DisplayName("When updating a valid game book, it is accepted")
    void testUpdateGameBook() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(GameBooks.minimal());

        requestBody = """
                {
                    "title": {
                        "title": "Updated Game Book"
                    },
                    "isbn": "1-56619-909-3",
                    "language": "en"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/book/game/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.title.title").exists());
    }

}
