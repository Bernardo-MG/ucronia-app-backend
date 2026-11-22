
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
@DisplayName("MemberController - updateMember")
class TestMemberControllerValidationUpdateMember {

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
    @DisplayName("When updating a member without name, then it is rejected")
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

        // WHEN
        mockMvc.perform(put("/profile/member/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When PUT receives a partial form without a fee type, then the request is rejected")
    void testUpdateMember_PartialFormRejected() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                    "name": { "firstName": "Ana", "lastName": "Test" },
                    "active": true,
                    "renew": true
                }
                """;

        // WHEN
        mockMvc.perform(put("/profile/member/10").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
