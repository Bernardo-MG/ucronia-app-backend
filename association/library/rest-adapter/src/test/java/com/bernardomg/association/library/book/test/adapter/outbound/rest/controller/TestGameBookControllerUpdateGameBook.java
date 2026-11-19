
package com.bernardomg.association.library.book.test.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.bernardomg.association.library.book.test.configuration.factory.GameBooks;
import com.bernardomg.association.library.book.usecase.service.GameBookService;

@ExtendWith(MockitoExtension.class)
@DisplayName("GameBookController - updateGameBook")
class TestGameBookControllerUpdateGameBook {

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
    @DisplayName("When updating a valid game book, then it is accepted")
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

        // WHEN
        mockMvc.perform(put("/library/book/game/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.title.title").exists());
    }

}
