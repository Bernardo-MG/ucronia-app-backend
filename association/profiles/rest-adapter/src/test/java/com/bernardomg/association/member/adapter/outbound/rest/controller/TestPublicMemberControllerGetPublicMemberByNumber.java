
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Optional;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.member.adapter.outbound.rest.security.PublicMemberStatusAuthorizer;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.association.member.usecase.service.PublicMemberService;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicMemberController - getPublicMemberByNumber")
class TestPublicMemberControllerGetPublicMemberByNumber {

    @InjectMocks
    private PublicMemberController       controller;

    private MockMvc                      mockMvc;

    @Mock
    private PublicMemberService          service;

    @Mock
    private PublicMemberStatusAuthorizer statusAuthorizer;

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean           validator;
        final MappingJackson2HttpMessageConverter converter;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        converter = new MappingJackson2HttpMessageConverter();
        converter.getObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(converter)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When the public member response is generated, then it contains its numbers")
    void testGetPublicMemberByNumber() throws Exception {

        // GIVEN
        given(service.getOne(MemberConstants.NUMBER))
            .willReturn(Optional.of(PublicMembers.withMembership(Instant.parse("2024-01-01T00:00:00Z"), 3L)));

        // WHEN
        mockMvc.perform(get("/member/{number}", MemberConstants.NUMBER).contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content.memberNumber").value((int) MemberConstants.MEMBER_NUMBER))
            .andExpect(jsonPath("$.content.joined").value("2024-01-01T00:00:00Z"))
            .andExpect(jsonPath("$.content.totalMonths").value(3));
    }

    @Test
    @DisplayName("When there is no paid fees, then the public member has no joined month and zero total months")
    void testGetPublicMemberByNumber_NoPaidFees() throws Exception {
        // GIVEN
        given(service.getOne(MemberConstants.NUMBER)).willReturn(Optional.of(PublicMembers.active()));

        // WHEN
        mockMvc.perform(get("/member/{number}", MemberConstants.NUMBER).contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.joined").doesNotExist())
            .andExpect(jsonPath("$.content.totalMonths").value(0));
    }

}
