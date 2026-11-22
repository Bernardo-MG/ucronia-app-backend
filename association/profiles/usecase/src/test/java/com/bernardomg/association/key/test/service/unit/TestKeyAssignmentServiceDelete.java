
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

import com.bernardomg.association.key.domain.exception.MissingKeyAssignmentException;
import com.bernardomg.association.key.domain.exception.MissingKeyException;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.usecase.service.DefaultKeyAssignmentService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultKeyAssignmentService - delete")
class TestKeyAssignmentServiceDelete {

    @Mock
    private KeyAssignmentRepository     repository;

    @InjectMocks
    private DefaultKeyAssignmentService service;

    @Test
    @DisplayName("When there is a valid assignment, then the change is persisted and returned")
    void testDelete() {
        final KeyAssignment result;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.assigned()));

        // WHEN
        result = service.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(result)
            .as("When there is a valid assignment, then the change is persisted and returned")
            .isEqualTo(KeyAssignments.assigned());
        verify(repository).delete(KeyConstants.NUMBER);
    }

    @Test
    @DisplayName("When there is a missing key, then an exception is thrown")
    void testDelete_MissingKey() {
        final ThrowingCallable action;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is a missing key, then an exception is thrown")
            .isInstanceOf(MissingKeyException.class);
        verify(repository, never()).save(anyLong(), anyLong());
        verify(repository, never()).delete(anyLong());
    }

    @Test
    @DisplayName("When there is an incompatible assignment state, then an exception is thrown")
    void testDelete_Unassigned() {
        final ThrowingCallable action;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.unassigned()));

        // WHEN
        action = () -> service.delete(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When there is an incompatible assignment state, then an exception is thrown")
            .isInstanceOf(MissingKeyAssignmentException.class);
        verify(repository, never()).save(anyLong(), anyLong());
        verify(repository, never()).delete(anyLong());
    }

}
