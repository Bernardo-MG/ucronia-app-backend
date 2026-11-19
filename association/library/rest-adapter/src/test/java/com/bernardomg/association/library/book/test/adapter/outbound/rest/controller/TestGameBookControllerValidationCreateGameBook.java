
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.bernardomg.association.library.book.adapter.outbound.rest.controller.GameBookController;
import com.bernardomg.association.library.book.usecase.service.GameBookService;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameBookController - createGameBook")
class TestGameBookControllerValidationCreateGameBook {

    @InjectMocks
    private GameBookController controller;

    private MockMvc            mockMvc;

    @Mock
    private GameBookService    service;

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
    @DisplayName("When creating an empty game book, then it is rejected")
    void testCreateGameBook_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN
        mockMvc.perform(post("/library/book/game").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a game book without title, then it is rejected")
    void testCreateGameBook_MissingTitle() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "isbn": "1-56619-909-3",
                    "language": "en"
                }
                """;

        // WHEN
        mockMvc.perform(post("/library/book/game").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
