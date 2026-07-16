
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.GameBookController;
import com.bernardomg.association.library.book.usecase.service.GameBookService;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameBookController - Validation")
class TestGameBookControllerValidation {

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
    @DisplayName("When creating an empty game book, it is rejected")
    void testCreateGameBook_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/book/game").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a game book without title, it is rejected")
    void testCreateGameBook_MissingTitle() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "isbn": "1-56619-909-3",
                    "language": "en"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/book/game").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an empty game book, it is rejected")
    void testUpdateGameBook_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/book/game/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a game book with oversized isbn, it is rejected")
    void testUpdateGameBook_OversizedIsbn() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "title": {
                        "title": "Book"
                    },
                    "isbn": "123456789012345678",
                    "language": "en"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/book/game/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
