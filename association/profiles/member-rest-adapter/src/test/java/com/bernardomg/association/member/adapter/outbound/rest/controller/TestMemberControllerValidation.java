
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.member.usecase.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController - Validation")
class TestMemberControllerValidation {

    private MockMvc       mockMvc;

    @Mock
    private MemberService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new MemberController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating a member with incomplete name, it is rejected")
    void testCreateMemberWithIncompleteName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                    "name": {
                        "firstName": "John"
                    }
                }
                """;

        mockMvc.perform(post("/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a member without name, it is rejected")
    void testCreateMemberWithoutName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                }
                """;

        mockMvc.perform(post("/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying members with page zero, it is rejected")
    void testGetAllMembersWithInvalidPageZero() throws Exception {
        mockMvc.perform(get("/member").param("page", "0")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying members with size zero, it is rejected")
    void testGetAllMembersWithInvalidSizeZero() throws Exception {
        mockMvc.perform(get("/member").param("page", "1")
            .param("size", "0")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying members with an invalid sort direction, it is rejected")
    void testGetAllMembersWithInvalidSortDirection() throws Exception {
        mockMvc.perform(get("/member").param("page", "1")
            .param("size", "10")
            .param("sort", "firstName|invalid")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying members with an invalid sort pattern, it is rejected")
    void testGetAllMembersWithInvalidSortPattern() throws Exception {
        mockMvc.perform(get("/member").param("page", "1")
            .param("size", "10")
            .param("sort", "firstName")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a member without name, it is rejected")
    void testUpdateMemberWithoutName() throws Exception {
        final String requestBody;

        requestBody = """
                {
                }
                """;

        mockMvc.perform(put("/member/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
