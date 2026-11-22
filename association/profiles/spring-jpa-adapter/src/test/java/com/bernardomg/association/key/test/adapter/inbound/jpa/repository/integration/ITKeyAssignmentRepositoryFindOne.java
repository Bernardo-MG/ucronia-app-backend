
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

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
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithKey;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - findOne")
class ITKeyAssignmentRepositoryFindOne {

    @Autowired
    private KeyAssignmentRepository repository;

    @Test
    @DisplayName("When there is an assigned key, then its member is returned")
    @PositiveFeeType
    @ActiveMemberWithKey
    void testFindOne_Assigned() {
        final Optional<KeyAssignment> assignment;

        // WHEN
        assignment = repository.findOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .as("When there is an assigned key, then its member is returned")
            .contains(KeyAssignments.assigned());
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<KeyAssignment> assignment;

        // WHEN
        assignment = repository.findOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is an unassigned key, then it is returned without a member")
    @AvailableKey
    void testFindOne_Unassigned() {
        final Optional<KeyAssignment> assignment;

        // WHEN
        assignment = repository.findOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .as("When there is an unassigned key, then it is returned without a member")
            .contains(KeyAssignments.unassigned());
    }

}
