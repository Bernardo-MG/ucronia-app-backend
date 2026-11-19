
package com.bernardomg.settings.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.settings.test.factory.Settings;
import com.bernardomg.settings.usecase.service.SettingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("SettingController - getAllSettings")
class TestSettingControllerGetAllSettings {

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
    @DisplayName("When requesting all settings, then they are returned")
    void testGetAllSettings() throws Exception {
        // GIVEN
        given(service.getAll()).willReturn(List.of(Settings.first(), Settings.second()));

        // WHEN
        mockMvc.perform(get("/settings").contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].code").exists());
    }

}
