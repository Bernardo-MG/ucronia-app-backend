
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
@DisplayName("KeyAssignmentRepository - find assignments")
class ITKeyAssignmentRepositoryFindAll {

    @Autowired
    private KeyAssignmentRepository repository;

    @Test
    @DisplayName("When there is an assigned key, it is returned")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testFindAll_Assigned() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .contains(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("Assigned and unassigned keys are listed together")
    @PositiveFeeType
    @ActiveMemberWithKey
    @AlternativeAvailableKey
    void testFindAll_Mixed() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .containsExactlyInAnyOrder(KeyAssignments.unassignedAlternative(), KeyAssignments.assigned());
    }

    @Test
    @DisplayName("When there is no data, nothing is returned")
    void testFindAll_NoData() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .isEmpty();
    }

    @Test
    @DisplayName("Assignments to profiles without membership are included")
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
            .containsExactly(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("When there is an unassigned key, it is returned")
    @AvailableKey
    void testFindAll_Unassigned() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .contains(KeyAssignments.unassigned());
    }

}
