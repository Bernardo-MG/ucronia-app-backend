
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
    @DisplayName("When creating a member without name, it is rejected")
    void testCreateMember_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/profile/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a member with incomplete name, it is rejected")
    void testCreateMember_IncompleteName() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "identifier": "6789",
                    "feeType": 10,
                    "name": {
                        "firstName": "John"
                    }
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/profile/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying members with an invalid sort direction, it is rejected")
    void testGetAllMembers_InvalidSortDirection() throws Exception {
        // WHEN + THEN
        mockMvc.perform(get("/profile/member").param("page", "0")
            .param("size", "10")
            .param("sort", "name.firstName|invalid")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying members with an invalid sort pattern, it is rejected")
    void testGetAllMembers_InvalidSortPattern() throws Exception {
        // WHEN + THEN
        mockMvc.perform(get("/profile/member").param("page", "0")
            .param("size", "10")
            .param("sort", "name.firstName")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When updating a member without name, it is rejected")
    void testUpdateMember_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                "identifier": "6789",
                "feeType": 10,
                "active": true,
                "renew": true
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/profile/member/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isBadRequest());
    }

}
