
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.GameBookController;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.GameBookCreation;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.GameBookUpdate;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.BookCreations;
import com.bernardomg.association.library.book.test.configuration.factory.BookUpdates;
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.GameBookService;
import com.bernardomg.test.json.JsonUtils;

/**
 * TODO: test donation is parsed
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("BookController")
class TestGameBookController {

    @InjectMocks
    private GameBookController controller;

    private MockMvc            mockMvc;

    @Mock
    private GameBookService    service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("Can create books")
    void testCreate() throws Exception {
        final GameBookCreation bookCreation;

        // GIVEN
        bookCreation = BookCreations.minimal();

        // WHEN + THEN
        mockMvc.perform(post("/library/book").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("When creating a book, it is sent to the service")
    void testCreate_CallsService() throws Exception {
        final GameBookCreation bookCreation;

        // GIVEN
        bookCreation = BookCreations.minimal();

        // WHEN
        mockMvc.perform(post("/library/book").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)));

        // THEN
        verify(service).create(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(GameBooks.minimal())));
    }

    @Test
    @DisplayName("Can update books")
    void testUpdate() throws Exception {
        final GameBookUpdate bookCreation;

        // GIVEN
        bookCreation = BookUpdates.minimal();

        // WHEN + THEN
        mockMvc.perform(put("/library/book/{number}", BookConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Can update books")
    void testUpdate_CallsService() throws Exception {
        final GameBookUpdate bookCreation;

        // GIVEN
        bookCreation = BookUpdates.minimal();

        // WHEN
        mockMvc.perform(put("/library/book/{number}", BookConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)));

        // THEN
        verify(service).update(eq(BookConstants.NUMBER),
            assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
                .ignoringFields("number")
                .isEqualTo(GameBooks.minimal())));
    }

}
