
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController")
class TestMemberController {

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
    @DisplayName("When creating a member with valid data, it is accepted")
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

        // WHEN + THEN
        mockMvc.perform(post("/profile/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name.firstName", equalTo(ProfileConstants.FIRST_NAME)))
            .andExpect(jsonPath("$.content.name.lastName", equalTo(ProfileConstants.LAST_NAME)))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When creating a member with address, it is accepted")
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

        // WHEN + THEN
        mockMvc.perform(post("/profile/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.address", equalTo(MemberConstants.ADDRESS)));
    }

    @Test
    @DisplayName("When the member is deleted, it is accepted")
    void testDeleteMember() throws Exception {
        // GIVEN
        given(service.delete(anyLong())).willReturn(Members.active());

        // WHEN + THEN
        mockMvc.perform(delete("/profile/member/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When the members exists, they are returned")
    void testGetAllMembers() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(Members.active()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/profile/member").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the member exists, it is returned")
    void testGetMemberByNumber() throws Exception {
        // GIVEN
        given(service.getOne(MemberConstants.NUMBER)).willReturn(Optional.of(Members.active()));

        // WHEN + THEN
        mockMvc.perform(get("/profile/member/{number}", MemberConstants.NUMBER).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When patching a valid member, it is accepted")
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

        // WHEN + THEN
        mockMvc.perform(patch("/profile/member/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name.firstName", equalTo("Name 123")));
    }

    @Test
    @DisplayName("When updating a valid member, it is accepted")
    void testUpdateMember() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(Members.nameChange());

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

        // WHEN + THEN
        mockMvc.perform(put("/profile/member/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.name.firstName", equalTo("Name 123")));
    }

}
