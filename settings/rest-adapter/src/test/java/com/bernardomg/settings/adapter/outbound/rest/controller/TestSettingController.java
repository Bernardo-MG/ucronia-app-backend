package com.bernardomg.settings.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.usecase.service.SettingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("SettingController")
class TestSettingController {

    private static final String CODE  = "code";

    private static final String VALUE = "value";

    private MockMvc        mockMvc;

    @Mock
    private SettingService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SettingController(service))
            .build();
    }

    @Test
    @DisplayName("When requesting all settings, they are returned")
    void testGetAllSettings() throws Exception {
        // GIVEN
        given(service.getAll()).willReturn(List.of(new Setting("string", "a", VALUE), new Setting("string", "b", VALUE)));

        // WHEN + THEN
        mockMvc.perform(get("/settings").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content").isArray())
            .andExpect(jsonPath("$.content[0].code").exists());
    }

    @Test
    @DisplayName("When requesting an existing setting, it is returned")
    void testGetSettingByCode() throws Exception {
        // GIVEN
        given(service.getOne(CODE)).willReturn(Optional.of(new Setting("string", CODE, VALUE)));

        // WHEN + THEN
        mockMvc.perform(get("/settings/{code}", CODE).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.code", equalTo(CODE)))
            .andExpect(jsonPath("$.content.value", equalTo(VALUE)));
    }

    @Test
    @DisplayName("When updating a setting with valid data, it is accepted")
    void testUpdateSetting_ValidData() throws Exception {
        final String requestBody;

        // GIVEN
        given(service.update(CODE, VALUE)).willReturn(new Setting("string", CODE, VALUE));

        requestBody = String.format("""
                {
                    "value": "%s"
                }
            """, VALUE);

        // WHEN + THEN
        mockMvc.perform(put("/settings/{code}", CODE).contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.code", equalTo(CODE)))
            .andExpect(jsonPath("$.content.value", equalTo(VALUE)));
    }

}
