
package com.bernardomg.association.library.author.test.test.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bernardomg.test.annotation.MvcIntegrationTest;

@MvcIntegrationTest
@DisplayName("AuthorController Validation Integration Tests")
class TestAuthorControllerValidation {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create author with null name - validates @NotNull constraint")
    void testCreateAuthorWithNullName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": null
                }
                """;

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(containsString("name"))));
    }

    @Test
    @DisplayName("Create author without name - validates @NotNull constraint")
    void testCreateAuthorWithoutName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                }
                """;

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(containsString("name"))));
    }

    @Test
    @DisplayName("Create author with oversized name - validates @Size constraint")
    void testCreateAuthorWithOversizedName() throws Exception {
        final String longName;
        final String requestBody;

        longName = "x".repeat(101);
        requestBody = String.format("""
                {
                    "name": "%s"
                }
                """, longName);

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(containsString("name"))));
    }

    @Test
    @DisplayName("List authors with invalid size=0 - validates @Min(1) constraint")
    void testGetAllAuthorsWithInvalidSizeZero() throws Exception {
        mockMvc.perform(get("/authors").param("page", "0")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("List authors with negative page - validates @Min(0) constraint")
    void testGetAllAuthorsWithNegativePage() throws Exception {
        mockMvc.perform(get("/authors").param("page", "-1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Update author without name - validates @NotNull constraint in update")
    void testUpdateAuthorWithoutName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                }
                """;

        mockMvc.perform(put("/authors/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(containsString("name"))));
    }

    @Test
    @DisplayName("Update author with oversized name - validates @Size constraint in update")
    void testUpdateAuthorWithOversizedName() throws Exception {
        final String longName;
        final String requestBody;

        longName = "x".repeat(101);
        requestBody = String.format("""
                {
                    "name": "%s"
                }
                """, longName);

        mockMvc.perform(put("/authors/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(containsString("name"))));
    }

}
