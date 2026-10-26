
package com.bernardomg.association.key.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.model.KeyAssignment.AssignedProfile;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.key.usecase.service.DefaultKeyAssignmentService;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;

@ExtendWith(MockitoExtension.class)
@DisplayName("Key assignment service - get assignments")
class TestKeyAssignmentServiceGetAssignments {

    @Mock
    private KeyAssignmentRepository     repository;

    @InjectMocks
    private DefaultKeyAssignmentService service;

    @Test
    @DisplayName("When there is data, assigned and unassigned keys are returned")
    void testGetAssignments() {
        final Page<KeyAssignment> assignments;
        final KeyAssignment       assigned;
        final KeyAssignment       unassigned;
        final Key                 key;
        final Member              member;
        final Pagination          pagination;
        final Sorting             sorting;
        final Page<KeyAssignment> existing;

        // GIVEN
        pagination = Pagination.unpaged();
        sorting = Sorting.unsorted();

        key = Keys.available();
        member = Members.alternativeActive();

        assigned = new KeyAssignment(key.number(), key.available(), key.description(),
            Optional.of(new AssignedProfile(member.number(), member.name()
                .fullName())));
        unassigned = new KeyAssignment(key.number() + 1, false, key.description(), Optional.empty());

        existing = new Page<>(List.of(assigned, unassigned), 0, 0, 0, 0, 0, false, false, sorting);
        given(repository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        assignments = service.getAssignments(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .as("key assignments")
            .containsExactly(assigned, unassigned);
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testGetAssignments_NoData() {
        final Page<KeyAssignment> assignments;
        final Pagination          pagination;
        final Sorting             sorting;
        final Page<KeyAssignment> existing;

        // GIVEN
        pagination = Pagination.unpaged();
        sorting = Sorting.unsorted();

        existing = new Page<>(List.of(), 0, 0, 0, 0, 0, false, false, sorting);
        given(repository.findAll(pagination, sorting)).willReturn(existing);

        // WHEN
        assignments = service.getAssignments(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments)
            .extracting(Page::content)
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

}
