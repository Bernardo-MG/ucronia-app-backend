
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.member.adapter.outbound.rest.dto.MemberStatusDto;
import com.bernardomg.association.member.adapter.outbound.rest.security.PublicMemberStatusAuthorizer;
import com.bernardomg.association.member.domain.filter.PublicMemberFilter;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.PublicMemberFilters;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.association.member.usecase.service.PublicMemberService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.security.domain.permission.constant.Actions;
import com.bernardomg.security.springframework.access.interceptor.ResourcePermissionEvaluator;
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicMemberController")
class TestPublicMemberController {

    private PublicMemberController      controller;

    private MockMvc                     mockMvc;

    @Mock
    private ResourcePermissionEvaluator permissionEvaluator;

    @Mock
    private PublicMemberService         service;

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

        controller = new PublicMemberController(service, new PublicMemberStatusAuthorizer(permissionEvaluator));
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(converter)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("The public member list returns the members")
    void testGetAllPublicMembers() throws Exception {
        final Page<PublicMember> existing;
        final Pagination         pagination;
        final Sorting            sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(PublicMembers.valid()), 1, 1, 1, 1, 1, true, true, sorting);
        given(service.getAll(PublicMemberFilters.empty(), pagination, sorting)).willReturn(existing);

        // WHEN + THEN
        mockMvc.perform(get("/member").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content[0].memberNumber").value((int) MemberConstants.MEMBER_NUMBER));
    }

    @Test
    @DisplayName("The member list accepts the inactive status filter")
    void testGetAllPublicMembers_Inactive() throws Exception {
        final Page<PublicMember> existing;
        final PublicMemberFilter filter;
        final Pagination         pagination;
        final Sorting            sorting;

        // GIVEN
        final Authentication     authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "INACTIVE_MEMBER", Actions.READ)).willReturn(true);
        filter = new PublicMemberFilter(Optional.empty(), MemberStatus.INACTIVE);
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(PublicMembers.valid()), 1, 1, 1, 1, 1, true, true, sorting);
        given(service.getAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN + THEN
        mockMvc.perform(get("/member").param("status", "INACTIVE")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value((int) MemberConstants.NUMBER));
    }

    @Test
    @DisplayName("The public member list contains the joined month and total paid months")
    void testGetAllPublicMembers_PaidMembership() throws Exception {
        final Page<PublicMember> existing;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMember       member;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        member = PublicMembers.withMembership(Instant.parse("2024-01-01T00:00:00Z"), 3L);
        existing = new Page<>(List.of(member), 1, 1, 1, 1, 1, true, true, sorting);
        given(service.getAll(PublicMemberFilters.empty(), pagination, sorting)).willReturn(existing);

        // WHEN + THEN
        mockMvc.perform(get("/member").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].joined").value("2024-01-01T00:00:00Z"))
            .andExpect(jsonPath("$.content[0].totalMonths").value(3));
    }

    @Test
    @DisplayName("Inactive and all filters are rejected without the inactive read permission")
    void testGetAllPublicMembers_WithoutInactivePermission() {
        // GIVEN
        final Authentication authentication = new TestingAuthenticationToken("user", "password");
        SecurityContextHolder.getContext()
            .setAuthentication(authentication);
        given(permissionEvaluator.isAuthorized(authentication, "INACTIVE_MEMBER", Actions.READ)).willReturn(false);

        // WHEN + THEN
        Assertions
            .assertThatThrownBy(() -> controller.getAllPublicMembers(1, 10, List.of(), MemberStatusDto.INACTIVE, null))
            .isInstanceOf(AccessDeniedException.class);
        Assertions.assertThatThrownBy(() -> controller.getAllPublicMembers(1, 10, List.of(), MemberStatusDto.ALL, null))
            .isInstanceOf(AccessDeniedException.class);
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("The public member response contains its numbers")
    void testGetPublicMemberByNumber() throws Exception {

        // GIVEN
        given(service.getOne(MemberConstants.NUMBER))
            .willReturn(Optional.of(PublicMembers.withMembership(Instant.parse("2024-01-01T00:00:00Z"), 3L)));

        // WHEN + THEN
        mockMvc.perform(get("/member/{number}", MemberConstants.NUMBER).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content.memberNumber").value((int) MemberConstants.MEMBER_NUMBER))
            .andExpect(jsonPath("$.content.joined").value("2024-01-01T00:00:00Z"))
            .andExpect(jsonPath("$.content.totalMonths").value(3));
    }

    @Test
    @DisplayName("With no paid fees, the public member has no joined month and zero total months")
    void testGetPublicMemberByNumber_NoPaidFees() throws Exception {
        // GIVEN
        given(service.getOne(MemberConstants.NUMBER)).willReturn(Optional.of(PublicMembers.valid()));

        // WHEN + THEN
        mockMvc.perform(get("/member/{number}", MemberConstants.NUMBER).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.joined").doesNotExist())
            .andExpect(jsonPath("$.content.totalMonths").value(0));
    }

}
