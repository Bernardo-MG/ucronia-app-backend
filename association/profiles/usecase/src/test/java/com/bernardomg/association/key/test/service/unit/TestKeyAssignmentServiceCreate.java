/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
@DisplayName("Key assignment service - create")
class TestKeyAssignmentServiceCreate {

    @Mock
    private KeyAssignmentRepository     repository;

    @InjectMocks
    private DefaultKeyAssignmentService service;

    @Test
    @DisplayName("With a valid assignment, the change is persisted and returned")
    void testCreate() {
        final KeyAssignment result;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.unassigned()));
        given(repository.save(KeyConstants.NUMBER, ProfileConstants.NUMBER)).willReturn(KeyAssignments.assigned());

        // WHEN
        result = service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(result)
            .isEqualTo(KeyAssignments.assigned());
        verify(repository).save(KeyConstants.NUMBER, ProfileConstants.NUMBER);
    }

    @Test
    @DisplayName("With an incompatible assignment state, an exception is thrown")
    void testCreate_AlreadyAssigned() {
        final ThrowingCallable execution;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.assigned()));

        // WHEN
        execution = () -> service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(KeyAssignmentConflictException.class);
        verify(repository, never()).save(anyLong(), anyLong());
        verify(repository, never()).delete(anyLong());
    }

    @Test
    @DisplayName("With a missing key, an exception is thrown")
    void testCreate_MissingKey() {
        final ThrowingCallable execution;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingKeyException.class);
        verify(repository, never()).save(anyLong(), anyLong());
        verify(repository, never()).delete(anyLong());
    }

    @Test
    @DisplayName("With a missing profile, an exception is thrown")
    void testCreate_MissingProfile() {
        final ThrowingCallable execution;

        // GIVEN
        given(repository.findOne(KeyConstants.NUMBER)).willReturn(Optional.of(KeyAssignments.unassigned()));
        given(repository.save(KeyConstants.NUMBER, ProfileConstants.NUMBER))
            .willThrow(new MissingProfileException(ProfileConstants.NUMBER));

        // WHEN
        execution = () -> service.create(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingProfileException.class);
        verify(repository).save(KeyConstants.NUMBER, ProfileConstants.NUMBER);
        verify(repository, never()).delete(anyLong());
    }

    @Test
    @DisplayName("A profile holding another key can receive an assignment")
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
            .isEqualTo(KeyAssignments.assigned());
        Assertions.assertThat(result)
            .isEqualTo(other);
        Assertions.assertThat(result.profile())
            .isEqualTo(first.profile());
        verify(repository).save(KeyConstants.NUMBER, ProfileConstants.NUMBER);
        verify(repository).save(other.number(), ProfileConstants.NUMBER);
        verify(repository, never()).delete(anyLong());
    }

}
