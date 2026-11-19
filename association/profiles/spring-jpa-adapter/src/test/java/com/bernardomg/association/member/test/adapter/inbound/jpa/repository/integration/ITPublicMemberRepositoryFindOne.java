
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.time.ZoneOffset;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.test.configuration.data.annotation.FeeFullYear;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFeesWithGaps;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.member.domain.repository.PublicMemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.member.test.configuration.factory.MemberConstants;
import com.bernardomg.association.member.test.configuration.factory.PublicMembers;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("PublicMemberRepository - findOne")
class ITPublicMemberRepositoryFindOne {

    @Autowired
    private PublicMemberRepository repository;

    @Test
    @DisplayName("When there is an active member, then it is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindOne_Active() {
        final Optional<PublicMember> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is an active member, then it is returned")
            .contains(PublicMembers.active());
    }

    @Test
    @DisplayName("When there is an inactive member, then it is returned")
    @PositiveFeeType
    @InactiveMember
    void testFindOne_Inactive() {
        final Optional<PublicMember> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is an inactive member, then it is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no member, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<PublicMember> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is no member, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a profile with no member role, then it returns nothing")
    @ValidProfile
    void testFindOne_NoMembership() {
        final Optional<PublicMember> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is a profile with no member role, then it returns nothing")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a year of paid fees, then the first fee month and twelve months are returned")
    @PositiveFeeType
    @ActiveMember
    @FeeFullYear
    void testFindOne_PaidFullYear() {
        final Optional<PublicMember> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When there is a year of paid fees, then the first fee month and twelve months are returned")
            .contains(PublicMembers.withMembership(FeeConstants.JANUARY_DATE, 12L));
    }

    @Test
    @DisplayName("When gaps and earlier unpaid fees is provided, then only paid fee months are counted")
    @PositiveFeeType
    @ActiveMember
    @PaidFeesWithGaps
    void testFindOne_PaidWithGaps() {
        final Optional<PublicMember> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When gaps and earlier unpaid fees is provided, then only paid fee months are counted")
            .contains(PublicMembers.withMembership(FeeConstants.FEBRUARY_DATE, 2L,
                FeeConstants.FEBRUARY_DATE.atOffset(ZoneOffset.UTC)
                    .plusMonths(2)
                    .toInstant()));
    }

    @Test
    @DisplayName("When only unpaid fees is provided, then no joined month and zero months are returned")
    @PositiveFeeType
    @ActiveMember
    @NotPaidFee
    void testFindOne_UnpaidFees() {
        final Optional<PublicMember> member;

        // WHEN
        member = repository.findOne(MemberConstants.NUMBER);

        // THEN
        Assertions.assertThat(member)
            .as("When only unpaid fees is provided, then no joined month and zero months are returned")
            .contains(PublicMembers.active());
    }

}
