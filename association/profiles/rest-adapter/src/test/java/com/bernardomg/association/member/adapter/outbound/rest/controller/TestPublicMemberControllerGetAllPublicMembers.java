
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
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
import com.fasterxml.jackson.databind.SerializationFeature;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicMemberController - getAllPublicMembers")
class TestPublicMemberControllerGetAllPublicMembers {

    @InjectMocks
    private PublicMemberController       controller;

    private MockMvc                      mockMvc;

    @Mock
    private PublicMemberService          service;

    @Mock
    private PublicMemberStatusAuthorizer statusAuthorizer;

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
    @DisplayName("When the public member list is requested, then the members are returned")
    void testGetAllPublicMembers() throws Exception {
        final Page<PublicMember> existing;
        final Pagination         pagination;
        final Sorting            sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(PublicMembers.active()), 1, 1, 1, 1, 1, true, true, sorting);
        given(service.getAll(PublicMemberFilters.active(), pagination, sorting)).willReturn(existing);

        // WHEN
        mockMvc.perform(get("/member").contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content[0].memberNumber").value((int) MemberConstants.MEMBER_NUMBER));
    }

    @Test
    @DisplayName("When inactive read permission is missing, then the all filter is rejected")
    void testGetAllPublicMembers_AllFilterWithoutPermission() {
        final ThrowingCallable action;

        // GIVEN
        doThrow(new AccessDeniedException("No permission to read inactive members")).when(statusAuthorizer)
            .checkCanRead(MemberStatus.ALL);

        // WHEN
        action = () -> controller.getAllPublicMembers(1, 10, List.of(), MemberStatusDto.ALL, null);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("all filter is denied")
            .isInstanceOf(AccessDeniedException.class);
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("When the member list is filtered by inactive status, then the filter is accepted")
    void testGetAllPublicMembers_Inactive() throws Exception {
        final Page<PublicMember> existing;
        final PublicMemberFilter filter;
        final Pagination         pagination;
        final Sorting            sorting;

        // GIVEN
        filter = PublicMemberFilters.inactive();
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(PublicMembers.inactive()), 1, 1, 1, 1, 1, true, true, sorting);
        given(service.getAll(filter, pagination, sorting)).willReturn(existing);

        // WHEN
        mockMvc.perform(get("/member").param("status", "INACTIVE")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content[0].active").value(false));

        verify(statusAuthorizer).checkCanRead(MemberStatus.INACTIVE);
    }

    @Test
    @DisplayName("When inactive read permission is missing, then the inactive filter is rejected")
    void testGetAllPublicMembers_InactiveFilterWithoutPermission() {
        final ThrowingCallable action;

        // GIVEN
        doThrow(new AccessDeniedException("No permission to read inactive members")).when(statusAuthorizer)
            .checkCanRead(MemberStatus.INACTIVE);

        // WHEN
        action = () -> controller.getAllPublicMembers(1, 10, List.of(), MemberStatusDto.INACTIVE, null);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("inactive filter is denied")
            .isInstanceOf(AccessDeniedException.class);
        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("When the public member list is generated, then it contains the joined month and total paid months")
    void testGetAllPublicMembers_PaidMembership() throws Exception {
        final Page<PublicMember> existing;
        final Pagination         pagination;
        final Sorting            sorting;
        final PublicMember       member;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        // TODO: don't parse like this
        member = PublicMembers.withMembership(FeeConstants.JANUARY_DATE, 3L);
        existing = new Page<>(List.of(member), 1, 1, 1, 1, 1, true, true, sorting);
        given(service.getAll(PublicMemberFilters.active(), pagination, sorting)).willReturn(existing);

        // WHEN
        mockMvc.perform(get("/member").contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].joined").value("2020-01-01T00:00:00Z"))
            .andExpect(jsonPath("$.content[0].totalMonths").value(3));
    }

}
