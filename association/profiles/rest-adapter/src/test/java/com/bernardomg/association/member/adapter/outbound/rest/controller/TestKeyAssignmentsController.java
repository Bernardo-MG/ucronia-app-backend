
package com.bernardomg.association.member.adapter.outbound.rest.controller;

import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bernardomg.association.key.adapter.outbound.rest.controller.KeyAssignmentController;
import com.bernardomg.association.key.adapter.outbound.rest.controller.KeyAssignmentExceptionHandler;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyAssignmentCreationDto;
import com.bernardomg.association.key.adapter.outbound.rest.dto.KeyAssignmentUpdateDto;
import com.bernardomg.association.key.domain.exception.KeyAssignmentConflictException;
import com.bernardomg.association.key.domain.exception.MissingKeyAssignmentException;
import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.domain.model.KeyAssignment.AssignedMember;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.usecase.service.KeyAssignmentService;
import com.bernardomg.association.member.domain.exception.MissingMemberException;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.framework.security.access.annotation.RequireResourceAuthorization;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.security.domain.permission.constant.Actions;

@ExtendWith(MockitoExtension.class)
@DisplayName("KeyAssignmentController")
class TestKeyAssignmentsController {

    private MockMvc              mockMvc;

    @Mock
    private KeyAssignmentService service;

    @BeforeEach
    void setUp() {
        final LocalValidatorFactoryBean validator;

        validator = new LocalValidatorFactoryBean();
        validator.setMessageInterpolator(new ParameterMessageInterpolator());
        validator.afterPropertiesSet();

        mockMvc = MockMvcBuilders.standaloneSetup(new KeyAssignmentController(service))
            .setControllerAdvice(new KeyAssignmentExceptionHandler())
            .setValidator(validator)
            .build();
    }

    @Test
    @DisplayName("When assignments exist, assigned and unassigned keys are returned")
    void testGetKeyAssignments() throws Exception {
        final KeyAssignment       assigned;
        final KeyAssignment       unassigned;
        final Page<KeyAssignment> existing;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        assigned = new KeyAssignment(1L, false, "Assigned unavailable",
            Optional.of(new AssignedMember(10L, 37L, "Ana")));
        unassigned = new KeyAssignment(2L, true, "Unassigned", Optional.empty());
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(assigned, unassigned), 0, 0, 0, 0, 0, false, false, sorting);
        given(service.getAssignments(pagination, sorting)).willReturn(existing);

        // WHEN + THEN
        mockMvc.perform(get("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .param("page", "1")
            .param("size", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].number").value(1))
            .andExpect(jsonPath("$.content[0].member.number").value(10))
            .andExpect(jsonPath("$.content[0].member.memberNumber").value(37))
            .andExpect(jsonPath("$.content[0].member.name").value("Ana"))
            .andExpect(jsonPath("$.content[0].member.identifier").doesNotExist())
            .andExpect(jsonPath("$.content[1].number").value(2))
            .andExpect(jsonPath("$.content[1].member").value(nullValue()));

        verify(service).getAssignments(pagination, sorting);
    }

    @Test
    @DisplayName("When no assignments exist, an empty page is returned")
    void testGetKeyAssignmentsEmpty() throws Exception {
        final Page<KeyAssignment> existing;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = new Pagination(1, 10);
        sorting = Sorting.unsorted();
        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(service.getAssignments(pagination, sorting)).willReturn(existing);

        // WHEN + THEN
        mockMvc.perform(get("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").isEmpty());

        verify(service).getAssignments(pagination, sorting);
    }

    @Test
    @DisplayName("Reading assignments requires key assignment read permission")
    void testRequiresKeyAssignmentReadPermission() throws Exception {
        final RequireResourceAuthorization authorization;

        // WHEN
        authorization = KeyAssignmentController.class
            .getMethod("getKeyAssignments", Integer.class, Integer.class, List.class)
            .getAnnotation(RequireResourceAuthorization.class);

        // THEN
        assertEquals("KEY_ASSIGNMENT", authorization.resource());
        assertEquals(Actions.READ, authorization.action());
    }

    @Test
    @DisplayName("When creating an assignment, it is accepted")
    void testCreateKeyAssignment() throws Exception {
        // GIVEN
        given(service.create(KeyConstants.NUMBER, MemberConstants.NUMBER)).willReturn(KeyAssignments.assigned());

        // WHEN + THEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                {"number": %d, "member": %d}
                """, KeyConstants.NUMBER, MemberConstants.NUMBER)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.content.number").value((int) KeyConstants.NUMBER))
            .andExpect(jsonPath("$.content.member.number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content.member.memberNumber").value((int) MemberConstants.MEMBER_NUMBER));

        verify(service).create(KeyConstants.NUMBER, MemberConstants.NUMBER);
    }

    @Test
    @DisplayName("When updating an assignment, it is accepted")
    void testUpdateKeyAssignment() throws Exception {
        // GIVEN
        given(service.update(KeyConstants.NUMBER, MemberConstants.NUMBER)).willReturn(KeyAssignments.assigned());

        // WHEN + THEN
        mockMvc.perform(put("/profile/key/assignments/{number}", KeyConstants.NUMBER)
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                {"member": %d}
                """, MemberConstants.NUMBER)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value((int) KeyConstants.NUMBER))
            .andExpect(jsonPath("$.content.member.number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content.member.memberNumber").value((int) MemberConstants.MEMBER_NUMBER));

