
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@DisplayName("MemberController - createMember")
class TestMemberControllerValidationCreateMember {

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
    @DisplayName("When creating a member without name, then it is rejected")
    void testCreateMember_Empty() throws Exception {
        final String requestBody;

        // GIVEN
        requestBody = """
                {
                }
                """;

        // WHEN
        mockMvc.perform(post("/profile/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When creating a member with incomplete name, then it is rejected")
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

        // WHEN
        mockMvc.perform(post("/profile/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
