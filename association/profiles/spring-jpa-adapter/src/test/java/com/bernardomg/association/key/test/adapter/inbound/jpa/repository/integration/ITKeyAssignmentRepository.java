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

package com.bernardomg.association.key.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.key.adapter.inbound.jpa.repository.KeyAssignmentSpringRepository;
import com.bernardomg.association.key.domain.model.Key;
import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.repository.KeyAssignmentRepository;
import com.bernardomg.association.key.domain.repository.KeyRepository;
import com.bernardomg.association.key.test.configuration.data.annotation.AvailableKey;
import com.bernardomg.association.key.test.configuration.factory.KeyAssignments;
import com.bernardomg.association.key.test.configuration.factory.KeyConstants;
import com.bernardomg.association.key.test.configuration.factory.Keys;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.AlternativeMemberWithKey;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.profile.domain.exception.MissingProfileException;
import com.bernardomg.association.profile.domain.repository.ProfileRepository;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.pagination.domain.Pagination;
import com.bernardomg.pagination.domain.Sorting;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("KeyAssignmentRepository")
class ITKeyAssignmentRepository {

    @Autowired
    private KeyRepository                 keyRepository;

    @Autowired
    private MemberRepository              memberRepository;

    @Autowired
    private ProfileRepository             profileRepository;

    @Autowired
    private KeyAssignmentRepository       repository;

    @Autowired
    private KeyAssignmentSpringRepository springRepository;

    @Test
    @DisplayName("Removing an assignment keeps the member and the unassigned key")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testDelete() {
        final Member member;

        // WHEN
        repository.delete(KeyConstants.NUMBER);

        // THEN
        member = memberRepository.findOne(MemberConstants.ALTERNATIVE_NUMBER)
            .orElseThrow();
        Assertions.assertThat(repository.findProfileKey(member.number()))
            .isEmpty();
        Assertions.assertThat(springRepository.count())
            .isZero();
        Assertions.assertThat(member.memberNumber())
            .isEqualTo(MemberConstants.ALTERNATIVE_MEMBER_NUMBER);
        Assertions.assertThat(repository.findOne(KeyConstants.NUMBER))
            .contains(KeyAssignments.unassigned());
        Assertions.assertThat(keyRepository.findOne(KeyConstants.NUMBER))
            .contains(Keys.available());
    }

