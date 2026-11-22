
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
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
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.FictionBookController;
import com.bernardomg.association.library.book.usecase.service.FictionBookService;

@ExtendWith(MockitoExtension.class)
@DisplayName("FictionBookController - updateFictionBook")
class TestFictionBookControllerValidationUpdateFictionBook {

    @InjectMocks
    private FictionBookController controller;

    private MockMvc               mockMvc;

    @Mock
    private FictionBookService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When updating an empty fiction book, then it is rejected")
    void testUpdateFictionBook_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN
        mockMvc.perform(put("/library/book/fiction/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a fiction book with oversized isbn, then it is rejected")
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

        // WHEN
        mockMvc.perform(put("/library/book/fiction/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
