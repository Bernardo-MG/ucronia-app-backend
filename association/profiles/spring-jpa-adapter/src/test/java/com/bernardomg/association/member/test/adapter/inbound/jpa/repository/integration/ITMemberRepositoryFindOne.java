
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMemberWithEmail;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.Members;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - findOne")
class ITMemberRepositoryFindOne {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("When there is a member, then it is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindOne() {
        final Optional<Member> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is a member, then it is returned")
            .contains(Members.active());
    }

    @Test
    @DisplayName("When there is no member, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Member> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is no member, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a member with no member role, then it returns nothing")
    @ValidProfile
    void testFindOne_NoMembership() {
        final Optional<Member> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is a member with no member role, then it returns nothing")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a member with contact method, then it is returned")
    @PositiveFeeType
    @ActiveMemberWithEmail
    void testFindOne_WithContactMethod() {
        final Optional<Member> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is a member with contact method, then it is returned")
            .contains(Members.withEmail());
    }

}
