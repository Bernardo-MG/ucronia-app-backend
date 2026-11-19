
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.SoftAssertions;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
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
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.key.adapter.outbound.rest.controller.KeyAssignmentController;
import com.bernardomg.association.key.adapter.outbound.rest.controller.KeyAssignmentExceptionHandler;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyAssignmentCreationDto;
import com.bernardomg.association.key.domain.exception.KeyAssignmentConflictException;
import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.usecase.service.KeyAssignmentService;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.domain.permission.constant.Actions;

@ExtendWith(MockitoExtension.class)
@DisplayName("KeyAssignmentController - createKeyAssignment")
class TestKeyAssignmentControllerCreateKeyAssignment {

    @InjectMocks
    private KeyAssignmentController controller;

    private MockMvc                 mockMvc;

    @Mock
    private KeyAssignmentService    service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new KeyAssignmentExceptionHandler())
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When the assignment already exists, then a conflict response is returned")
    void testCreateConflict() throws Exception {
        // GIVEN
        given(service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER))
            .willThrow(new KeyAssignmentConflictException(KeyConstants.NUMBER));

        // WHEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                    {"number": %d, "profile": %d}
                    """, KeyConstants.NUMBER, ProfileConstants.NUMBER)))
            // THEN
            .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("When creating an assignment, then it is accepted")
    void testCreateKeyAssignment() throws Exception {
        // GIVEN
        given(service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER)).willReturn(KeyAssignments.assigned());

        // WHEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                    {"number": %d, "profile": %d}
                    """, KeyConstants.NUMBER, ProfileConstants.NUMBER)))
            // THEN
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.content.number").value((int) KeyConstants.NUMBER))
            .andExpect(jsonPath("$.content.profile.number").value((int) ProfileConstants.NUMBER));

        verify(service).create(KeyConstants.NUMBER, ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When the key does not exist, then a not-found response is returned")
    void testCreateMissingKey() throws Exception {
        // GIVEN
        given(service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER))
            .willThrow(new MissingKeyException(KeyConstants.NUMBER));

        // WHEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                    {"number": %d, "profile": %d}
                    """, KeyConstants.NUMBER, ProfileConstants.NUMBER)))
            // THEN
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When the assignment request omits the key number, then it is rejected")
    void testCreateMissingKeyField() throws Exception {
        // WHEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"profile": 10}
                    """))
            // THEN
            .andExpect(status().isBadRequest());

        verify(service, never()).create(anyLong(), anyLong());
    }

    @Test
    @DisplayName("When the profile does not exist, then a not-found response is returned")
    void testCreateMissingProfile() throws Exception {
        // GIVEN
        given(service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER))
            .willThrow(new MissingProfileException(ProfileConstants.NUMBER));

        // WHEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                    {"number": %d, "profile": %d}
                    """, KeyConstants.NUMBER, ProfileConstants.NUMBER)))
            // THEN
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When the assignment request omits the profile number, then it is rejected")
    void testCreateMissingProfileField() throws Exception {
        // WHEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"number": 1}
                    """))
            // THEN
            .andExpect(status().isBadRequest());

        verify(service, never()).create(anyLong(), anyLong());
    }

    @Test
    @DisplayName("When creation authorization is inspected, then the create action is required")
    void testPermissionCreate() throws Exception {
        final RequireResourceAuthorization authorization;

        // WHEN
        authorization = KeyAssignmentController.class.getMethod("createKeyAssignment", KeyAssignmentCreationDto.class)
            .getAnnotation(RequireResourceAuthorization.class);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(authorization.resource())
                .as("Uses the key assignment resource for create authorization")
                .isEqualTo("KEY_ASSIGNMENT");
            softly.assertThat(authorization.action())
                .as("Requires create permission for key assignment creation")
                .isEqualTo(Actions.CREATE);
        });
    }

}
