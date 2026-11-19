
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController - createMember")
class TestMemberControllerCreateMember {

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
    @DisplayName("When creating a member with valid data, then it is accepted")
    void testCreateMember() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(Members.created());

        requestBody = String.format("""
                {
                    "identifier": "%s",
                    "feeType": %d,
                    "name": {
                        "firstName": "%s",
                        "lastName": "%s"
                    }
                }
                """, MemberConstants.IDENTIFIER, FeeConstants.FEE_TYPE_NUMBER, ProfileConstants.FIRST_NAME,
            ProfileConstants.LAST_NAME);

        // WHEN
        mockMvc.perform(post("/profile/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name.firstName", equalTo(ProfileConstants.FIRST_NAME)))
            .andExpect(jsonPath("$.content.name.lastName", equalTo(ProfileConstants.LAST_NAME)))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When creating a member with address, then it is accepted")
    void testCreateMember_WithAddress() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(Members.active());

        requestBody = String.format("""
                {
                    "identifier": "%s",
                    "feeType": %d,
                    "name": {
                        "firstName": "%s",
                        "lastName": "%s"
                    }
                }
                """, MemberConstants.IDENTIFIER, FeeConstants.FEE_TYPE_NUMBER, ProfileConstants.FIRST_NAME,
            ProfileConstants.LAST_NAME);

        // WHEN
        mockMvc.perform(post("/profile/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isCreated())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.address", equalTo(MemberConstants.ADDRESS)));
    }

}
