
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController - patchMember")
class TestMemberControllerPatchMember {

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
    @DisplayName("When patching a valid member, then it is accepted")
    void testPatchMember() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.patch(any())).willReturn(Members.nameChangePatch());

        requestBody = String.format("""
                {
                    "identifier": "%s",
                    "feeType": %d,
                    "name": {
                        "firstName": "Name 123",
                        "lastName": "Last name"
                    },
                    "active": true,
                    "renew": true
                }
                """, MemberConstants.IDENTIFIER, FeeConstants.FEE_TYPE_NUMBER);

        // WHEN
        mockMvc.perform(patch("/profile/member/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name.firstName", equalTo("Name 123")));
    }

}
