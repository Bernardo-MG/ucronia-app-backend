
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.member.test.configuration.data.annotation.ActiveMember;
import com.bernardomg.association.member.test.configuration.data.annotation.InactiveMember;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - existsPaid")
class ITFeeRepositoryExistsPaid {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("When there is no fee, then nothing exists")
    @PositiveFeeType
    @ActiveMember
    void testExists_NoFee() {
        final boolean exists;

        // WHEN
        exists = repository.existsPaid(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is an existing not paid fee for an active member, then it doesn't exist")
    @PositiveFeeType
    @ActiveMember
    @NotPaidFee
    void testExistsPaid_Active_NotPaid() {
        final boolean exists;

        // WHEN
        exists = repository.existsPaid(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is an existing paid fee for an active member, then it exists")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testExistsPaid_Active_Paid() {
        final boolean exists;

        // WHEN
        exists = repository.existsPaid(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is an existing not paid fee for an inactive member, then it doesn't exist")
    @PositiveFeeType
    @InactiveMember
    @NotPaidFee
    void testExistsPaid_Inactive_NotPaid() {
        final boolean exists;

        // WHEN
        exists = repository.existsPaid(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is an existing paid fee for an inactive member, then it exists")
    @PositiveFeeType
    @InactiveMember
    @PaidFee
    void testExistsPaid_Inactive_Paid() {
        final boolean exists;

        // WHEN
        exists = repository.existsPaid(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no data, then nothing exists")
    void testExistsPaid_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.existsPaid(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is an existing  paid fee for an active member, and checking an invalid date, then nothing exists")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testExistsPaid_WrongDate() {
        final boolean exists;

        // WHEN
        exists = repository.existsPaid(ProfileConstants.NUMBER, FeeConstants.NEXT_DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is an existing paid fee for an active member, and checking an invalid number, then nothing exists")
    @PositiveFeeType
    @ActiveMember
    @PaidFee
    void testExistsPaid_WrongNumber() {
        final boolean exists;

        // WHEN
        exists = repository.existsPaid(ProfileConstants.ALTERNATIVE_NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