    @Test
    @DisplayName("Deleting an assigned profile removes the assignment and keeps the key")
    @ValidProfile
    @AvailableKey
    void testDelete_Profile() {
        // GIVEN
        repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // WHEN
        profileRepository.delete(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isZero();
        Assertions.assertThat(repository.findOne(KeyConstants.NUMBER))
            .contains(KeyAssignments.unassigned());
    }

    @Test
    @DisplayName("With an assigned key, its member is returned")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindOne_Assigned() {
        final Optional<KeyAssignment> assignment;

        // WHEN
        assignment = repository.findOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .contains(KeyAssignments.reassigned());
    }

    @Test
    @DisplayName("With no key, no assignment is returned")
    void testFindOne_MissingKey() {
        final Optional<KeyAssignment> assignment;

        // WHEN
        assignment = repository.findOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .isEmpty();
    }

    @Test
    @DisplayName("With an unassigned key, it is returned without a member")
    @AvailableKey
    void testFindOne_Unassigned() {
        final Optional<KeyAssignment> assignment;

        // WHEN
        assignment = repository.findOne(KeyConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .contains(KeyAssignments.unassigned());
    }

    @Test
    @DisplayName("An assigned member is found by profile number")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testFindProfileKey() {
        final Optional<Long> key;

        // WHEN
        key = repository.findProfileKey(MemberConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(key)
            .contains(KeyConstants.NUMBER);
    }

    @Test
    @DisplayName("Looking up a missing member reports a missing member")
    void testFindProfileKey_MissingProfile() {
        final ThrowingCallable execution;

        // WHEN
        execution = () -> repository.findProfileKey(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingProfileException.class);
    }

    @Test
    @DisplayName("A profile without membership is a valid assignment target")
    @ValidProfile
    void testFindProfileKey_ProfileWithoutMembership() {
        final Optional<Long> key;

        // WHEN
        key = repository.findProfileKey(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(key)
            .isEmpty();
    }

    @Test
    @DisplayName("A member without a key returns an empty assignment")
    @PositiveFeeType
    @ActiveMember
    void testFindProfileKey_Unassigned() {
        final Optional<Long> key;

        // WHEN
        key = repository.findProfileKey(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(key)
            .isEmpty();
    }

    @Test
    @DisplayName("Assigning a key preserves all other member fields and key details")
    @PositiveFeeType
    @AvailableKey
    @ActiveMember
    void testSave_Assigned() {
        final KeyAssignment assignment;
        final Member        member;

        // WHEN
        assignment = repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // THEN
        member = memberRepository.findOne(MemberConstants.NUMBER)
            .orElseThrow();
        Assertions.assertThat(assignment)
            .isEqualTo(KeyAssignments.assigned());
        Assertions.assertThat(member)
            .usingRecursiveComparison()
            .ignoringFields("audit")
            .isEqualTo(Members.active());
        Assertions.assertThat(keyRepository.findOne(KeyConstants.NUMBER))
            .contains(Keys.available());
    }

    @Test
    @DisplayName("The assignment table rejects assigning two keys to the same profile")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testSave_DuplicateProfile() {
        final Key              second;
        final ThrowingCallable execution;

        // GIVEN
        second = keyRepository.save(new Key(-1L, false, "Second key"));

        // WHEN
        execution = () -> repository.save(second.number(), MemberConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("An inactive member can keep and receive a key assignment")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testSave_InactiveMember() {
        final KeyAssignment assignment;

        // GIVEN
        memberRepository.save(Members.alternativeActive()
            .deactivated());

        // WHEN
        assignment = repository.save(KeyConstants.NUMBER, MemberConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .isEqualTo(KeyAssignments.reassigned());
        Assertions.assertThat(memberRepository.isActive(MemberConstants.ALTERNATIVE_NUMBER))
            .isFalse();
    }

    @Test
    @DisplayName("Repeating an assignment keeps a single assignment row")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testSave_OneRowPerKey() {
        // WHEN
        repository.save(KeyConstants.NUMBER, MemberConstants.ALTERNATIVE_NUMBER);
        repository.save(KeyConstants.NUMBER, MemberConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(springRepository.count())
            .isEqualTo(1);
        Assertions.assertThat(springRepository.findByKeyNumber(KeyConstants.NUMBER)
            .orElseThrow()
            .getProfile()
            .getNumber())
            .isEqualTo(MemberConstants.ALTERNATIVE_NUMBER);
    }

    @Test
    @DisplayName("A profile without membership can receive a key")
    @ValidProfile
    @AvailableKey
    void testSave_ProfileWithoutMembership() {
        final KeyAssignment assignment;

        // WHEN
        assignment = repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .isEqualTo(KeyAssignments.assigned());
        Assertions.assertThat(memberRepository.exists(MemberConstants.NUMBER))
            .isFalse();
    }

    @Test
    @DisplayName("Reassigning a key releases its previous member before saving the new assignment")
    @PositiveFeeType
    @ActiveMember
    @AlternativeMemberWithKey
    void testSave_Reassigned() {
        final KeyAssignment assignment;
        final Member        previous;

        // WHEN
        assignment = repository.save(KeyConstants.NUMBER, MemberConstants.NUMBER);

        // THEN
        previous = memberRepository.findOne(MemberConstants.ALTERNATIVE_NUMBER)
            .orElseThrow();
        Assertions.assertThat(assignment)
            .isEqualTo(KeyAssignments.assigned());
        Assertions.assertThat(springRepository.count())
            .isEqualTo(1);
        Assertions.assertThat(repository.findProfileKey(previous.number()))
            .isEmpty();
        Assertions.assertThat(repository.findAll(Pagination.unpaged(), Sorting.unsorted())
            .content())
            .containsExactly(KeyAssignments.assigned());
        Assertions.assertThat(keyRepository.findOne(KeyConstants.NUMBER))
            .contains(Keys.available());
    }

    @Test
    @DisplayName("Repeating the same assignment preserves its member and key")
    @PositiveFeeType
    @AlternativeMemberWithKey
    void testSave_SameMember() {
        final KeyAssignment assignment;

        // WHEN
        assignment = repository.save(KeyConstants.NUMBER, MemberConstants.ALTERNATIVE_NUMBER);

        // THEN
        Assertions.assertThat(assignment)
            .isEqualTo(KeyAssignments.reassigned());
    }

}
