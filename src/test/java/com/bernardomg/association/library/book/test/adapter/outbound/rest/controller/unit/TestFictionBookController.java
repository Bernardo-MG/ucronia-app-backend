
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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.FictionBookController;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookCreation;
import com.bernardomg.association.library.book.adapter.outbound.rest.model.FictionBookUpdate;
import com.bernardomg.association.library.book.test.configuration.factory.BookConstants;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBookCreations;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBookUpdates;
import com.bernardomg.association.library.book.test.configuration.factory.FictionBooks;
import com.bernardomg.association.library.book.usecase.service.FictionBookService;
import com.bernardomg.test.json.JsonUtils;

/**
 * TODO: test donation is parsed
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("FictionBookController")
class TestFictionBookController {

    private static final String   URL = "/library/book/fiction";

    @InjectMocks
    private FictionBookController controller;

    private MockMvc               mockMvc;

    @Mock
    private FictionBookService    service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("Can create books")
    void testCreate() throws Exception {
        final FictionBookCreation bookCreation;

        // GIVEN
        bookCreation = FictionBookCreations.minimal();

        // WHEN + THEN
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("When creating a book, it is sent to the service")
    void testCreate_CallsService() throws Exception {
        final FictionBookCreation bookCreation;

        // GIVEN
        bookCreation = FictionBookCreations.minimal();

        // WHEN
        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)));

        // THEN
        verify(service).create(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(FictionBooks.minimal())));
    }

    @Test
    @DisplayName("Can update books")
    void testUpdate() throws Exception {
        final FictionBookUpdate bookCreation;

        // GIVEN
        bookCreation = FictionBookUpdates.minimal();

        // WHEN + THEN
        mockMvc.perform(put(URL + "/{number}", BookConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Can update books")
    void testUpdate_CallsService() throws Exception {
        final FictionBookUpdate bookCreation;

        // GIVEN
        bookCreation = FictionBookUpdates.minimal();

        // WHEN
        mockMvc.perform(put(URL + "/{number}", BookConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(bookCreation)));

        // THEN
        verify(service).update(eq(BookConstants.NUMBER),
            assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
                .ignoringFields("number")
                .isEqualTo(FictionBooks.minimal())));
    }

}
