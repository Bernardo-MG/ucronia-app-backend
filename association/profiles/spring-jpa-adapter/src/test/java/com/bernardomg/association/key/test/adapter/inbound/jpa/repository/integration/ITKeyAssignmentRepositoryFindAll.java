
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.test.configuration.data.annotation.AlternativeAvailableKey;
import com.bernardomg.association.key.test.configuration.data.annotation.AvailableKey;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - findAll")
class ITKeyAssignmentRepositoryFindAll {

    @Autowired
    private KeyAssignmentRepository repository;

    @Test
    @DisplayName("When there is an assigned key, then it is returned")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testFindAll_Assigned() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .as("When there is an assigned key, then it is returned")
            .contains(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("When keys are listed, then assigned and unassigned keys are both included")
    @PositiveFeeType
    @ActiveMemberWithKey
    @AlternativeAvailableKey
    void testFindAll_Mixed() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .as("Assigned and unassigned keys are listed together")
            .containsExactlyInAnyOrder(KeyAssignments.unassignedAlternative(), KeyAssignments.assigned());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindAll_NoData() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When assignments are listed, then profiles without membership are included")
    @AvailableKey
    @ValidProfile
    void testFindAll_ProfileWithoutMembership() {
        final Page<KeyAssignment> assignments;

        // GIVEN
        repository.save(KeyConstants.NUMBER, ProfileConstants.NUMBER);

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .as("Assignments to profiles without membership are included")
            .containsExactly(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("When there is an unassigned key, then it is returned")
    @AvailableKey
    void testFindAll_Unassigned() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .as("When there is an unassigned key, then it is returned")
            .contains(KeyAssignments.unassigned());
    }

}
