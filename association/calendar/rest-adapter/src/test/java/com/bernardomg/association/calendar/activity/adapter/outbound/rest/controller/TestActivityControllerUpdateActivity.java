
package com.bernardomg.association.calendar.activity.adapter.outbound.rest.controller;

import static com.bernardomg.testing.mvc.MockMvcTestSupport.standalone;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
@DisplayName("ActivityController - updateActivity")
class TestActivityControllerUpdateActivity {

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
    @DisplayName("When updating an activity with valid data, then it is accepted")
    void testUpdateActivity() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(any())).willReturn(Activities.singleDay());

        requestBody = """
                {
                    "title": "Activity Updated",
                    "image": "http://somewhere.com/image.jpg",
                    "dates": [
                        {
                            "start": "2025-08-01T00:00:00Z",
                            "end": "2025-08-01T02:00:00Z"
                        }
                    ]
                }
                """;

        // WHEN
        mockMvc.perform(put("/activity/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.title").exists());
    }

}
