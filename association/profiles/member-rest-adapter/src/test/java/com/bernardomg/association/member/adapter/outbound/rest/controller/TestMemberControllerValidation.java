package com.bernardomg.association.member.adapter.outbound.rest.controller;

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
@DisplayName("MemberController Validation Integration Tests")
class TestMemberControllerValidation {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Create member without name - validates @NotNull constraint")
    void testCreateMemberWithoutName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                }
                """;

        mockMvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors", hasItem(
                containsString("name"))));
    }

    @Test
    @DisplayName("Create member with incomplete name - validates nested object validation")
    void testCreateMemberWithIncompleteName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": {
                        "firstName": "John"
                    }
                }
                """;

        mockMvc.perform(post("/members").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("List members with invalid sort pattern - validates pattern constraint")
    void testGetAllMembersWithInvalidSortPattern() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("sort", "firstName")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("List members with invalid sort direction - validates direction validation")
    void testGetAllMembersWithInvalidSortDirection() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("sort", "firstName|invalid")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("List members with invalid page=0 - validates @Min(1) constraint")
    void testGetAllMembersWithInvalidPageZero() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("List members with invalid size=0 - validates @Min(1) constraint")
    void testGetAllMembersWithInvalidSizeZero() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

}
