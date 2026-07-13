package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bernardomg.test.annotation.MvcIntegrationTest;

@MvcIntegrationTest
@DisplayName("TransactionController Validation Integration Tests")
class ITTransactionControllerValidation {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create transaction without date - validates @NotNull constraint")
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
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(
                containsString("date"))));
    }

    @Test
    @DisplayName("Create transaction without amount - validates @NotNull constraint")
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
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(
                containsString("amount"))));
    }

    @Test
    @DisplayName("Create transaction without description - validates @NotNull constraint")
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
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(
                containsString("description"))));
    }

    @Test
    @DisplayName("Create transaction with oversized description - validates @Size constraint")
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
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(
                containsString("description") )));
    }

    @Test
    @DisplayName("List transactions with invalid page=0 - validates @Min(1) constraint")
    void testGetAllTransactionsWithInvalidPageZero() throws Exception {
        mockMvc.perform(get("/transactions")
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("List transactions with invalid size=0 - validates @Min(1) constraint")
    void testGetAllTransactionsWithInvalidSizeZero() throws Exception {
        mockMvc.perform(get("/transactions")
            .param("page", "1")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

}
