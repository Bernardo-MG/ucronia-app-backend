
package com.bernardomg.association.member.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.FeeType;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeTypes;
import com.bernardomg.association.member.domain.repository.MemberRepository;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("MemberRepository - findFeeType")
class ITMemberRepositoryGetFeeType {

    @Autowired
    private MemberRepository repository;

    @Test
    @DisplayName("When there is a member, then the fee type is returned")
    @PositiveFeeType
    @ActiveMember
    void testFindOne() {
        final Optional<FeeType> feeType;

        // WHEN
        feeType = repository.findFeeType(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .as("When there is a member, then the fee type is returned")
            .contains(FeeTypes.positive());
    }

    @Test
    @DisplayName("When there is no member, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<FeeType> feeType;

        // WHEN
        feeType = repository.findFeeType(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .as("When there is no member, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a member with no member role, then it returns nothing")
    @ValidProfile
    void testFindOne_NoMembership() {
        final Optional<FeeType> feeType;

        // WHEN
        feeType = repository.findFeeType(ProfileConstants.NUMBER);

        // THEN
        Assertions.assertThat(feeType)
            .as("When there is a member with no member role, then it returns nothing")
            .isEmpty();
    }

}
