
package com.bernardomg.settings.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.settings.test.factory.SettingConstants;
import com.bernardomg.settings.test.factory.Settings;
import com.bernardomg.settings.usecase.service.SettingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("SettingController - updateSetting")
class TestSettingControllerUpdateSetting {

    @InjectMocks
    private SettingController controller;

    private MockMvc        mockMvc;

    @Mock
    private SettingService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("When updating a setting with valid data, then it is accepted")
    void testUpdateSetting_ValidData() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(SettingConstants.CODE, SettingConstants.STRING_VALUE)).willReturn(Settings.stringValue());

        requestBody = String.format("""
                    {
                        "value": "%s"
                    }
                """, SettingConstants.STRING_VALUE);

        // WHEN
        mockMvc.perform(put("/settings/{code}", SettingConstants.CODE).contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.code", equalTo(SettingConstants.CODE)))
            .andExpect(jsonPath("$.content.value", equalTo(SettingConstants.STRING_VALUE)));
    }

}
