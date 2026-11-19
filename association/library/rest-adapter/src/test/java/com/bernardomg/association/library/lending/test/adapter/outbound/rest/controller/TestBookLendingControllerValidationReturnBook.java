
package com.bernardomg.association.library.lending.test.adapter.outbound.rest.controller;

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
@DisplayName("BookLendingController - returnBook")
class TestBookLendingControllerValidationReturnBook {

    @InjectMocks
    private BookLendingController controller;

    private MockMvc               mockMvc;

    @Mock
    private BookLendingService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean           validator;
        final ObjectMapper                        objectMapper;
        final MappingJackson2HttpMessageConverter converter;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        converter = new MappingJackson2HttpMessageConverter(objectMapper);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(converter)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When returning a book without borrower, then it is rejected")
    void testReturnBook_MissingBorrower() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "book": 1,
                    "returnDate": "2025-08-02T00:00:00Z"
                }
                """;

        // WHEN
        mockMvc.perform(put("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When returning a book without date, then it is rejected")
    void testReturnBook_MissingDate() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "book": 1,
                    "borrower": 10
                }
                """;

        // WHEN
        mockMvc.perform(put("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
