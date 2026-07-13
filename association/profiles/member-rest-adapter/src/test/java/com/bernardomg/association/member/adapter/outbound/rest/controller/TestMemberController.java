
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
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
import java.util.Set;

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

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.association.profile.domain.model.Name;

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
    @DisplayName("When creating a member with address, it is accepted")
    void testCreateMemberWithAddress() throws Exception {
        final String requestBody;

        given(service.create(any())).willReturn(new Member(Optional.empty(), 1L, new Name("Alice", "Brown"),
            Optional.empty(), List.of(), Optional.of("123 Main St"), Optional.empty(), true, true,
            new Member.FeeType(1L, "Standard", 10F), Set.of(Member.PROFILE_TYPE)));

        requestBody = """
                {
                    "name": {
                        "firstName": "Alice",
                        "lastName": "Brown"
                    },
                    "address": "123 Main St"
                }
                """;

        mockMvc.perform(post("/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.address", equalTo("123 Main St")));
    }

    @Test
    @DisplayName("When creating a member with valid data, it is accepted")
    void testCreateMemberWithValidData() throws Exception {
        final String requestBody;

        given(service.create(any())).willReturn(
            new Member(Optional.empty(), 1L, new Name("John", "Doe"), Optional.empty(), List.of(), Optional.empty(),
                Optional.empty(), true, true, new Member.FeeType(1L, "Standard", 10F), Set.of(Member.PROFILE_TYPE)));

        requestBody = """
                {
                    "name": {
                        "firstName": "John",
                        "lastName": "Doe"
                    }
                }
                """;

        mockMvc.perform(post("/member").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name.firstName", equalTo("John")))
            .andExpect(jsonPath("$.name.lastName", equalTo("Doe")))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("When the member is deleted, it is accepted")
    void testDeleteMember() throws Exception {
        given(service.delete(any())).willReturn(
            new Member(Optional.empty(), 1L, new Name("John", "Doe"), Optional.empty(), List.of(), Optional.empty(),
                Optional.empty(), true, true, new Member.FeeType(1L, "Standard", 10F), Set.of(Member.PROFILE_TYPE)));

        mockMvc.perform(delete("/member/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("When the member exists, it is returned")
    void testGetMemberByNumber() throws Exception {
        given(service.getOne(1L)).willReturn(Optional
            .of(new Member(Optional.empty(), 1L, new Name("John", "Doe"), Optional.empty(), List.of(), Optional.empty(),
                Optional.empty(), true, true, new Member.FeeType(1L, "Standard", 10F), Set.of(Member.PROFILE_TYPE))));

        mockMvc.perform(get("/member/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").exists());
    }

    @Test
    @DisplayName("When patching a valid member, it is accepted")
    void testPatchMemberWithValidData() throws Exception {
        final String requestBody;

        given(service.patch(any())).willReturn(
            new Member(Optional.empty(), 1L, new Name("Jane", "Smith"), Optional.empty(), List.of(), Optional.empty(),
                Optional.empty(), true, true, new Member.FeeType(1L, "Standard", 10F), Set.of(Member.PROFILE_TYPE)));

        requestBody = """
                {
                    "name": {
                        "firstName": "Jane",
                        "lastName": "Smith"
                    }
                }
                """;

        mockMvc.perform(patch("/member/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name.firstName", equalTo("Jane")));
    }

    @Test
    @DisplayName("When updating a valid member, it is accepted")
    void testUpdateMemberWithValidData() throws Exception {
        final String requestBody;

        given(service.update(any())).willReturn(new Member(Optional.empty(), 1L, new Name("Richard", "Johnson"),
            Optional.empty(), List.of(), Optional.empty(), Optional.empty(), true, true,
            new Member.FeeType(1L, "Standard", 10F), Set.of(Member.PROFILE_TYPE)));

        requestBody = """
                {
                    "name": {
                        "firstName": "Richard",
                        "lastName": "Johnson"
                    }
                }
                """;

        mockMvc.perform(put("/member/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.name.firstName", equalTo("Richard")));
    }

}
