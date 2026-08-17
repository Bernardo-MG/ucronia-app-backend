
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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.FictionBookController;
import com.bernardomg.association.library.book.usecase.service.FictionBookService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FictionBookController - Validation")
class TestFictionBookControllerValidation {

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
    @DisplayName("When creating an empty fiction book, it is rejected")
    void testCreateFictionBook_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/book/fiction").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a fiction book without title, it is rejected")
    void testCreateFictionBook_MissingTitle() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "isbn": "1-56619-909-3",
                    "language": "en"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/book/fiction").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating an empty fiction book, it is rejected")
    void testUpdateFictionBook_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/book/fiction/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a fiction book with oversized isbn, it is rejected")
    void testUpdateFictionBook_OversizedIsbn() throws Exception {
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
        mockMvc.perform(put("/library/book/fiction/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
