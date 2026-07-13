
package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

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

import com.bernardomg.association.transaction.domain.model.Transaction;
import com.bernardomg.association.transaction.domain.model.TransactionMonthsRange;
import com.bernardomg.association.transaction.usecase.service.TransactionService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionController")
class TestTransactionController {

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
    @DisplayName("When creating a transaction with valid data, it is accepted")
    void testCreateTransactionWithValidData() throws Exception {
        final String requestBody;

        given(service.create(any()))
            .willReturn(new Transaction(1L, Instant.parse("2025-08-01T00:00:00Z"), 100.50F, "Test transaction"));

        requestBody = """
                {
                    "date": "2025-08-01T00:00:00Z",
                    "amount": 100.50,
                    "description": "Test transaction"
                }
                """;

        mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.date").exists())
            .andExpect(jsonPath("$.amount").exists())
            .andExpect(jsonPath("$.description", equalTo("Test transaction")));
    }

    @Test
    @DisplayName("When the transaction is deleted, it is accepted")
    void testDeleteTransaction() throws Exception {
        given(service.delete(any()))
            .willReturn(new Transaction(1L, Instant.parse("2025-08-01T00:00:00Z"), 150.75F, "Updated transaction"));

        mockMvc.perform(delete("/transactions/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When the transaction exists, it is returned")
    void testGetOneTransaction() throws Exception {
        given(service.getOne(1L)).willReturn(
            Optional.of(new Transaction(1L, Instant.parse("2025-08-01T00:00:00Z"), 100.50F, "Test transaction")));

        mockMvc.perform(get("/transactions/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When requesting the transaction range, it is returned")
    void testGetTransactionRange() throws Exception {
        given(service.getRange()).willReturn(new TransactionMonthsRange(List.of(YearMonth.of(2025, 8))));

        mockMvc.perform(get("/transactions/range").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When updating a valid transaction, it is accepted")
    void testUpdateTransactionWithValidData() throws Exception {
        final String requestBody;

        given(service.update(any()))
            .willReturn(new Transaction(1L, Instant.parse("2025-08-01T00:00:00Z"), 150.75F, "Updated transaction"));

        requestBody = """
                {
                    "amount": 150.75,
                    "description": "Updated transaction"
                }
                """;

        mockMvc.perform(put("/transactions/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", equalTo("Updated transaction")));
    }

}
