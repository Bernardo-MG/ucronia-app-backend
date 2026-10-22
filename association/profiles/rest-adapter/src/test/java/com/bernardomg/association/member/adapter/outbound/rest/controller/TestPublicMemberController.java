
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

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

import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.PublicMemberFilters;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.association.member.usecase.service.PublicMemberService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicMemberController")
class TestPublicMemberController {

    private MockMvc             mockMvc;

    @Mock
    private PublicMemberService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new PublicMemberController(service))
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
    @DisplayName("The public member response contains its numbers")
    void testGetPublicMemberByNumber() throws Exception {
        // GIVEN
        given(service.getOne(MemberConstants.NUMBER)).willReturn(Optional.of(PublicMembers.valid()));

        // WHEN + THEN
        mockMvc.perform(get("/member/{number}", MemberConstants.NUMBER).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content.memberNumber").value((int) MemberConstants.MEMBER_NUMBER));
    }

}
