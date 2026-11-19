
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
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - exists")
class ITFeeRepositoryExists {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("When there is an existing not paid fee for an active member, then it exists")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testExists_Active_NotPaid() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is an existing paid fee for an active member, then it exists")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testExists_Active_Paid() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isTrue();
    }

    @Test
    @DisplayName("When there is no data, then nothing exists")
    void testExists_NoData() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is no fee, then nothing exists")
    @PositiveFeeType
    @ValidProfile
    void testExists_NoFee() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is an existing not paid fee for an active member, and checking an invalid date, then nothing exists")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testExists_WrongDate() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.NUMBER, FeeConstants.NEXT_DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

    @Test
    @DisplayName("When there is an existing not paid fee for an active member, and checking an invalid number, then nothing exists")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testExists_WrongNumber() {
        final boolean exists;

        // WHEN
        exists = repository.exists(ProfileConstants.ALTERNATIVE_NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(exists)
            .as("exists")
            .isFalse();
    }

}
