
package com.bernardomg.settings.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

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
@DisplayName("SettingController - getSettingByCode")
class TestSettingControllerGetSettingByCode {

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
    @DisplayName("When requesting an existing setting, then it is returned")
    void testGetSettingByCode() throws Exception {
        // GIVEN
        given(service.getOne(SettingConstants.CODE)).willReturn(Optional.of(Settings.stringValue()));

        // WHEN
        mockMvc.perform(get("/settings/{code}", SettingConstants.CODE).contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.code", equalTo(SettingConstants.CODE)))
            .andExpect(jsonPath("$.content.value", equalTo(SettingConstants.STRING_VALUE)));
    }

}
