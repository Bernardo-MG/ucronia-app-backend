package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
@DisplayName("MemberController Integration Tests")
class TestMemberController {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create member with valid data - validates mapping and response")
    void testCreateMemberWithValidData() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": {
                        "firstName": "John",
                        "lastName": "Doe"
                    }
                }
                """;

        mockMvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name.firstName", equalTo("John")))
            .andExpect(jsonPath("$.name.lastName", equalTo("Doe")))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("List members with pagination - validates pagination parameters")
    void testGetAllMembersWithPagination() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("List members with ACTIVE status - validates enum parameter mapping")
    void testGetAllMembersWithActiveStatus() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("status", "ACTIVE")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List members with INACTIVE status - validates enum value mapping")
    void testGetAllMembersWithInactiveStatus() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("status", "INACTIVE")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List members with name filter - validates string filter parameter")
    void testGetAllMembersWithNameFilter() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("name", "John")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List members with combined filters - validates multiple filter parameters")
    void testGetAllMembersWithCombinedFilters() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("status", "ACTIVE")
            .param("name", "Doe")
            .param("sort", "lastName|asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get member by number - validates single resource mapping")
    void testGetMemberByNumber() throws Exception {
        mockMvc.perform(get("/members/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("Update member with PATCH - validates update mapping")
    void testPatchMemberWithValidData() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": {
                        "firstName": "Jane",
                        "lastName": "Smith"
                    }
                }
                """;

        mockMvc.perform(patch("/members/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name.firstName", equalTo("Jane")));
    }

    @Test
    @DisplayName("Update member with PUT - validates full update mapping")
    void testUpdateMemberWithValidData() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": {
                        "firstName": "Richard",
                        "lastName": "Johnson"
                    }
                }
                """;

        mockMvc.perform(put("/members/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name.firstName", equalTo("Richard")));
    }

    @Test
    @DisplayName("Delete member - validates delete operation")
    void testDeleteMember() throws Exception {
        mockMvc.perform(delete("/members/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("List members with padded name filter - validates whitespace handling")
    void testGetAllMembersWithPaddedNameFilter() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("name", "  John  ")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Create member with address - validates optional field mapping")
    void testCreateMemberWithAddress() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": {
                        "firstName": "Alice",
                        "lastName": "Brown"
                    },
                    "address": "123 Main St"
                }
                """;

        mockMvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.address", equalTo("123 Main St")));
    }

}
