
package com.bernardomg.association.library.lending.test.adapter.outbound.rest.controller;

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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.library.lending.adapter.outbound.rest.controller.BookLendingController;
import com.bernardomg.association.library.lending.usecase.service.BookLendingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookLendingController - Validation")
class TestBookLendingControllerValidation {

    private MockMvc            mockMvc;

    @Mock
    private BookLendingService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean           validator    = new LocalValidatorFactoryBean();
        final ObjectMapper                        objectMapper = new ObjectMapper();
        final MappingJackson2HttpMessageConverter converter;

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        converter = new MappingJackson2HttpMessageConverter(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(new BookLendingController(service))
            .setMessageConverters(converter)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When lending a book without book number, it is rejected")
    void testLendBook_MissingBook() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "borrower": 10,
                    "lendingDate": "2025-08-01T00:00:00Z"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When lending a book without date, it is rejected")
    void testLendBook_MissingDate() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "book": 1,
                    "borrower": 10
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When returning a book without borrower, it is rejected")
    void testReturnBook_MissingBorrower() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "book": 1,
                    "returnDate": "2025-08-02T00:00:00Z"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When returning a book without date, it is rejected")
    void testReturnBook_MissingDate() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "book": 1,
                    "borrower": 10
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
