
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

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
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.model.KeyAssignment.AssignedProfile;
import com.bernardomg.association.key.usecase.service.KeyAssignmentService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@ExtendWith(MockitoExtension.class)
@DisplayName("KeyAssignmentController - getKeyAssignments")
class TestKeyAssignmentControllerGetKeyAssignments {

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
    @DisplayName("When assignments exist, then assigned and unassigned keys are returned")
    void testGetKeyAssignments() throws Exception {
        final KeyAssignment       assigned;
        final KeyAssignment       unassigned;
        final Page<KeyAssignment> existing;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        assigned = new KeyAssignment(1L, false, "Assigned unavailable", Optional.of(new AssignedProfile(10L, "Ana")));
        unassigned = new KeyAssignment(2L, true, "Unassigned", Optional.empty());
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(assigned, unassigned), 0, 0, 0, 0, 0, false, false, sorting);
        given(service.getAssignments(pagination, sorting)).willReturn(existing);

        // WHEN
        mockMvc.perform(get("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .param("page", "1")
            .param("size", "10"))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value(1))
            .andExpect(jsonPath("$.content[0].profile.number").value(10))
            .andExpect(jsonPath("$.content[0].profile.name").value("Ana"))
            .andExpect(jsonPath("$.content[0].profile.identifier").doesNotExist())
            .andExpect(jsonPath("$.content[1].number").value(2))
            .andExpect(jsonPath("$.content[1].profile").value(nullValue()));

        verify(service).getAssignments(pagination, sorting);
    }

    @Test
    @DisplayName("When no assignments exist, then an empty page is returned")
    void testGetKeyAssignmentsEmpty() throws Exception {
        final Page<KeyAssignment> existing;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(service.getAssignments(pagination, sorting)).willReturn(existing);

        // WHEN
        mockMvc.perform(get("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON))
            // THEN
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isEmpty());

        verify(service).getAssignments(pagination, sorting);
    }

    @Test
    @DisplayName("When read authorization is inspected, then the read action is required")
    void testRequiresKeyAssignmentReadPermission() throws Exception {
        final RequireResourceAuthorization authorization;

        // WHEN
        authorization = KeyAssignmentController.class
            .getMethod("getKeyAssignments", Integer.class, Integer.class, List.class)
            .getAnnotation(RequireResourceAuthorization.class);

        // THEN
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(authorization.resource())
                .as("Uses the key assignment resource for read authorization")
                .isEqualTo("KEY_ASSIGNMENT");
            softly.assertThat(authorization.action())
                .as("Requires read permission for key assignment retrieval")
                .isEqualTo(Actions.READ);
        });
    }

}
