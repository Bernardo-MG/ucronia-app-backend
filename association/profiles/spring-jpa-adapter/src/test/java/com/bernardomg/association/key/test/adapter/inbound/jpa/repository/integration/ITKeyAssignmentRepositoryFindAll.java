
package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.model.KeyAssignment.AssignedProfile;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.data.annotation.AvailableKey;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.AlternativeMemberWithKey;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.pagination.domain.Page;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository - find assignments")
class ITKeyAssignmentRepositoryFindAll {

    @Autowired
    private KeyRepository           keyRepository;

    @Autowired
    private MemberRepository        memberRepository;

    @Autowired
    private KeyAssignmentRepository repository;

    @Test
    @DisplayName("An empty page retains the total number of keys")
    @AvailableKey
    void testFindAll_EmptyPage() {
        final Page<KeyAssignment> assignments;

        // WHEN
        assignments = repository.findAll(new Pagination(10, 10), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .isEmpty();
        Assertions.assertThat(assignments.totalElements())
            .isEqualTo(1);
    }

    @Test
    @DisplayName("Assigned and unassigned keys are listed together")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindAll_Mixed() {
        final Key                 free;
        final Page<KeyAssignment> assignments;

        // GIVEN
        free = keyRepository.save(new Key(-1L, false, "Unassigned unavailable key"));

        // WHEN
        assignments = repository.findAll(Pagination.unpaged(), Sorting.unsorted());

        // THEN
        Assertions.assertThat(assignments.content())
            .containsExactlyInAnyOrder(KeyAssignments.reassigned(),
                new KeyAssignment(free.number(), free.available(), free.description(), Optional.empty()));
    }

    @Test
    @DisplayName("With an assigned key, the key and its member are returned")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindAssignments() {
        final Page<KeyAssignment> assignments;
        final KeyAssignment       expected;
        final Key                 key;
        final Member              member;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        key = Keys.available();
        member = Members.alternativeActive();
        expected = new KeyAssignment(key.number(), key.available(), key.description(),
            Optional.of(new AssignedProfile(member.number(), member.name()
                .fullName())));
        pagination = Pagination.unpaged();
        sorting = Sorting.unsorted();

        // WHEN
        assignments = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(expected);
    }

    @Test
    @DisplayName("With an inactive member, its key assignment is still returned")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindAssignments_InactiveMember() {
        final Page<KeyAssignment> assignments;
        final KeyAssignment       expected;
        final Key                 key;
        final Member              member;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        key = Keys.available();
        member = Members.alternativeActive()
            .deactivated();
        memberRepository.save(member);

        expected = new KeyAssignment(key.number(), key.available(), key.description(),
            Optional.of(new AssignedProfile(member.number(), member.name()
                .fullName())));
        pagination = Pagination.unpaged();
        sorting = Sorting.unsorted();

        // WHEN
        assignments = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(expected);
    }

    @Test
    @DisplayName("With no keys, nothing is returned")
    void testFindAssignments_NoData() {
        final Page<KeyAssignment> assignments;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        pagination = Pagination.unpaged();
        sorting = Sorting.unsorted();

        // WHEN
        assignments = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .isEmpty();
    }

    @Test
    @DisplayName("With an unassigned key, it is returned without a member")
    @AvailableKey
    void testFindAssignments_Unassigned() {
        final Page<KeyAssignment> assignments;
        final KeyAssignment       expected;
        final Key                 key;
        final Pagination          pagination;
        final Sorting             sorting;

        // GIVEN
        key = Keys.available();
        expected = new KeyAssignment(key.number(), key.available(), key.description(), Optional.empty());
        pagination = Pagination.unpaged();
        sorting = Sorting.unsorted();

        // WHEN
        assignments = repository.findAll(pagination, sorting);

        // THEN
        Assertions.assertThat(assignments.content())
            .asInstanceOf(InstanceOfAssertFactories.LIST)
            .containsExactly(expected);
    }

}