        verify(service).update(KeyConstants.NUMBER, MemberConstants.NUMBER);
    }

    @Test
    @DisplayName("When deleting an assignment, it is accepted")
    void testDeleteKeyAssignment() throws Exception {
        // GIVEN
        given(service.delete(KeyConstants.NUMBER)).willReturn(KeyAssignments.assigned());

        // WHEN + THEN
        mockMvc.perform(delete("/profile/key/assignments/{number}", KeyConstants.NUMBER)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content.number").value((int) KeyConstants.NUMBER))
            .andExpect(jsonPath("$.content.member.number").value((int) MemberConstants.NUMBER))
            .andExpect(jsonPath("$.content.member.memberNumber").value((int) MemberConstants.MEMBER_NUMBER));

        verify(service).delete(KeyConstants.NUMBER);
    }

    @Test
    @DisplayName("An incomplete assignment request is rejected: CreateMissingKey")
    void testCreateMissingKeyField() throws Exception {
        // WHEN + THEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"member": 10}
                """))
            .andExpect(status().isBadRequest());

        verify(service, never()).create(anyLong(), anyLong());
    }

    @Test
    @DisplayName("An incomplete assignment request is rejected: CreateMissingMember")
    void testCreateMissingMemberField() throws Exception {
        // WHEN + THEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"number": 1}
                """))
            .andExpect(status().isBadRequest());

        verify(service, never()).create(anyLong(), anyLong());
    }

    @Test
    @DisplayName("An incomplete assignment request is rejected: UpdateMissingMember")
    void testUpdateMissingMember() throws Exception {
        // WHEN + THEN
        mockMvc.perform(put("/profile/key/assignments/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {}
                """))
            .andExpect(status().isBadRequest());

        verify(service, never()).update(anyLong(), anyLong());
    }

    @Test
    @DisplayName("An incomplete assignment request is rejected: UpdateNullMember")
    void testUpdateNullMember() throws Exception {
        // WHEN + THEN
        mockMvc.perform(put("/profile/key/assignments/1").contentType(MediaType.APPLICATION_JSON)
            .content("""
                {"member": null}
                """))
            .andExpect(status().isBadRequest());

        verify(service, never()).update(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Assignment creation reports MissingKey")
    void testCreateMissingKey() throws Exception {
        // GIVEN
        given(service.create(KeyConstants.NUMBER, MemberConstants.NUMBER))
            .willThrow(new MissingKeyException(KeyConstants.NUMBER));

        // WHEN + THEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                {"number": %d, "member": %d}
                """, KeyConstants.NUMBER, MemberConstants.NUMBER)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Assignment creation reports MissingMember")
    void testCreateMissingMember() throws Exception {
        // GIVEN
        given(service.create(KeyConstants.NUMBER, MemberConstants.NUMBER))
            .willThrow(new MissingMemberException(MemberConstants.NUMBER));

        // WHEN + THEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                {"number": %d, "member": %d}
                """, KeyConstants.NUMBER, MemberConstants.NUMBER)))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Assignment creation reports Conflict")
    void testCreateConflict() throws Exception {
        // GIVEN
        given(service.create(KeyConstants.NUMBER, MemberConstants.NUMBER))
            .willThrow(new KeyAssignmentConflictException(KeyConstants.NUMBER));

        // WHEN + THEN
        mockMvc.perform(post("/profile/key/assignments").contentType(MediaType.APPLICATION_JSON)
            .content(String.format("""
                {"number": %d, "member": %d}
                """, KeyConstants.NUMBER, MemberConstants.NUMBER)))
            .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deleting an absent assignment reports not found")
    void testDeleteMissingAssignment() throws Exception {
        // GIVEN
        given(service.delete(KeyConstants.NUMBER)).willThrow(new MissingKeyAssignmentException(KeyConstants.NUMBER));

        // WHEN + THEN
        mockMvc.perform(delete("/profile/key/assignments/{number}", KeyConstants.NUMBER))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("createKeyAssignment requires key assignment create permission")
    void testPermissionCreate() throws Exception {
        final RequireResourceAuthorization authorization;

        // WHEN
        authorization = KeyAssignmentController.class
            .getMethod("createKeyAssignment", KeyAssignmentCreationDto.class)
            .getAnnotation(RequireResourceAuthorization.class);

        // THEN
        assertEquals("KEY_ASSIGNMENT", authorization.resource());
        assertEquals(Actions.CREATE, authorization.action());
    }

    @Test
    @DisplayName("updateKeyAssignment requires key assignment update permission")
    void testPermissionUpdate() throws Exception {
        final RequireResourceAuthorization authorization;

        // WHEN
        authorization = KeyAssignmentController.class
            .getMethod("updateKeyAssignment", Long.class, KeyAssignmentUpdateDto.class)
            .getAnnotation(RequireResourceAuthorization.class);

        // THEN
        assertEquals("KEY_ASSIGNMENT", authorization.resource());
        assertEquals(Actions.UPDATE, authorization.action());
    }

    @Test
    @DisplayName("deleteKeyAssignment requires key assignment delete permission")
    void testPermissionDelete() throws Exception {
        final RequireResourceAuthorization authorization;

        // WHEN
        authorization = KeyAssignmentController.class
            .getMethod("deleteKeyAssignment", Long.class)
            .getAnnotation(RequireResourceAuthorization.class);

        // THEN
        assertEquals("KEY_ASSIGNMENT", authorization.resource());
        assertEquals(Actions.DELETE, authorization.action());
    }

}
