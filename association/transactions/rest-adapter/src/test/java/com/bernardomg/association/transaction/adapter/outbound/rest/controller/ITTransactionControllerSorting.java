package com.bernardomg.association.transaction.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
@DisplayName("TransactionController Sorting Integration Tests")
class ITTransactionControllerSorting {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("List transactions with sorting - validates sort parameter handling")
    void testGetAllTransactionsWithSorting() throws Exception {
        mockMvc.perform(get("/transactions")
            .param("page", "1")
            .param("size", "10")
            .param("sort", "date:desc,amount:asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", isA(java.util.ArrayList.class)));
    }

}
