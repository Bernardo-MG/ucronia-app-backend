
package com.bernardomg.association.fee.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.association.TestApplication;
import com.bernardomg.association.fee.domain.model.Fee;
import com.bernardomg.association.fee.domain.repository.FeeRepository;
import com.bernardomg.association.fee.test.configuration.data.annotation.AlternativePaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.NotPaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PaidFee;
import com.bernardomg.association.fee.test.configuration.data.annotation.PositiveFeeType;
import com.bernardomg.association.fee.test.configuration.factory.FeeConstants;
import com.bernardomg.association.fee.test.configuration.factory.Fees;
import com.bernardomg.association.profile.test.configuration.data.annotation.AlternativeProfile;
import com.bernardomg.association.profile.test.configuration.data.annotation.ValidProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("FeeRepository - findOne")
class ITFeeRepositoryFindOne {

    @Autowired
    private FeeRepository repository;

    @Test
    @DisplayName("When there is two active members, then the alternative entity is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @PaidFee
    @AlternativePaidFee
    void testFindOne_Active_TwoMembers_Alternative() {
        final Optional<Fee> fee;

        // WHEN
        fee = repository.findOne(ProfileConstants.ALTERNATIVE_NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is two active members, then the alternative entity is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Fees.alternative()));
    }

    @Test
    @DisplayName("When there is no data, then nothing is returned")
    void testFindOne_NoData() {
        final Optional<Fee> fee;

        // WHEN
        fee = repository.findOne(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("When there is no data, then nothing is returned")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is no fee, then it returns nothing")
    @PositiveFeeType
    @ValidProfile
    void testFindOne_NoFee() {
        final Optional<Fee> fee;

        // WHEN
        fee = repository.findOne(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("When there is no fee, then it returns nothing")
            .isEmpty();
    }

    @Test
    @DisplayName("When there is a fee, and a not paid fee, then the related entity is returned")
    @PositiveFeeType
    @ValidProfile
    @NotPaidFee
    void testFindOne_NotPaid() {
        final Optional<Fee> fee;

        // WHEN
        fee = repository.findOne(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("When there is a fee, and a not paid fee, then the related entity is returned")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a fee, and a not paid fee, then the related entity is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Fees.notPaid()));
    }

    @Test
    @DisplayName("When there is a fee, and a paid fee, then the related entity is returned")
    @PositiveFeeType
    @ValidProfile
    @PaidFee
    void testFindOne_Paid() {
        final Optional<Fee> fee;

        // WHEN
        fee = repository.findOne(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a fee, and a paid fee, then the related entity is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Fees.paid()));
    }

    @Test
    @DisplayName("When there is a fee, and two members with paid fees, then the first entity is returned")
    @PositiveFeeType
    @ValidProfile
    @AlternativeProfile
    @PaidFee
    @AlternativePaidFee
    void testFindOne_Paid_TwoMembers() {
        final Optional<Fee> fee;

        // WHEN
        fee = repository.findOne(ProfileConstants.NUMBER, FeeConstants.DATE);

        // THEN
        Assertions.assertThat(fee)
            .as("fee")
            .hasValueSatisfying(actual -> Assertions.assertThat(actual)
                .as("When there is a fee, and two members with paid fees, then the first entity is returned")
                .usingRecursiveComparison()
                .ignoringFields("audit")
                .isEqualTo(Fees.paid()));
    }

}
