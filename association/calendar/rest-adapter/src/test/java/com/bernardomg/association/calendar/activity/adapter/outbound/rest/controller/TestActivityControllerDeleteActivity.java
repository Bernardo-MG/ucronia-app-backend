
package com.bernardomg.association.calendar.activity.adapter.outbound.rest.controller;

import static com.bernardomg.testing.mvc.MockMvcTestSupport.standalone;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.bernardomg.association.calendar.activity.test.configuration.factory.Activities;
import com.bernardomg.association.calendar.activity.usecase.service.ActivityService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityController - deleteActivity")
class TestActivityControllerDeleteActivity {

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
    @DisplayName("When deleting an activity, then it is accepted")
    void testDeleteActivity() throws Exception {
        // GIVEN
        given(service.delete(1L)).willReturn(Activities.singleDay());

        // WHEN
        mockMvc.perform(delete("/activity/1").contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number").exists());
    }

}
