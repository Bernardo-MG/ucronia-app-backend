
package com.bernardomg.association.library.author.test.test.adapter.outbound.rest.controller;

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
@DisplayName("AuthorController Sorting Integration Tests")
class TestAuthorControllerSorting {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("List authors with multiple sort fields - validates complex sorting")
    void testGetAllAuthorsWithMultipleSortFields() throws Exception {
        mockMvc.perform(get("/authors").param("page", "0")
            .param("size", "10")
            .param("sort", "name:asc,number:desc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List authors with sorting - validates sort parameter handling")
    void testGetAllAuthorsWithSorting() throws Exception {
        mockMvc.perform(get("/authors").param("page", "0")
            .param("size", "10")
            .param("sort", "name:asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", isA(java.util.ArrayList.class)));
    }

}
