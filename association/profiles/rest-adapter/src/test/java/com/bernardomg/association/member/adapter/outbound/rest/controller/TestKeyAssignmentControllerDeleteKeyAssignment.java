
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.bernardomg.association.key.domain.exception.MissingKeyAssignmentException;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.usecase.service.KeyAssignmentService;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.security.domain.permission.constant.Actions;

@ExtendWith(MockitoExtension.class)
@DisplayName("KeyAssignmentController - deleteKeyAssignment")
class TestKeyAssignmentControllerDeleteKeyAssignment {

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
    @DisplayName("When deleting an assignment, then it is accepted")
    void testDeleteKeyAssignment() throws Exception {
        // GIVEN
        given(service.delete(KeyConstants.NUMBER)).willReturn(KeyAssignments.assigned());

        // WHEN
        mockMvc
            .perform(delete("/profile/key/assignments/{number}", KeyConstants.NUMBER)
                .contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value((int) KeyConstants.NUMBER))
            .andExpect(jsonPath("$.content.profile.number").value((int) ProfileConstants.NUMBER));

        verify(service).delete(KeyConstants.NUMBER);
    }

    @Test
    @DisplayName("When an absent assignment is deleted, then not found is reported")
    void testDeleteMissingAssignment() throws Exception {
        // GIVEN
        given(service.delete(KeyConstants.NUMBER)).willThrow(new MissingKeyAssignmentException(KeyConstants.NUMBER));

        // WHEN
        mockMvc.perform(delete("/profile/key/assignments/{number}", KeyConstants.NUMBER))
            // THEN
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When deletion authorization is inspected, then the delete action is required")
    void testPermissionDelete() throws Exception {
        final RequireResourceAuthorization authorization;

        // WHEN
        authorization = KeyAssignmentController.class.getMethod("deleteKeyAssignment", Long.class)
            .getAnnotation(RequireResourceAuthorization.class);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(authorization.resource())
                .as("Uses the key assignment resource for delete authorization")
                .isEqualTo("KEY_ASSIGNMENT");
            softly.assertThat(authorization.action())
                .as("Requires delete permission for key assignment deletion")
                .isEqualTo(Actions.DELETE);
        });
    }

}
