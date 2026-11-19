
package com.bernardomg.association.key.test.service.unit;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.key.domain.exception.KeyAssignmentConflictException;
import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.usecase.service.DefaultKeyAssignmentService;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultKeyAssignmentService - create")
class TestKeyAssignmentServiceCreate {

    @Mock
    private KeyAssignmentRepository     repository;

    @InjectMocks
    private DefaultKeyAssignmentService service;

    @Test
    @DisplayName("When there is a valid assignment, then the change is persisted and returned")
    void testCreate() {
        final KeyAssignment result;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.unassigned()));
        given(repository.save(KeyConstants.NUMBER, ProfileConstants.NUMBER)).willReturn(KeyAssignments.assigned());

        // WHEN
        result = service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(result)
            .as("When there is a valid assignment, then the change is persisted and returned")
            .isEqualTo(KeyAssignments.assigned());
        verify(repository).save(KeyConstants.NUMBER, ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("When there is an incompatible assignment state, then an exception is thrown")
    void testCreate_AlreadyAssigned() {
        final ThrowingCallable action;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.assigned()));

        // WHEN
        action = () -> service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is an incompatible assignment state, then an exception is thrown")
            .isInstanceOf(KeyAssignmentConflictException.class);
        verify(repository, never()).save(anyLong(), anyLong());
        verify(repository, never()).delete(anyLong());
    }

    @Test
    @DisplayName("When there is a missing key, then an exception is thrown")
    void testCreate_MissingKey() {
        final ThrowingCallable action;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing key, then an exception is thrown")
            .isInstanceOf(MissingKeyException.class);
        verify(repository, never()).save(anyLong(), anyLong());
        verify(repository, never()).delete(anyLong());
    }

    @Test
    @DisplayName("When there is a missing profile, then an exception is thrown")
    void testCreate_MissingProfile() {
        final ThrowingCallable action;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.unassigned()));
        given(repository.save(KeyConstants.NUMBER, ProfileConstants.NUMBER))
            .willThrow(new MissingProfileException(ProfileConstants.NUMBER));

        // WHEN
        action = () -> service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing profile, then an exception is thrown")
            .isInstanceOf(MissingProfileException.class);
        verify(repository).save(KeyConstants.NUMBER, ProfileConstants.NUMBER);
        verify(repository, never()).delete(anyLong());
    }

    @Test
    @DisplayName("When a profile already holding a key receives an assignment, then the assignment is accepted")
    void testCreate_ProfileHasAnotherKey() {
        final KeyAssignment first;
        final KeyAssignment other;
        final KeyAssignment result;

        // GIVEN
        other = new KeyAssignment(KeyConstants.NUMBER + 1, true, "Other key", KeyAssignments.assigned()
            .profile());
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.unassigned()));
        given(repository.save(KeyConstants.NUMBER, ProfileConstants.NUMBER)).willReturn(KeyAssignments.assigned());
        given(repository.findOne(other.number())).willReturn(
            Optional.of(new KeyAssignment(other.number(), other.available(), other.description(), Optional.empty())));
        given(repository.save(other.number(), ProfileConstants.NUMBER)).willReturn(other);
        first = service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // WHEN
        result = service.create(other.number(), ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(first)
            .as("A profile holding another key can receive an assignment")
            .isEqualTo(KeyAssignments.assigned());
        Assertions.assertThat(result)
            .as("A profile holding another key can receive an assignment")
            .isEqualTo(other);
        Assertions.assertThat(result.profile())
            .as("A profile holding another key can receive an assignment")
            .isEqualTo(first.profile());
        verify(repository).save(KeyConstants.NUMBER, ProfileConstants.NUMBER);
        verify(repository).save(other.number(), ProfileConstants.NUMBER);
        verify(repository, never()).delete(anyLong());
    }

}
