
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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.BookController;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookCreation;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.BookUpdate;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.BookCreations;
import com.bernardomg.association.library.book.test.configuration.factory.BookUpdates;
import com.bernardomg.association.library.book.test.configuration.factory.Books;
import com.bernardomg.association.library.book.usecase.service.BookService;
import com.bernardomg.test.json.JsonUtils;

/**
 * TODO: test donation is parsed
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("BookController")
class TestBookController {

    @InjectMocks
    private BookController controller;

    private MockMvc        mockMvc;

    @Mock
    private BookService    service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("Can create books")
    void testCreate() throws Exception {
        final BookCreation bookCreation;

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
        final BookCreation bookCreation;

        // GIVEN
        bookCreation = BookCreations.minimal();

        // WHEN
        mockMvc.perform(post("/library/book").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)));

        // THEN
        verify(service).create(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Books.minimal())));
    }

    @Test
    @DisplayName("Can update books")
    void testUpdate() throws Exception {
        final BookUpdate bookCreation;

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
        final BookUpdate bookCreation;

        // GIVEN
        bookCreation = BookUpdates.minimal();

        // WHEN
        mockMvc.perform(put("/library/book/{number}", BookConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)));

        // THEN
        verify(service).update(eq(BookConstants.NUMBER),
            assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
                .ignoringFields("number")
                .isEqualTo(Books.minimal())));
    }

}
