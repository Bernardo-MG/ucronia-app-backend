
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyAssignmentUpdateDto;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.usecase.service.KeyAssignmentService;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.domain.permission.constant.Actions;

@ExtendWith(MockitoExtension.class)
@DisplayName("KeyAssignmentController - updateKeyAssignment")
class TestKeyAssignmentControllerUpdateKeyAssignment {

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
    @DisplayName("When update authorization is inspected, then the update action is required")
    void testPermissionUpdate() throws Exception {
        final RequireResourceAuthorization authorization;

        // WHEN
        authorization = KeyAssignmentController.class
            .getMethod("updateKeyAssignment", Long.class, KeyAssignmentUpdateDto.class)
            .getAnnotation(RequireResourceAuthorization.class);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(authorization.resource())
                .as("Uses the key assignment resource for update authorization")
                .isEqualTo("KEY_ASSIGNMENT");
            softly.assertThat(authorization.action())
                .as("Requires update permission for key assignment updates")
                .isEqualTo(Actions.UPDATE);
        });
    }

    @Test
    @DisplayName("When updating an assignment, then it is accepted")
    void testUpdateKeyAssignment() throws Exception {
        // GIVEN
        given(service.update(KeyConstants.NUMBER, ProfileConstants.NUMBER)).willReturn(KeyAssignments.assigned());

        // WHEN
        mockMvc
            .perform(
                put("/profile/key/assignments/{number}", KeyConstants.NUMBER).contentType(MediaType.APPLICATION_JSON)
                    .content(String.format("""
                            {"profile": %d}
                            """, ProfileConstants.NUMBER)))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value((int) KeyConstants.NUMBER))
            .andExpect(jsonPath("$.content.profile.number").value((int) ProfileConstants.NUMBER));

        verify(service).update(KeyConstants.NUMBER, ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When the assignment request omits the profile number, then it is rejected")
    void testUpdateMissingProfile() throws Exception {
        // WHEN
        mockMvc.perform(put("/profile/key/assignments/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {}
                    """))
            // THEN
            .andExpect(status().isBadRequest());

        verify(service, never()).update(anyLong(), anyLong());
    }

    @Test
    @DisplayName("When the assignment request has a null profile number, then it is rejected")
    void testUpdateNullMember() throws Exception {
        // WHEN
        mockMvc.perform(put("/profile/key/assignments/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"profile": null}
                    """))
            // THEN
            .andExpect(status().isBadRequest());

        verify(service, never()).update(anyLong(), anyLong());
    }

}
