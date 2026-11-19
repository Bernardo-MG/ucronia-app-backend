
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveToNotRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveToRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveToNotRenewMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveToRenewMember;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - findAllToRenew")
class ITMemberRepositoryFindAllToRenew {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("When there is a profile without member role, then nothing is returned")
    @ValidProfile
    void testFindAllToRenew_NoMemberProfileship() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllToRenew();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is an active membership to not renew, then nothing is returned")
    @PositiveFeeType
    @ActiveToNotRenewMember
    void testFindAllToRenew_ToNotRenew_Active() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllToRenew();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is an inactive membership to not renew, then nothing is returned")
    @PositiveFeeType
    @InactiveToNotRenewMember
    void testFindAllToRenew_ToNotRenew_Inactive() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllToRenew();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is an active membership to renew, then it is returned")
    @PositiveFeeType
    @ActiveToRenewMember
    void testFindAllToRenew_ToRenew_Active() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllToRenew();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.active());
    }

    @Test
    @DisplayName("When there is an inactive membership to renew, then it is returned")
    @PositiveFeeType
    @InactiveToRenewMember
    void testFindAllToRenew_ToRenew_Inactive() {
        final Collection<Member> members;

        // WHEN
        members = repository.findAllToRenew();

        // THEN
        Assertions.assertThat(members)
            .as("members")
            .containsExactly(Members.inactiveToRenew());
    }

}
