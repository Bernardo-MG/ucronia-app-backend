
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
import com.bernardomg.association.key.test.configuration.data.annotation.AvailableKey;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMemberWithKey;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - find assignments")
class ITKeyAssignmentRepositoryFindAssignments {

    @Autowired
    private KeyAssignmentRepository repository;

    @Test
    @DisplayName("With an assigned key, the key and its member are returned")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testFindAssignments() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .containsExactly(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("With an inactive member, its key assignment is still returned")
    @PositiveFeeType
    @InactiveMemberWithKey
    void testFindAssignments_InactiveMember() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .containsExactly(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("With no keys, nothing is returned")
    void testFindAssignments_NoData() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .isEmpty();
    }

    @Test
    @DisplayName("With an unassigned key, it is returned without a member")
    @AvailableKey
    void testFindAssignments_Unassigned() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .containsExactly(KeyAssignments.unassigned());
    }

}
