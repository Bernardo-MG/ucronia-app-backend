
package com.bernardomg.association.security.user.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.bernardomg.association.security.user.test.configuration.factory.UserConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfileConstants;
import com.bernardomg.association.security.user.test.configuration.factory.UserProfiles;
import com.bernardomg.association.security.user.usecase.service.UserProfileService;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserProfileController - assignProfileToUser")
class TestUserProfileControllerAssignProfileToUser {

    @InjectMocks
    private UserProfileController controller;

    private MockMvc               mockMvc;

    @Mock
    private UserProfileService    service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("When assigning a profile to a user, then it is returned")
    void testAssignProfileToUser() throws Exception {
        // GIVEN
        given(service.assignProfile(UserConstants.USERNAME, UserProfileConstants.NUMBER))
            .willReturn(UserProfiles.valid());

        // WHEN
        mockMvc
            .perform(post("/security/user/{username}/profile/{memberNumber}", UserConstants.USERNAME,
                UserProfileConstants.NUMBER).contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.number", equalTo((int) UserProfileConstants.NUMBER)));
    }

}
