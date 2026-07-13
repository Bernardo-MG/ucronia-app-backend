
package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.bernardomg.association.transaction.usecase.service.TransactionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionController - Validation")
class TestTransactionControllerValidation {

    private MockMvc            mockMvc;

    @Mock
    private TransactionService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new TransactionController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a transaction without amount, it is rejected")
    void testCreateTransactionWithoutAmount() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "date": "2025-08-01T00:00:00Z",
                    "description": "Test transaction"
                }
                """;

        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a transaction without date, it is rejected")
    void testCreateTransactionWithoutDate() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "amount": 100.50,
                    "description": "Test transaction"
                }
                """;

        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a transaction without description, it is rejected")
    void testCreateTransactionWithoutDescription() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "date": "2025-08-01T00:00:00Z",
                    "amount": 100.50
                }
                """;

        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a transaction with an oversized description, it is rejected")
    void testCreateTransactionWithOversizedDescription() throws Exception {
        final String longDescription;
        final String requestBody;

        longDescription = "x".repeat(201);
        requestBody = String.format("""
                {
                    "date": "2025-08-01T00:00:00Z",
                    "amount": 100.50,
                    "description": "%s"
                }
                """, longDescription);

        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying transactions with page zero, it is rejected")
    void testGetAllTransactionsWithInvalidPageZero() throws Exception {
        mockMvc.perform(get("/transactions").param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying transactions with size zero, it is rejected")
    void testGetAllTransactionsWithInvalidSizeZero() throws Exception {
        mockMvc.perform(get("/transactions").param("page", "1")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

}
