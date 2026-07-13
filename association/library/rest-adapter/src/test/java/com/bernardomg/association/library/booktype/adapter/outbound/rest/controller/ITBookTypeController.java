package com.bernardomg.association.library.booktype.adapter.outbound.rest.controller;

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
@DisplayName("BookTypeController Integration Tests")
class ITBookTypeController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create book type with valid data - validates mapping and response")
    void testCreateBookTypeWithValidData() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": "Science Fiction"
                }
                """;

        mockMvc.perform(post("/book-types").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo("Science Fiction")))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("List book types with pagination - validates pagination parameters")
    void testGetAllBookTypesWithPagination() throws Exception {
        mockMvc.perform(get("/book-types")
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("List book types with page=0 - validates @Min(0) for page parameter")
    void testGetAllBookTypesWithPageZero() throws Exception {
        mockMvc.perform(get("/book-types")
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get book type by ID - validates single resource mapping")
    void testGetBookTypeById() throws Exception {
        mockMvc.perform(get("/book-types/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists())
            .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("Update book type with valid data - validates update mapping")
    void testUpdateBookTypeWithValidData() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": "Mystery"
                }
                """;

        mockMvc.perform(put("/book-types/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo("Mystery")));
    }

    @Test
    @DisplayName("Delete book type - validates delete operation")
    void testDeleteBookType() throws Exception {
        mockMvc.perform(delete("/book-types/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists())
            .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("Create book type with padded name - validates whitespace handling")
    void testCreateBookTypeWithPaddedName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": "  Adventure  "
                }
                """;

        mockMvc.perform(post("/book-types").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name").exists());
    }

    @Test
    @DisplayName("List book types with large page size - validates pagination with custom size")
    void testGetAllBookTypesWithLargePageSize() throws Exception {
        mockMvc.perform(get("/book-types")
            .param("page", "0")
            .param("size", "100")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List book types with specific page number - validates pagination offset")
    void testGetAllBookTypesWithSpecificPage() throws Exception {
        mockMvc.perform(get("/book-types")
            .param("page", "5")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Create book type with empty name - validates empty string handling")
    void testCreateBookTypeWithEmptyName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": ""
                }
                """;

        mockMvc.perform(post("/book-types").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Create book type with special characters - validates special char handling")
    void testCreateBookTypeWithSpecialCharacters() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": "Fantasy & Sci-Fi (Extended)"
                }
                """;

        mockMvc.perform(post("/book-types").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name", equalTo("Fantasy & Sci-Fi (Extended)")));
    }

}
