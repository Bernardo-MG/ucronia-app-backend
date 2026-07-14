
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.bernardomg.association.member.usecase.service.MemberService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController - Sorting")
class TestMemberControllerSorting {

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
    @DisplayName("When sorting by last name descending, it is accepted")
    void testGetAllMembers_LastNameSort() throws Exception {
        given(service.getAll(any(), any(), any()))
            .willReturn(new Page<>(List.of(), 10, 0, 0, 0, 0, true, true, Sorting.unsorted()));

        mockMvc.perform(get("/member").param("page", "1")
            .param("size", "10")
            .param("sort", "lastName|desc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When sorting by number ascending, it is accepted")
    void testGetAllMembers_NumberSort() throws Exception {
        given(service.getAll(any(), any(), any()))
            .willReturn(new Page<>(List.of(), 10, 0, 0, 0, 0, true, true, Sorting.unsorted()));

        mockMvc.perform(get("/member").param("page", "1")
            .param("size", "10")
            .param("sort", "number|asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content", isA(java.util.ArrayList.class)));
    }

    @Test
    @DisplayName("When sorting by first name ascending, it is accepted")
    void testGetAllMembers_ValidSort() throws Exception {
        given(service.getAll(any(), any(), any()))
            .willReturn(new Page<>(List.of(), 10, 0, 0, 0, 0, true, true, Sorting.unsorted()));

        mockMvc.perform(get("/member").param("page", "1")
            .param("size", "10")
            .param("sort", "firstName|asc")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
