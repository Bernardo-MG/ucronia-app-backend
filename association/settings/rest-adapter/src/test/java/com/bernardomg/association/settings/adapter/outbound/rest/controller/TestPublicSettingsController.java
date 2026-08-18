
package com.bernardomg.association.settings.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.association.settings.domain.model.PublicSettings;
import com.bernardomg.association.settings.test.factory.AssociationSettingsConstants;
import com.bernardomg.association.settings.usecase.service.PublicSettingsService;

@ExtendWith(MockitoExtension.class)
@DisplayName("PublicSettingsController")
class TestPublicSettingsController {

    private MockMvc               mockMvc;

    @Mock
    private PublicSettingsService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PublicSettingsController(service))
            .build();
    }

    @Test
    @DisplayName("When requesting public settings, they are returned")
    void testGetPublicSettings() throws Exception {
        // GIVEN
        given(service.getSettings()).willReturn(
            new PublicSettings(AssociationSettingsConstants.GOOGLE_MAPS_CODE, AssociationSettingsConstants.TEAMUP_CODE,
                AssociationSettingsConstants.EMAIL_CODE, AssociationSettingsConstants.INSTAGRAM_CODE));

        // WHEN + THEN
        mockMvc.perform(get("/settings/public").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.map", equalTo(AssociationSettingsConstants.GOOGLE_MAPS_CODE)))
            .andExpect(jsonPath("$.content.calendar", equalTo(AssociationSettingsConstants.TEAMUP_CODE)))
            .andExpect(jsonPath("$.content.email", equalTo(AssociationSettingsConstants.EMAIL_CODE)))
            .andExpect(jsonPath("$.content.instagram", equalTo(AssociationSettingsConstants.INSTAGRAM_CODE)));
    }

}
