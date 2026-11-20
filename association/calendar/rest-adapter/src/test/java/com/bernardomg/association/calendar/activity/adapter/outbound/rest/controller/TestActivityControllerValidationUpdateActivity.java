
package com.bernardomg.association.calendar.activity.adapter.outbound.rest.controller;

import static com.bernardomg.testing.mvc.MockMvcTestSupport.standalone;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.bernardomg.association.calendar.activity.usecase.service.ActivityService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ActivityController - updateActivity")
class TestActivityControllerValidationUpdateActivity {

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
    @DisplayName("When updating an activity without title, then it is rejected")
    void testUpdateActivityMissingTitle() throws Exception {
        // WHEN
        mockMvc.perform(put("/activity/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {
                      "dates":[
                        {
                          "start":"2025-08-01T00:00:00Z",
                          "end":"2025-08-01T02:00:00Z"
                        }
                      ]
                    }
                    """))
            // THEN
            .andExpect(status().isBadRequest());
    }

}
