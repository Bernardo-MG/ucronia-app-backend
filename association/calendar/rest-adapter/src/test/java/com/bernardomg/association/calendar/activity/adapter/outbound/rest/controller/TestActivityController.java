
package com.bernardomg.association.calendar.activity.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.activity.usecase.service.ActivityService;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityController")
class TestActivityController {

    private MockMvc         mockMvc;

    @Mock
    private ActivityService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();

        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new ActivityController(service))
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When creating an activity with valid data, it is accepted")
    void testCreateActivity() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.create(any())).willReturn(Activities.singleDay());

        requestBody = """
                {
                    "title": "Activity",
                    "dates": [
                        {
                            "start": "2025-08-01T00:00:00Z",
                            "end": "2025-08-01T02:00:00Z"
                        }
                    ]
                }
                """;

        // WHEN + THEN
        mockMvc.perform(post("/activity").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists())
            .andExpect(jsonPath("$.content.title").exists());
    }

    @Test
    @DisplayName("When deleting an activity, it is accepted")
    void testDeleteActivity() throws Exception {
        // GIVEN
        given(service.delete(1L)).willReturn(Activities.singleDay());

        // WHEN + THEN
        mockMvc.perform(delete("/activity/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When there are activities, they are returned")
    void testGetAllActivities() throws Exception {
        // GIVEN
        given(service.getAll(eq(new Pagination(1, 10)), any()))
            .willReturn(new Page<>(List.of(Activities.singleDay()), 1, 1, 0, 0, 0, false, false, Sorting.unsorted()));

        // WHEN + THEN
        mockMvc.perform(get("/activity").param("page", "1")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("When the activity exists, it is returned")
    void testGetOneActivity() throws Exception {
        // GIVEN
        given(service.getOne(1L)).willReturn(Optional.of(Activities.singleDay()));

        // WHEN + THEN
        mockMvc.perform(get("/activity/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists());
    }

    @Test
    @DisplayName("When updating an activity with valid data, it is accepted")
    void testUpdateActivity() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(Activities.singleDay());

        requestBody = """
                {
                    "title": "Activity Updated",
                    "dates": [
                        {
                            "start": "2025-08-01T00:00:00Z",
                            "end": "2025-08-01T02:00:00Z"
                        }
                    ]
                }
                """;

        // WHEN + THEN
        mockMvc.perform(put("/activity/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.title").exists());
    }

}
