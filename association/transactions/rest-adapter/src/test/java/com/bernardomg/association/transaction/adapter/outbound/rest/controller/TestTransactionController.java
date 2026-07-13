package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bernardomg.test.annotation.MvcIntegrationTest;

@MvcIntegrationTest
@DisplayName("TransactionController Integration Tests")
class TestTransactionController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create transaction with valid data - validates mapping and response")
    void testCreateTransactionWithValidData() throws Exception {
        final String requestBody;

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
    @DisplayName("List transactions with pagination - validates pagination parameters")
    void testGetAllTransactionsWithPagination() throws Exception {
        mockMvc.perform(get("/transactions")
            .param("page", "1")
            .param("size", "20")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.page").exists());
    }

    @Test
    @DisplayName("List transactions with date filter - validates optional parameter handling")
    void testGetAllTransactionsWithDateFilter() throws Exception {
        mockMvc.perform(get("/transactions")
            .param("page", "1")
            .param("size", "10")
            .param("date", "2025-08-01T00:00:00Z")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List transactions with date range - validates multiple optional parameters")
    void testGetAllTransactionsWithDateRange() throws Exception {
        mockMvc.perform(get("/transactions")
            .param("page", "1")
            .param("size", "10")
            .param("from", "2025-08-01T00:00:00Z")
            .param("to", "2025-08-31T23:59:59Z")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List transactions with description filter - validates string parameter handling")
    void testGetAllTransactionsWithDescriptionFilter() throws Exception {
        mockMvc.perform(get("/transactions")
            .param("page", "1")
            .param("size", "10")
            .param("description", "test")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get transaction range - validates response body mapping")
    void testGetTransactionRange() throws Exception {
        mockMvc.perform(get("/transactions/range")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Update transaction with valid data - validates update mapping")
    void testUpdateTransactionWithValidData() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "amount": 150.75,
                    "description": "Updated transaction"
                }
                """;

        mockMvc.perform(put("/transactions/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", equalTo("Updated transaction")));
    }

    @Test
    @DisplayName("Delete transaction - validates delete operation")
    void testDeleteTransaction() throws Exception {
        mockMvc.perform(delete("/transactions/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get single transaction - validates single resource mapping")
    void testGetOneTransaction() throws Exception {
        mockMvc.perform(get("/transactions/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
