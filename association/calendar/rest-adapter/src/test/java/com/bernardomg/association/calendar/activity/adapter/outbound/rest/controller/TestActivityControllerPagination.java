
package com.bernardomg.association.calendar.activity.adapter.outbound.rest.controller;

import static com.bernardomg.testing.mvc.MockMvcBuilder.standalone;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bernardomg.association.calendar.activity.usecase.service.ActivityService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityController - getAllActivities")
class TestActivityControllerPagination {

    @InjectMocks
    private ActivityController controller;

    private MockMvc            mockMvc;

    @Mock
    private ActivityService    service;

    @BeforeEach
    void setUp() {
        mockMvc = standalone(controller).build();
    }

    @Test
    @DisplayName("When querying another page, then it is accepted")
    void testGetAllActivities_AnotherPage() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(2, 5)), any()))
            .willReturn(new Page<>(List.of(), 10, 5, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/activity").param("page", "2")
            .param("size", "5")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When querying the first page, then it is accepted")
    void testGetAllActivities_FirstPage() throws Exception {
        // GIVEN
        given(service.getAll(any(), eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(), 10, 5, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN
        mockMvc.perform(get("/activity").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk());
    }

}
