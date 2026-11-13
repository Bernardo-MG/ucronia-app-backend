
package com.bernardomg.association.contacts.adapter.outbound.rest.controller;

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

import com.bernardomg.association.contacts.domain.model.ContactInfo;
import com.bernardomg.association.contacts.test.factory.ContactSettingsConstants;
import com.bernardomg.association.contacts.usecase.service.ContactInfoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContactInfoController")
class TestContactInfoController {

    private MockMvc            mockMvc;

    @Mock
    private ContactInfoService service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ContactInfoController(service))
            .build();
    }

    @Test
    @DisplayName("When requesting contact info, it is returned")
    void testGetContactInfo() throws Exception {
        // GIVEN
        given(service.getContactInfo()).willReturn(new ContactInfo(ContactSettingsConstants.GOOGLE_MAPS_CODE,
            ContactSettingsConstants.TEAMUP_CODE, ContactSettingsConstants.EMAIL_CODE,
            ContactSettingsConstants.INSTAGRAM_CODE, ContactSettingsConstants.TELEGRAM_CODE));

        // WHEN + THEN
        mockMvc.perform(get("/contact").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.map", equalTo(ContactSettingsConstants.GOOGLE_MAPS_CODE)))
            .andExpect(jsonPath("$.content.calendar", equalTo(ContactSettingsConstants.TEAMUP_CODE)))
            .andExpect(jsonPath("$.content.email", equalTo(ContactSettingsConstants.EMAIL_CODE)))
            .andExpect(jsonPath("$.content.instagram", equalTo(ContactSettingsConstants.INSTAGRAM_CODE)))
            .andExpect(jsonPath("$.content.telegram", equalTo(ContactSettingsConstants.TELEGRAM_CODE)));
    }

}
