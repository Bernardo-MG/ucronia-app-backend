
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.key.adapter.outbound.rest.controller.KeyController;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.model.KeyAssignment.AssignedMember;
import com.bernardomg.association.key.usecase.service.KeyService;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.security.domain.permission.constant.Actions;

class TestKeyAssignmentsController {

    @Test
    void requiresKeyReadPermission() throws Exception {
        final var annotation = KeyController.class
            .getMethod("getKeyAssignments", Integer.class, Integer.class, List.class)
            .getAnnotation(RequireResourceAuthorization.class);
        assertEquals("KEY", annotation.resource());
        assertEquals(Actions.READ, annotation.action());
    }

    @Test
    void returnsOnlyAssignmentInformationForEveryKey() throws Exception {
        final var                 service  = mock(KeyService.class);
        final var                 assigned = new KeyAssignment(1L, false, "Assigned unavailable",
            Optional.of(new AssignedMember(10L, 37L, "Ana")));
        final var                 free     = new KeyAssignment(2L, true, "Unassigned", Optional.empty());
        final Page<KeyAssignment> page     = new Page<>(List.of(assigned, free), 0, 0, 0, 0, 0, false, false,
            Sorting.unsorted());
        when(service.getAssignments(any(), any())).thenReturn(page);
        final var validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();
        MockMvcBuilders.standaloneSetup(new KeyController(service))
            .setValidator(validator)
            .build()
            .perform(get("/profile/key/assignments").param("page", "1")
                .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].member.number").value(10))
            .andExpect(jsonPath("$.content[0].member.memberNumber").value(37))
            .andExpect(jsonPath("$.content[0].member.name").value("Ana"))
            .andExpect(jsonPath("$.content[0].member.identifier").doesNotExist())
            .andExpect(jsonPath("$.content[1].member").value(nullValue()));
    }
}
