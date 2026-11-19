
package com.bernardomg.association.calendar.activity.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

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

import com.bernardomg.association.calendar.activity.usecase.service.ActivityService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityController - getAllActivities")
class TestActivityControllerSorting {

    @InjectMocks
    private ActivityController controller;

    private MockMvc            mockMvc;

    @Mock
    private ActivityService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When sorting by title ascending, then it is accepted")
    void testGetAllActivities_TitleAsc() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/activity").param("page", "1")
            .param("size", "10")
            .param("sort", "title|asc")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("When sorting by title descending, then it is accepted")
    void testGetAllActivities_TitleDesc() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/activity").param("page", "1")
            .param("size", "10")
            .param("sort", "title|desc")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
