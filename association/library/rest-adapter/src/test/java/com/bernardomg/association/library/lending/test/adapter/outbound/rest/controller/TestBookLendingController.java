
package com.bernardomg.association.library.lending.test.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

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
import com.bernardomg.association.library.lending.test.configuration.factory.BookLendings;
import com.bernardomg.association.library.lending.usecase.service.BookLendingService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookLendingController")
class TestBookLendingController {

    private MockMvc            mockMvc;

    @Mock
    private BookLendingService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean           validator    = new LocalValidatorFactoryBean();
        final ObjectMapper                        objectMapper = new ObjectMapper();
        final MappingJackson2HttpMessageConverter converter;

        validator.setValidationPropertyMap(Map.of("hibernate.validator.allow_parameter_constraint_override", "true"));
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
    @DisplayName("When there are lendings, they are returned")
    void testGetAllBookLendings() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(BookLendings.lent()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/library/lending").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When lending a book with valid data, it is accepted")
    void testLendBook() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.lendBook(1L, 10L, java.time.Instant.parse("2025-08-01T00:00:00Z")))
            .willReturn(BookLendings.lent());

        requestBody = """
                {
                    "book": 1,
                    "borrower": 10,
                    "lendingDate": "2025-08-01T00:00:00Z"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.book.number").exists());
    }

    @Test
    @DisplayName("When returning a book with valid data, it is accepted")
    void testReturnBook() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.returnBook(1L, 10L, java.time.Instant.parse("2025-08-02T00:00:00Z")))
            .willReturn(BookLendings.returned());

        requestBody = """
                {
                    "book": 1,
                    "borrower": 10,
                    "returnDate": "2025-08-02T00:00:00Z"
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/library/lending").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.book.number").exists());
    }

}
