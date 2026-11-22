
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.member.usecase.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController - getAllMembers")
class TestMemberControllerValidationGetAllMembers {

    @InjectMocks
    private MemberController controller;

    private MockMvc          mockMvc;

    @Mock
    private MemberService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When querying members with an invalid sort direction, then it is rejected")
    void testGetAllMembers_InvalidSortDirection() throws Exception {
        // WHEN
        mockMvc.perform(get("/profile/member").param("page", "0")
            .param("size", "10")
            .param("sort", "name.firstName|invalid")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When querying members with an invalid sort pattern, then it is rejected")
    void testGetAllMembers_InvalidSortPattern() throws Exception {
        // WHEN
        mockMvc.perform(get("/profile/member").param("page", "0")
            .param("size", "10")
            .param("sort", "name.firstName")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
