
package com.bernardomg.association.library.author.test.test.adapter.outbound.rest.controller;

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
@DisplayName("AuthorController Integration Tests")
class TestAuthorController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create author with empty name - validates constraint handling")
    void testCreateAuthorWithEmptyName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": ""
                }
                """;

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create author with name at max boundary - validates @Size boundary")
    void testCreateAuthorWithNameAtMaxBoundary() throws Exception {
        final String boundaryName;
        final String requestBody;

        boundaryName = "x".repeat(100);
        requestBody = String.format("""
                {
                    "name": "%s"
                }
                """, boundaryName);

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo(boundaryName)));
    }

    @Test
    @DisplayName("Create author with unknown fields - validates JSON deserialization")
    void testCreateAuthorWithUnknownFields() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": "Test Author",
                    "unknownField": "some value"
                }
                """;

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", equalTo("Test Author")));
    }

    @Test
    @DisplayName("Create author with valid data - validates mapping and response")
    void testCreateAuthorWithValidData() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": "Gary Gigax"
                }
                """;

        mockMvc.perform(post("/authors").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo("Gary Gigax")))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("Delete author - validates delete operation")
    void testDeleteAuthor() throws Exception {
        mockMvc.perform(delete("/authors/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists())
            .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("List authors with page=0 - validates @Min(0) for page parameter")
    void testGetAllAuthorsWithPageZero() throws Exception {
        mockMvc.perform(get("/authors").param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List authors with pagination - validates pagination parameters")
    void testGetAllAuthorsWithPagination() throws Exception {
        mockMvc.perform(get("/authors").param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("Get author by ID - validates single resource mapping")
    void testGetAuthorById() throws Exception {
        mockMvc.perform(get("/authors/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists())
            .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("Update author with valid data - validates update mapping")
    void testUpdateAuthorWithValidData() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": "Updated Author Name"
                }
                """;

        mockMvc.perform(put("/authors/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo("Updated Author Name")));
    }

}
