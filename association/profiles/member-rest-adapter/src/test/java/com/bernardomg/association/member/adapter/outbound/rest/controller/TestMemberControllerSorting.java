package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bernardomg.test.annotation.MvcIntegrationTest;

@MvcIntegrationTest
@DisplayName("MemberController Sorting Integration Tests")
class TestMemberControllerSorting {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("List members with valid sort pattern - validates sort parameter pattern")
    void testGetAllMembersWithValidSort() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("sort", "firstName|asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List members with sort by lastName desc - validates alternative sort patterns")
    void testGetAllMembersWithLastNameSort() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("sort", "lastName|desc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("List members with sort by number - validates number field in sort")
    void testGetAllMembersWithNumberSort() throws Exception {
        mockMvc.perform(get("/members")
            .param("page", "1")
            .param("size", "10")
            .param("sort", "number|asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
